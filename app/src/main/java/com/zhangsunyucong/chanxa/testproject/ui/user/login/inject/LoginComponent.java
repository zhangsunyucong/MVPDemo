package com.zhangsunyucong.chanxa.testproject.ui.user.login.inject;

import com.zhangsunyucong.chanxa.testproject.base.AppComponent;
import com.zhangsunyucong.chanxa.testproject.base.scope.ActivityScope;
import com.zhangsunyucong.chanxa.testproject.ui.user.login.LoginActivity;

import dagger.Component;

@ActivityScope
@Component(modules = LoginModule.class, dependencies = AppComponent.class)
public interface LoginComponent {

    void inject(LoginActivity activity);

}
