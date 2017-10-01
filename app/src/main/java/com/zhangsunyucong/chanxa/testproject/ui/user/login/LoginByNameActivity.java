package com.zhangsunyucong.chanxa.testproject.ui.user.login;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zhangsunyucong.chanxa.testproject.MainActivity;
import com.zhangsunyucong.chanxa.testproject.R;
import com.example.librarys.utils.ToastUtil;
import com.example.librarys.utils.ViewUtils;
import com.zhangsunyucong.chanxa.testproject.base.AppComponent;
import com.zhangsunyucong.chanxa.testproject.base.mvp.BaseActivity;
import com.zhangsunyucong.chanxa.testproject.common.ActivitysManager;
import com.zhangsunyucong.chanxa.testproject.manager.ARouterConstant;
import com.zhangsunyucong.chanxa.testproject.ui.user.AppUser;
import com.zhangsunyucong.chanxa.testproject.ui.user.login.inject.DaggerLoginByNameComponent;
import com.zhangsunyucong.chanxa.testproject.ui.user.login.inject.LoginByNameModule;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = ARouterConstant.PATH_ACTIVITY_LOGIN_BY_NAME)
public class LoginByNameActivity extends BaseActivity<LoginByNameContract.Presenter>
    implements LoginByNameContract.View {

    @BindView(R.id.et_user_name)
    EditText et_user_name;

    @BindView(R.id.et_password)
    EditText et_password;

    @BindView(R.id.btn_login)
    Button btn_login;

    public static void startLoginByNameActivity() {
        ARouter.getInstance()
                .build(ARouterConstant.PATH_ACTIVITY_LOGIN_BY_NAME)
                .navigation();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_by_name;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerLoginByNameComponent
                .builder()
                .appComponent(appComponent)
                .loginByNameModule(new LoginByNameModule(this))
                .build()
                .inject(this);
    }

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
        int nickNameLength = et_user_name.getText().toString().trim().length();
        int pwdLength = et_password.getText().toString().trim().length();
        if (nickNameLength > 0 && pwdLength > 0) {
            return true;
        }
        return false;
    }

    @Override
    protected void initListener() {
        super.initListener();
        et_user_name.addTextChangedListener(watcher);
        et_password.addTextChangedListener(watcher);
    }

    @OnClick(R.id.btn_login)
    public void clickLoginEvent(View view) {

        String userName = ViewUtils.getEditTextString(et_user_name);
        if(TextUtils.isEmpty(userName)) {
            ToastUtil.showShort(mContext, R.string.user_name_not_empty);
            return;
        }

        String password = ViewUtils.getEditTextString(et_password);
        if(TextUtils.isEmpty(password)) {
            ToastUtil.showShort(mContext, R.string.password_not_empty);
            return;
        }

        mPresenter.loginByName(userName, password);
    }

    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        setTitleText(R.string.login);
        showTitleLeftView(true);
    }

    @Override
    protected void onTitileLeftClick(View backwardView) {
        super.onTitileLeftClick(backwardView);
        finish();
    }

    @Override
    protected void initData() {
        ViewUtils.updateEditText(et_user_name,
                AppUser.getAppUser().getLastLoginUserName());
    }

    @Override
    public void loginFail(String errorMsg) {
        ToastUtil.showShort(mContext, errorMsg);
    }

    @Override
    public void loginSuccess(String result) {
        ActivitysManager.getInstance().getActivity(LoginActivity.class).finish();
        MainActivity.startMainActivity(this);
        finish();
    }
}
