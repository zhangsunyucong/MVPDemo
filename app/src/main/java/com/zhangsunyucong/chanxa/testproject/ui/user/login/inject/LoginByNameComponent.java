package com.zhangsunyucong.chanxa.testproject.ui.user.login.inject;

import com.zhangsunyucong.chanxa.testproject.base.AppComponent;
import com.zhangsunyucong.chanxa.testproject.base.scope.ActivityScope;
import com.zhangsunyucong.chanxa.testproject.ui.user.login.LoginByNameActivity;

import dagger.Component;

/**
 * Created by hyj on 2017/8/9 0009.
 */
@ActivityScope
@Component(modules = LoginByNameModule.class, dependencies = AppComponent.class)
public interface LoginByNameComponent {

    void inject(LoginByNameActivity activity);

}
