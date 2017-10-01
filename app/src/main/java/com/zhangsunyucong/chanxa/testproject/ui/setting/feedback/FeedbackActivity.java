package com.zhangsunyucong.chanxa.testproject.ui.setting.feedback;

import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.librarys.utils.ToastUtil;
import com.example.librarys.utils.ValidateUtils;
import com.example.librarys.utils.ViewUtils;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.zhangsunyucong.chanxa.testproject.R;
import com.zhangsunyucong.chanxa.testproject.base.AppComponent;
import com.zhangsunyucong.chanxa.testproject.base.mvp.BaseActivity;
import com.zhangsunyucong.chanxa.testproject.manager.ARouterConstant;
import com.zhangsunyucong.chanxa.testproject.ui.setting.inject.DaggerFeedbackComponent;
import com.zhangsunyucong.chanxa.testproject.ui.setting.inject.FeedbackModule;
import com.zhangsunyucong.chanxa.testproject.ui.user.AppUser;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import shortbread.Shortcut;

@Shortcut(id = "feedback", icon = R.mipmap.ic_shortcuts_feedback, shortLabel = "feedback")
@Route(path = ARouterConstant.PATH_ACTIVITY_FEEDBACK)
public class FeedbackActivity extends BaseActivity<FeedbackContract.Presenter>
    implements FeedbackContract.View {

    @BindView(R.id.btn_feedback)
    Button btn_feedback;

    @BindView(R.id.et_content)
    AppCompatEditText et_content;

    @BindView(R.id.et_userPhoneNum)
    EditText et_userPhoneNum;

    public static void startActicity() {
        ARouter.getInstance()
                .build(ARouterConstant.PATH_ACTIVITY_FEEDBACK)
                .navigation();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerFeedbackComponent.builder()
                .feedbackModule(new FeedbackModule(this))
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        setTitleText(R.string.feedback);
    }

    @Override
    protected void onTitileLeftClick(View backwardView) {
        super.onTitileLeftClick(backwardView);
        finish();
    }

    @Override
    protected void initListener() {
        super.initListener();
        RxTextView.textChanges(et_content)
                .debounce(600, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CharSequence>() {
                    @Override
                    public void accept(CharSequence charSequence) throws Exception {
                        if(ValidateUtils.checkNotNULL(charSequence)
                                && !TextUtils.isEmpty(charSequence.toString())) {
                            btn_feedback.setEnabled(true);
                        } else {
                            btn_feedback.setEnabled(false);
                        }
                    }
                });
    }

    @Override
    public void feedbackSuccess() {
        ToastUtil.showShort(mContext, "谢谢您的吐槽！");
    }

    @Override
    public void feedbackFail(String msg) {
        ToastUtil.showShort(mContext, msg);
    }

    @OnClick(R.id.btn_feedback)
    public void onFeedbackClickEvent(View view) {
        String content = ViewUtils.getEditTextString(et_content);
        if(TextUtils.isEmpty(content)) {
            ToastUtil.showShort(mContext, R.string.feedback_content_empty);
        }
        String userPhoneNum = ViewUtils.getEditTextString(et_userPhoneNum);

        if(TextUtils.isEmpty(userPhoneNum)) {
            userPhoneNum = AppUser.getAppUser().getuserLoginResult().mobilePhoneNumber;
        }

        if(TextUtils.isEmpty(userPhoneNum)) {
            userPhoneNum = AppUser.getAppUser().getuserLoginResult().username;
        }

        mPresenter.feedback("android", content, userPhoneNum);

    }
}
