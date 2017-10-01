package com.zhangsunyucong.chanxa.testproject.base.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.zhangsunyucong.chanxa.testproject.App;
import com.zhangsunyucong.chanxa.testproject.R;
import com.example.librarys.utils.ToastUtil;
import com.zhangsunyucong.chanxa.testproject.database.common.AESUtils;
import com.zhangsunyucong.chanxa.testproject.manager.AppConfig;

import javax.inject.Inject;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by zhangsunyucong on 2017/9/17.
 */

public abstract class BaseSocketFragment<P extends IPresenter> extends BaseFragment<P> {

    private Boolean isConnected = true;
    private Socket mSocket;
    @Inject
    Gson mGson;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initSocket();
    }

    protected boolean getSocketConnectStatus() {
        return isConnected;
    }

    protected void initSocket() {
        getSocket().on(Socket.EVENT_CONNECT, onConnect);
        getSocket().on(Socket.EVENT_DISCONNECT, onDisconnect);
        getSocket().on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        getSocket().on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
    }

    protected Gson getGson(){
        if(mGson == null) {
            mGson = new Gson();
        }
        return mGson;
    }

    protected Socket getSocket() {
        App app = (App) getActivity().getApplication();
        mSocket = app.getSocket();
        return mSocket;
    }

    protected void connectSocket() {
        getSocket().connect();
    }

    protected void disConnectSocket() {
        getSocket().disconnect();
    }

    protected void socketEmitJSONString(final String event, final String args) {
        getSocket().emit(event, args);
    }

    protected void socketEmitString(final String event, String args) {
        if(args == null) {
            args = "";
        }

        String data = AESUtils.encryptData(AppConfig.ChatScrect, args);
        getSocket().emit(event, data);
    }

    protected void socketEmitEvent(final String event) {
        getSocket().emit(event);
    }

    protected void socketEmitObjects(final String event, final Object... args) {
        getSocket().emit(event, args);
    }

    protected Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(!isConnected) {
                        onSocketConnect(args);
                        ToastUtil.showShort(mActivity, R.string.connect);
                        isConnected = true;
                    }
                }
            });
        }
    };

    protected void onSocketConnect(Object... args) {}

    protected Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    onSocketDisConnect(args);
                    Log.i(TAG, "diconnected");
                    isConnected = false;
                    ToastUtil.showShort(mActivity, R.string.disconnect);
                }
            });
        }
    };

    protected void onSocketDisConnect(Object... args) {}

    protected Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    onSocketConnectError(args);
                    Log.e(TAG, "Error connecting");
                    ToastUtil.showShort(mActivity, R.string.error_connect);
                }
            });
        }
    };

    protected void onSocketConnectError(Object... args) {}

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(getSocket() != null) {
            getSocket().off(Socket.EVENT_CONNECT, onConnect);
            getSocket().off(Socket.EVENT_DISCONNECT, onDisconnect);
            getSocket().off(Socket.EVENT_CONNECT_ERROR, onConnectError);
            getSocket().off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        }
    }
}
