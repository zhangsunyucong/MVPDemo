package com.zhangsunyucong.chanxa.testproject.ui.user.login.inject;

import com.zhangsunyucong.chanxa.testproject.base.scope.ActivityScope;
import com.zhangsunyucong.chanxa.testproject.ui.user.UserModel;
import com.zhangsunyucong.chanxa.testproject.ui.user.login.LoginBySMSCodeContract;
import com.zhangsunyucong.chanxa.testproject.ui.user.login.LoginBySMSPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hyj on 2017/8/9 0009.
 */

@Module
public class LoginBySMSModule {

    private LoginBySMSCodeContract.View mView;

    public LoginBySMSModule(LoginBySMSCodeContract.View view) {
        mView = view;
    }

    @ActivityScope
    @Provides
    public LoginBySMSCodeContract.View provideView() {
        return mView;
    }

    @ActivityScope
    @Provides
    public LoginBySMSCodeContract.Presenter providePresenter(LoginBySMSPresenter presenter) {
        return presenter;
    }

    @ActivityScope
    @Provides
    public LoginBySMSCodeContract.Model provideModel(UserModel userModel) {
        return userModel;
    }
}
