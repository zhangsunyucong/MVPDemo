package com.zhangsunyucong.chanxa.testproject.ui.main.inject;

import com.zhangsunyucong.chanxa.testproject.base.AppComponent;
import com.zhangsunyucong.chanxa.testproject.base.scope.FragmentScope;
import com.zhangsunyucong.chanxa.testproject.ui.main.firstpage.FirstFragment;

import dagger.Component;

/**
 * Created by hyj on 2017/8/15 0015.
 */
@FragmentScope
@Component(modules = FirstModule.class, dependencies = AppComponent.class)
public interface FirstComponent {

    void inject(FirstFragment fragment);

}
