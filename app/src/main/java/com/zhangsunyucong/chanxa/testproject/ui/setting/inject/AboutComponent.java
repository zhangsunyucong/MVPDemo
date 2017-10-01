package com.zhangsunyucong.chanxa.testproject.ui.setting.inject;

import com.zhangsunyucong.chanxa.testproject.base.AppComponent;
import com.zhangsunyucong.chanxa.testproject.base.scope.ActivityScope;
import com.zhangsunyucong.chanxa.testproject.ui.setting.AboutActivity;

import dagger.Component;

/**
 * Created by hyj on 2017/8/17 0017.
 */
@ActivityScope
@Component(modules = AboutModule.class, dependencies = AppComponent.class)
public interface AboutComponent {
    void inject(AboutActivity activity);
}
