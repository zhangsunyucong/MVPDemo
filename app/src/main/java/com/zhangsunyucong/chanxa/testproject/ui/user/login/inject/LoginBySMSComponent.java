package com.zhangsunyucong.chanxa.testproject.ui.user.login.inject;

import com.zhangsunyucong.chanxa.testproject.base.AppComponent;
import com.zhangsunyucong.chanxa.testproject.base.scope.ActivityScope;
import com.zhangsunyucong.chanxa.testproject.ui.user.login.LoginBySMSActivity;

import dagger.Component;

/**
 * Created by hyj on 2017/8/9 0009.
 */

@ActivityScope
@Component(modules = LoginBySMSModule.class, dependencies = AppComponent.class)
public interface LoginBySMSComponent {
    void inject(LoginBySMSActivity activity);
}
