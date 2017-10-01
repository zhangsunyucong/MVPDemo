package com.zhangsunyucong.chanxa.testproject.ui.user.register.inject;

import com.zhangsunyucong.chanxa.testproject.base.AppComponent;
import com.zhangsunyucong.chanxa.testproject.base.scope.ActivityScope;
import com.zhangsunyucong.chanxa.testproject.ui.user.register.RegisterActivity;

import dagger.Component;

/**
 * Created by hyj on 2017/8/8 0008.
 */

@ActivityScope
@Component(modules = RegisterModule.class, dependencies = AppComponent.class)
public interface RegisterComponent {

    void inject(RegisterActivity activity);
}
