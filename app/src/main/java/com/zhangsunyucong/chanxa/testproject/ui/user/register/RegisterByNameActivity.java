package com.zhangsunyucong.chanxa.testproject.ui.user.register;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zhangsunyucong.chanxa.testproject.R;
import com.example.librarys.utils.ToastUtil;
import com.example.librarys.utils.ViewUtils;
import com.zhangsunyucong.chanxa.testproject.base.AppComponent;
import com.zhangsunyucong.chanxa.testproject.base.mvp.BaseActivity;
import com.zhangsunyucong.chanxa.testproject.manager.ARouterConstant;
import com.zhangsunyucong.chanxa.testproject.ui.user.login.LoginActivity;
import com.zhangsunyucong.chanxa.testproject.ui.user.register.inject.DaggerRegisterByNameComponent;
import com.zhangsunyucong.chanxa.testproject.ui.user.register.inject.RegisterByNameModule;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = ARouterConstant.PATH_ACTIVITY_REGISTER_BY_NAME)
public class RegisterByNameActivity extends BaseActivity<RegisterByNameContract.Presenter>
    implements RegisterByNameContract.View{

    @BindView(R.id.et_user_name)
    EditText et_user_name;

    @BindView(R.id.et_password)
    EditText et_password;

    @BindView(R.id.btn_register)
    Button btn_register;

    public static void startRegisterByNameActivity() {
        ARouter.getInstance()
                .build(ARouterConstant.PATH_ACTIVITY_REGISTER_BY_NAME)
                .navigation();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register_by_name;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerRegisterByNameComponent
                .builder()
                .appComponent(appComponent)
                .registerByNameModule(new RegisterByNameModule(this))
                .build()
                .inject(this);
    }

    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            btn_register.setEnabled(canRegister());
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    private boolean canRegister() {
        int nickNameLength = et_user_name.getText().toString().trim().length();
        int pwdLength = et_password.getText().toString().trim().length();
        if (nickNameLength > 0 && pwdLength > 0 ) {
            return true;
        }
        return false;
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
    protected void initListener() {
        super.initListener();
        et_user_name.addTextChangedListener(watcher);
        et_password.addTextChangedListener(watcher);
    }

    @Override
    protected void onTitileLeftClick(View backwardView) {
        super.onTitileLeftClick(backwardView);
        finish();
    }

    @OnClick({R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                clickRegisterEvent();
                break;
        }
    }

    private void clickRegisterEvent() {

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

        if(mPresenter != null) {
            mPresenter.registerByName(userName, password);
        }
    }

    @Override
    public void registerSuccess() {
        LoginActivity.startLoginActivity(RegisterByNameActivity.this, true);
    }

    @Override
    public void registerFail(String errorMsg) {
        ToastUtil.showShort(mContext, errorMsg);
    }
}
