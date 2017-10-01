package com.zhangsunyucong.chanxa.testproject.ui.user.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.librarys.rxpermissions.RxPermissions;
import com.zhangsunyucong.chanxa.testproject.MainActivity;
import com.zhangsunyucong.chanxa.testproject.R;
import com.example.librarys.utils.RegexUtils;
import com.example.librarys.utils.ToastUtil;
import com.example.librarys.utils.ViewUtils;
import com.zhangsunyucong.chanxa.testproject.base.AppComponent;
import com.zhangsunyucong.chanxa.testproject.base.mvp.BaseActivity;
import com.zhangsunyucong.chanxa.testproject.manager.PermissionManager;
import com.zhangsunyucong.chanxa.testproject.ui.user.AppUser;
import com.zhangsunyucong.chanxa.testproject.ui.user.login.inject.DaggerLoginComponent;
import com.zhangsunyucong.chanxa.testproject.ui.user.login.inject.LoginModule;
import com.zhangsunyucong.chanxa.testproject.ui.user.register.RegisterActivity;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2017/1/3.
 */
public class LoginActivity extends BaseActivity<LoginContract.Presenter> implements LoginContract.View {

    @BindView(R.id.et_userPhoneNum)
    EditText et_userPhoneNum;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.tv_link_signup)
    TextView tv_link_signup;
    @BindView(R.id.tv_login_by_user_name)
    TextView tv_login_by_user_name;
    AppUser mAppUser;

    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            btn_login.setEnabled(canLogin());
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    private boolean canLogin() {
        int userPhoneNumLength = et_userPhoneNum.getText().toString().trim().length();
        int pwdLength = et_password.getText().toString().trim().length();
        if (userPhoneNumLength > 0 && pwdLength > 0 && RegexUtils.isMobileExact(et_userPhoneNum.getText().toString().trim())) {
            return true;
        }
        return false;
    }


    public static void startLoginActivity(BaseActivity activity, boolean finishAppActivity) {
        if(activity != null) {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_REORDER_TO_FRONT|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setClass(activity, LoginActivity.class);
            activity.startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNotSwipeBackEnable();
        if(AppUser.getAppUser().isLogin(this)) {
            MainActivity.startMainActivity(this);
            finish();
        }
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerLoginComponent
                .builder()
                .appComponent(appComponent)
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initListener() {
        super.initListener();
        et_userPhoneNum.addTextChangedListener(watcher);
        et_password.addTextChangedListener(watcher);
    }

    @Override
    protected void getRuntimePermission(RxPermissions rxPermissions) {
        super.getRuntimePermission(rxPermissions);
        rxPermissions
                .request(PermissionManager.STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {

                    }
                });

        rxPermissions
                .request(PermissionManager.LOCATION)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {

                    }
                });

        rxPermissions
                .request(PermissionManager.CAMERA)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {

                    }
                });


    }

    @Override
    protected void initData() {
        ViewUtils.updateEditText(et_userPhoneNum,
                AppUser.getAppUser().getLastLoginUserPhoneNum());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        setTitleText(R.string.login);
        showTitleLeftView(true);
    }

    @OnClick(R.id.tv_link_signup)
    public void onClickRegister(View v) {
        RegisterActivity.startRegisterActivity();
    }

    @OnClick(R.id.tv_login_by_user_name)
    public void onClickLoginByUserName(View view) {
        LoginByNameActivity.startLoginByNameActivity();
    }

    @OnClick(R.id.tv_login_by_sms)
    public void onClickLoginBySmsEvent(View view){
        LoginBySMSActivity.startLoginBySMSActivity();
    }

    @OnClick(R.id.btn_login)
    public void onClick(View v) {
        mAppUser = AppUser.getAppUser();
        String userPhoneNum = et_userPhoneNum.getText().toString();
        String password = et_password.getText().toString();
        if(TextUtils.isEmpty(userPhoneNum)) {
            ToastUtil.showShort(mContext, R.string.user_name_not_empty);
            return;
        }

        if(!RegexUtils.isMobileExact(userPhoneNum)) {
            ToastUtil.showShort(mContext, R.string.user_phone_num_not_right);
            return;
        }

        if(TextUtils.isEmpty(password)) {
            ToastUtil.showShort(mContext, R.string.password_not_empty);
            return;
        }

        mPresenter.login(userPhoneNum, password);

    }

    @Override
    protected void onTitileLeftClick(View backwardView) {
        super.onTitileLeftClick(backwardView);
        finish();
    }

    @Override
    public void loginSuccess(String result) {
        MainActivity.startMainActivity(this);
        finish();
    }

    @Override
    public void loginFail(String errorMsg) {
        ToastUtil.showShort(mContext, errorMsg);
    }
}
