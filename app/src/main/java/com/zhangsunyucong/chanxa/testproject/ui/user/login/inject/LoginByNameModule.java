package com.zhangsunyucong.chanxa.testproject.ui.user.login.inject;

import com.zhangsunyucong.chanxa.testproject.base.scope.ActivityScope;
import com.zhangsunyucong.chanxa.testproject.ui.user.UserModel;
import com.zhangsunyucong.chanxa.testproject.ui.user.login.LoginByNameContract;
import com.zhangsunyucong.chanxa.testproject.ui.user.login.LoginByNamePresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hyj on 2017/8/9 0009.
 */

@Module
public class LoginByNameModule {

    private LoginByNameContract.View mView;

    public LoginByNameModule(LoginByNameContract.View view) {
        mView = view;
    }

    @ActivityScope
    @Provides
    public LoginByNameContract.View provideView() {
        return mView;
    }

    @ActivityScope
    @Provides
    public LoginByNameContract.Presenter providePreserter(LoginByNamePresenter presenter) {
        return presenter;
    }

    @ActivityScope
    @Provides
    public LoginByNameContract.Model provideModel(UserModel userModel) {
        return userModel;
    }

}
