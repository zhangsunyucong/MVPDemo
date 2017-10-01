package com.zhangsunyucong.chanxa.testproject.ui.user.login.inject;

import com.zhangsunyucong.chanxa.testproject.base.scope.ActivityScope;
import com.zhangsunyucong.chanxa.testproject.ui.user.UserModel;
import com.zhangsunyucong.chanxa.testproject.ui.user.login.LoginContract;
import com.zhangsunyucong.chanxa.testproject.ui.user.login.LoginPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {
    private LoginContract.View view;

    public LoginModule(LoginContract.View view) {
        this.view = view;
    }

   //M层
    @ActivityScope
    @Provides
    LoginContract.Model provideModel(UserModel model) {
        return model;
    }

    //V层
    @ActivityScope
    @Provides
    LoginContract.View provideView() {
        return this.view;
    }

    //C层
    @ActivityScope
    @Provides
    LoginContract.Presenter providePresenter(LoginPresenter presenter) {
        return presenter;
    }

    //数据实体
   /* @ActivityScope
    @Provides
    public UserEntity provideUserEntity() {
        return new UserEntity();
    }*/
}
