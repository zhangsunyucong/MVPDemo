package com.zhangsunyucong.chanxa.testproject.ui.main.inject;

import com.zhangsunyucong.chanxa.testproject.base.AppComponent;
import com.zhangsunyucong.chanxa.testproject.base.scope.ActivityScope;
import com.zhangsunyucong.chanxa.testproject.ui.main.firstpage.BlogListActivity;

import dagger.Component;

/**
 * Created by hyj on 2017/8/15 0015.
 */
@ActivityScope
@Component(modules = BlogListModule.class, dependencies = AppComponent.class)
public interface BlogListComponent {

    void inject(BlogListActivity activity);
}
