package com.zhangsunyucong.chanxa.testproject.ui.main.inject;

import com.zhangsunyucong.chanxa.testproject.base.AppComponent;
import com.zhangsunyucong.chanxa.testproject.base.scope.FragmentScope;
import com.zhangsunyucong.chanxa.testproject.ui.main.vitae.VitaeFragment;

import dagger.Component;

/**
 * Created by hyj on 2017/8/14 0014.
 */

@FragmentScope
@Component(modules = VitaeModule.class, dependencies = AppComponent.class)
public interface VitaeComponent {
    void inject(VitaeFragment fragment);
}
