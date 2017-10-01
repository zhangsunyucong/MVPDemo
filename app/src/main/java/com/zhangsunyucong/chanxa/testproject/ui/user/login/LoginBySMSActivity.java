package com.zhangsunyucong.chanxa.testproject.ui.user.login;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.zhangsunyucong.chanxa.testproject.MainActivity;
import com.zhangsunyucong.chanxa.testproject.R;
import com.example.librarys.utils.RegexUtils;
import com.example.librarys.utils.ToastUtil;
import com.example.librarys.utils.ViewUtils;
import com.zhangsunyucong.chanxa.testproject.base.AppComponent;
import com.zhangsunyucong.chanxa.testproject.base.mvp.BaseActivity;
import com.zhangsunyucong.chanxa.testproject.common.ActivitysManager;
import com.zhangsunyucong.chanxa.testproject.manager.ARouterConstant;
import com.zhangsunyucong.chanxa.testproject.ui.user.AppUser;
import com.zhangsunyucong.chanxa.testproject.ui.user.login.inject.DaggerLoginBySMSComponent;
import com.zhangsunyucong.chanxa.testproject.ui.user.login.inject.LoginBySMSModule;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

@Route(path = ARouterConstant.PATH_ACTIVITY_LOGIN_BY_SMS)
public class LoginBySMSActivity extends BaseActivity<LoginBySMSCodeContract.Presenter>
implements LoginBySMSCodeContract.View{

    @BindView(R.id.et_user_phone_num)
    EditText et_user_phone_num;

    @BindView(R.id.et_sms_code)
    EditText et_sms_code;

    @BindView(R.id.btn_login)
    Button btn_login;

    @BindView(R.id.btn_send_sms)
    Button btn_send_sms;


    public static void startLoginBySMSActivity() {
        ARouter.getInstance()
                .build(ARouterConstant.PATH_ACTIVITY_LOGIN_BY_SMS)
                .navigation();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_by_sms;
    }


    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerLoginBySMSComponent
                .builder()
                .appComponent(appComponent)
                .loginBySMSModule(new LoginBySMSModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initData() {
        ViewUtils.updateEditText(et_user_phone_num,
                AppUser.getAppUser().getLastLoginUserPhoneNum());
        setTitleText(R.string.login);
    }

    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        showTitleLeftView(true);
        setTitleText(R.string.login);
    }

    @Override
    protected void onTitileLeftClick(View backwardView) {
        super.onTitileLeftClick(backwardView);
        finish();
    }

    @Override
    protected void initListener() {
        super.initListener();
        et_user_phone_num.addTextChangedListener(watcher);
        et_sms_code.addTextChangedListener(watcher);
        RxTextView.textChanges(et_user_phone_num)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CharSequence>() {
                    @Override
                    public void accept(CharSequence charSequence) throws Exception {
                        if(TextUtils.isEmpty(charSequence)) {
                            btn_send_sms.setEnabled(false);
                        } else {
                            if(RegexUtils.isMobileExact(charSequence)) {
                                btn_send_sms.setEnabled(true);
                            } else {
                                btn_send_sms.setEnabled(false);
                            }
                        }
                    }
                });
    }

    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            btn_login.setEnabled(canRegister());
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    private boolean canRegister() {
        int userPhoneNumLength = ViewUtils.getEditTextString(et_user_phone_num).trim().length();
        int smsCodepasswordLength = ViewUtils.getEditTextString(et_sms_code).trim().length();
        if(userPhoneNumLength > 0 && smsCodepasswordLength > 0) {
            return true;
        }
        return false;
    }

    @OnClick(R.id.btn_send_sms)
    public void onClickSendSmsEvent(View view) {
        String userPhoneNum = ViewUtils.getEditTextString(et_user_phone_num);
        if(TextUtils.isEmpty(userPhoneNum)) {
            ToastUtil.showShort(mContext, R.string.user_phone_num_not_empty);
            return;
        }

        if(!RegexUtils.isMobileExact(userPhoneNum)) {
            ToastUtil.showShort(mContext, R.string.user_phone_num_not_right);
            return;
        }

        mPresenter.requestSmsCode(userPhoneNum);
    }

    @OnClick(R.id.btn_login)
    public void clickLoginEvent(View view) {
        String userPhoneNum = ViewUtils.getEditTextString(et_user_phone_num);
        if(TextUtils.isEmpty(userPhoneNum)) {
            ToastUtil.showShort(mContext, R.string.user_phone_num_not_right);
            return;
        }

        String smsCode = ViewUtils.getEditTextString(et_sms_code);
        if(TextUtils.isEmpty(smsCode)) {
            ToastUtil.showShort(mContext, R.string.sms_code_not_empty);
            return;
        }

        mPresenter.loginBySMSCode(userPhoneNum, smsCode);
    }

    @Override
    public void loginSuccess(String result) {
        ActivitysManager.getInstance().getActivity(LoginActivity.class).finish();
        MainActivity.startMainActivity(LoginBySMSActivity.this);
        finish();
    }

    @Override
    public void loginFail(String errorMsg) {
        ToastUtil.showShort(mContext, errorMsg);
    }

    @Override
    public void requestSmsCodeSuccess() {
        ToastUtil.showShort(mContext, R.string.sms_code_send);
    }

    @Override
    public void requestSmsCodeFail(String errorMsg) {
        ToastUtil.showShort(mContext, errorMsg);
    }
}
