package com.zhangsunyucong.chanxa.testproject.ui.main.inject;

import com.zhangsunyucong.chanxa.testproject.base.scope.ActivityScope;
import com.zhangsunyucong.chanxa.testproject.ui.main.MainModel;
import com.zhangsunyucong.chanxa.testproject.ui.main.firstpage.BlogListContrat;
import com.zhangsunyucong.chanxa.testproject.ui.main.firstpage.BlogListPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hyj on 2017/8/15 0015.
 */

@Module
public class BlogListModule {

    private BlogListContrat.View mView;

    public BlogListModule(BlogListContrat.View view) {
        mView = view;
    }

    @ActivityScope
    @Provides
    public BlogListContrat.View provideView() {
        return mView;
    }

    @ActivityScope
    @Provides
    public BlogListContrat.Presenter providePresenter(BlogListPresenter presenter) {
        return presenter;
    }

    @ActivityScope
    @Provides
    public BlogListContrat.Model provideModel(MainModel model) {
        return model;
    }

}
