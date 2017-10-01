package com.zhangsunyucong.chanxa.testproject.ui.main.music;


import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.zhangsunyucong.chanxa.testproject.R;
import com.example.librarys.utils.ValidateUtils;
import com.example.librarys.utils.ViewUtils;
import com.zhangsunyucong.chanxa.testproject.base.AppComponent;
import com.zhangsunyucong.chanxa.testproject.base.mvp.BaseSocketFragment;
import com.zhangsunyucong.chanxa.testproject.common.SocketHelper;
import com.zhangsunyucong.chanxa.testproject.socket.Message;
import com.zhangsunyucong.chanxa.testproject.socket.MessageAdapter;
import com.zhangsunyucong.chanxa.testproject.socket.MessageEntity;
import com.zhangsunyucong.chanxa.testproject.ui.user.AppUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.socket.emitter.Emitter;

public class MusicFragment extends BaseSocketFragment {

    private String mTitle;
    private static final String TAG = "MainFragment";

    private static final int REQUEST_LOGIN = 0;

    private static final int TYPING_TIMER_LENGTH = 600;

    @BindView(R.id.messages)
    RecyclerView mMessagesView;
    @BindView(R.id.message_input)
    EditText mInputMessageView;
    private List<Message> mMessages = new ArrayList<Message>();
    private RecyclerView.Adapter mAdapter;
    private boolean mTyping = false;
    private Handler mTypingHandler = new Handler();
    private String mUsername;


    public MusicFragment() {
        // Required empty public constructor
    }

    public static MusicFragment newInstance(String title) {
        MusicFragment fragment = new MusicFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_music;
    }

    @Override
    protected void ComponentInject(AppComponent appComponent) {

    }

    @Override
    protected void getFragmentArg(Bundle bundle) {
        super.getFragmentArg(bundle);
        if(bundle != null) {
            mTitle = getArguments().getString("title");
        }
    }

    @Override
    protected void setAdapter() {
        super.setAdapter();
        mAdapter = new MessageAdapter(mActivity, mMessages);
        mMessagesView = (RecyclerView) mRootView.findViewById(R.id.messages);
        mMessagesView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMessagesView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        mUsername = AppUser.getAppUser().getuserLoginResult().username;
        socketEmitString("add user", mUsername);
    }

    @Override
    protected void initSocket() {
        super.initSocket();

        getSocket().on("new message", onNewMessage);
        getSocket().on("user joined", onUserJoined);
        getSocket().on("user left", onUserLeft);
        getSocket().on("typing", onTyping);
        getSocket().on("stop typing", onStopTyping);
        getSocket().on("login", onLogin);
        connectSocket();
    }

    @Override
    protected void onSocketConnect(Object... args) {
        super.onSocketConnect(args);
        if(null!=mUsername)
            getSocket().emit("add user", mUsername);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        getSocket().disconnect();

        getSocket().off("new message", onNewMessage);
        getSocket().off("user joined", onUserJoined);
        getSocket().off("user left", onUserLeft);
        getSocket().off("typing", onTyping);
        getSocket().off("stop typing", onStopTyping);
        getSocket().off("login", onLogin);
    }

    @Override
    protected void initListener() {
        super.initListener();

        ViewUtils.setOnEditorActionListener(mInputMessageView, new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == R.id.send || actionId == EditorInfo.IME_NULL) {
                    attemptSend();
                    return true;
                }
                return false;
            }
        });

        ViewUtils.addTextChangedListener(mInputMessageView, new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (null == mUsername) return;
                if (!getSocketConnectStatus()) return;

                if (!mTyping) {
                    mTyping = true;
                    socketEmitEvent("typing");
                }

                mTypingHandler.removeCallbacks(onTypingTimeout);
                mTypingHandler.postDelayed(onTypingTimeout, TYPING_TIMER_LENGTH);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @OnClick({R.id.send_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_button:
                attemptSend();
                break;

        }
    }

    private void addLog(String message) {
        mMessages.add(new Message.Builder(Message.TYPE_LOG)
                .message(message).build());
        mAdapter.notifyItemInserted(mMessages.size() - 1);
        scrollToBottom();
    }

    private void addParticipantsLog(int numUsers) {
        addLog(getResources().getQuantityString(R.plurals.message_participants, numUsers, numUsers));
    }

    private void addMessage(String username, String message) {
        mMessages.add(new Message.Builder(Message.TYPE_MESSAGE)
                .username(username).message(message).build());
        mAdapter.notifyItemInserted(mMessages.size() - 1);
        scrollToBottom();
    }

    private void addTyping(String username) {
        mMessages.add(new Message.Builder(Message.TYPE_ACTION)
                .username(username).build());
        mAdapter.notifyItemInserted(mMessages.size() - 1);
        scrollToBottom();
    }

    private void removeTyping(String username) {
        for (int i = mMessages.size() - 1; i >= 0; i--) {
            Message message = mMessages.get(i);
            if (message.getType() == Message.TYPE_ACTION && message.getUsername().equals(username)) {
                mMessages.remove(i);
                mAdapter.notifyItemRemoved(i);
            }
        }
    }

    private void attemptSend() {
        if (null == mUsername) return;
        if (!getSocketConnectStatus()) return;

        mTyping = false;

        String message = mInputMessageView.getText().toString().trim();
        if (TextUtils.isEmpty(message)) {
            mInputMessageView.requestFocus();
            return;
        }

        mInputMessageView.setText("");
        addMessage(mUsername, message);

        // perform the sending message attempt.

        socketEmitString("new message", message);

    }

    private void leave() {
        mUsername = null;
        disConnectSocket();
        connectSocket();
    }

    private void scrollToBottom() {
        mMessagesView.scrollToPosition(mAdapter.getItemCount() - 1);
    }



    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    String data = (String)args[0];
                    String decData = SocketHelper.getDecryptData(data);
                    MessageEntity messageEntity = getGson().fromJson(decData, MessageEntity.class);

                    String username;
                    String message;
                    if(ValidateUtils.checkNotNULL(messageEntity)) {
                        username = messageEntity.username;
                        message = messageEntity.message;
                        removeTyping(username);
                        addMessage(username, message);
                    }

                }
            });
        }
    };


    private Emitter.Listener onLogin = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {



                    JSONObject data = (JSONObject) args[0];

                    int numUsers;
                    try {
                        numUsers = data.getInt("numUsers");
                        addLog(getResources().getString(R.string.message_welcome));
                        addParticipantsLog(numUsers);
                    } catch (JSONException e) {
                        return;
                    }
                }
            });


        }
    };

    private Emitter.Listener onUserJoined = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    JSONObject respose = (JSONObject)args[0];

                    String username;
                    int numUsers;
                    try {
                        username = respose.getString("username");
                        numUsers = respose.getInt("numUsers");
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                        return;
                    }
                    addLog(getResources().getString(R.string.message_user_joined, username));
                    addParticipantsLog(numUsers);
                }
            });
        }
    };

    private Emitter.Listener onUserLeft = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    int numUsers;
                    try {
                        username = data.getString("username");
                        numUsers = data.getInt("numUsers");
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                        return;
                    }

                    addLog(getResources().getString(R.string.message_user_left, username));
                    addParticipantsLog(numUsers);
                    removeTyping(username);
                }
            });
        }
    };

    private Emitter.Listener onTyping = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    try {
                        username = data.getString("username");
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                        return;
                    }
                    addTyping(username);
                }
            });
        }
    };

    private Emitter.Listener onStopTyping = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    try {
                        username = data.getString("username");
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                        return;
                    }
                    removeTyping(username);
                }
            });
        }
    };

    private Runnable onTypingTimeout = new Runnable() {
        @Override
        public void run() {
            if (!mTyping) return;

            mTyping = false;
            socketEmitObjects("stop typing");
        }
    };
}
