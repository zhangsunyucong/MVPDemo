package com.zhangsunyucong.chanxa.testproject.ui.user.register;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.zhangsunyucong.chanxa.testproject.R;
import com.example.librarys.utils.RegexUtils;
import com.example.librarys.utils.ToastUtil;
import com.example.librarys.utils.ViewUtils;
import com.zhangsunyucong.chanxa.testproject.base.AppComponent;
import com.zhangsunyucong.chanxa.testproject.base.mvp.BaseActivity;
import com.zhangsunyucong.chanxa.testproject.manager.ARouterConstant;
import com.zhangsunyucong.chanxa.testproject.ui.user.login.LoginActivity;
import com.zhangsunyucong.chanxa.testproject.ui.user.register.inject.DaggerRegisterComponent;
import com.zhangsunyucong.chanxa.testproject.ui.user.register.inject.RegisterModule;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

@Route(path = ARouterConstant.PATH_ACTIVITY_REGISTER)
public class RegisterActivity extends BaseActivity<RegisterContract.Presenter> implements RegisterContract.View{

    @BindView(R.id.etNick)
    EditText mEtNick;
    @BindView(R.id.vLineNick)
    View mVLineNick;

    @BindView(R.id.etPhone)
    EditText mEtPhone;
    @BindView(R.id.vLinePhone)
    View mVLinePhone;

    @BindView(R.id.etPwd)
    EditText mEtPwd;
    @BindView(R.id.ivSeePwd)
    ImageView mIvSeePwd;
    @BindView(R.id.vLinePwd)
    View mVLinePwd;

    @BindView(R.id.etVerifyCode)
    EditText mEtVerifyCode;
    @BindView(R.id.btnSendCode)
    Button mBtnSendCode;
    @BindView(R.id.vLineVertifyCode)
    View mVLineVertifyCode;

    @BindView(R.id.btnRegister)
    Button mBtnRegister;

    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mBtnRegister.setEnabled(canRegister());
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    public static void startRegisterActivity() {
        ARouter.getInstance()
                .build(ARouterConstant.PATH_ACTIVITY_REGISTER)
                .navigation();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerRegisterComponent
                .builder()
                .appComponent(appComponent)
                .registerModule(new RegisterModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        showTitleLeftView(true);
        setTitleText(R.string.register);
    }

    @Override
    protected void onTitileLeftClick(View backwardView) {
        super.onTitileLeftClick(backwardView);
        finish();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mEtNick.addTextChangedListener(watcher);
        mEtPwd.addTextChangedListener(watcher);
        mEtPhone.addTextChangedListener(watcher);
        mEtVerifyCode.addTextChangedListener(watcher);
        RxTextView.textChanges(mEtPhone)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CharSequence>() {
                    @Override
                    public void accept(CharSequence charSequence) throws Exception {
                        if(TextUtils.isEmpty(charSequence)) {
                            mBtnSendCode.setEnabled(false);
                        } else {
                            if(RegexUtils.isMobileExact(charSequence)) {
                                mBtnSendCode.setEnabled(true);
                            } else {
                                mBtnSendCode.setEnabled(false);
                            }
                        }
                    }
                });
    }

    private boolean canRegister() {
        int nickNameLength = mEtNick.getText().toString().trim().length();
        int pwdLength = mEtPwd.getText().toString().trim().length();
        int phoneLength = mEtPhone.getText().toString().trim().length();
        int codeLength = mEtVerifyCode.getText().toString().trim().length();
        if (nickNameLength > 0 && pwdLength > 0 && phoneLength > 0 && codeLength > 0) {
            return true;
        }
        return false;
    }

    @OnClick({R.id.btnSendCode, R.id.btnRegister, R.id.ivSeePwd, R.id.tv_register_by_nickname})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSendCode:
                if(mPresenter != null) {
                    String userPhoneNum = mEtPhone.getText().toString();
                    if(TextUtils.isEmpty(userPhoneNum)) {
                        ToastUtil.showShort(mContext, "请输入手机号");
                        return;
                    }
                    if(!RegexUtils.isMobileExact(userPhoneNum)) {
                        ToastUtil.showShort(mContext, R.string.user_phone_num_not_right);
                        return;
                    }
                    mPresenter.requestSmsCode(userPhoneNum);
                }

                break;
            case R.id.btnRegister:
                if(mPresenter != null) {
                    String userPhoneNum = ViewUtils.getEditTextString(mEtPhone);
                    String nickName = ViewUtils.getEditTextString(mEtNick);
                    String smsCcode = ViewUtils.getEditTextString(mEtVerifyCode);
                    String password = ViewUtils.getEditTextString(mEtPwd);
                    mPresenter.registerByMobile(userPhoneNum, nickName, smsCcode, password);
                }
                break;
            case R.id.ivSeePwd:
                setPwdVisibility();
                break;
            case R.id.tv_register_by_nickname:
                RegisterByNameActivity.startRegisterByNameActivity();
                break;
        }
    }

    private void setPwdVisibility() {
        if (mEtPwd.getTransformationMethod() == HideReturnsTransformationMethod.getInstance()) {
            mEtPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            mEtPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }

        mEtPwd.setSelection(mEtPwd.getText().toString().trim().length());
    }

    @Override
    public void registerFail(String errorMsg) {
        ToastUtil.showShort(mContext, errorMsg);
    }

    @Override
    public void registerSuccess() {
        LoginActivity.startLoginActivity(RegisterActivity.this, true);
    }

    @Override
    public void requestSmsCodeSuccess() {
        ToastUtil.showShort(mContext, "验证码已经发送");
    }

    @Override
    public void requestSmsCodeFail(String errorMsg) {
        ToastUtil.showShort(mContext, errorMsg);
    }
}
