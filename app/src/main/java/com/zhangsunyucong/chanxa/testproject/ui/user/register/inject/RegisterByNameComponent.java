package com.zhangsunyucong.chanxa.testproject.ui.user.register.inject;

import com.zhangsunyucong.chanxa.testproject.base.AppComponent;
import com.zhangsunyucong.chanxa.testproject.base.scope.ActivityScope;
import com.zhangsunyucong.chanxa.testproject.ui.user.register.RegisterByNameActivity;

import dagger.Component;

/**
 * Created by hyj on 2017/8/9 0009.
 */

@ActivityScope
@Component(modules = RegisterByNameModule.class, dependencies = AppComponent.class)
public interface RegisterByNameComponent {

    void inject(RegisterByNameActivity activity);

}
