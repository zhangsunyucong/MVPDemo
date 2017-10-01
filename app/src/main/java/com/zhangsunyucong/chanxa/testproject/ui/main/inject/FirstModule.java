package com.zhangsunyucong.chanxa.testproject.ui.main.inject;

import com.zhangsunyucong.chanxa.testproject.base.scope.FragmentScope;
import com.zhangsunyucong.chanxa.testproject.ui.main.MainModel;
import com.zhangsunyucong.chanxa.testproject.ui.main.firstpage.FirstContract;
import com.zhangsunyucong.chanxa.testproject.ui.main.firstpage.FirstPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hyj on 2017/8/15 0015.
 */

@Module
public class FirstModule {

    private FirstContract.View mView;

    public FirstModule(FirstContract.View view) {
        mView = view;
    }

    @FragmentScope
    @Provides
    public FirstContract.View provideView() {
        return mView;
    }

    @FragmentScope
    @Provides
    public FirstContract.Presenter providerPresenter(FirstPresenter presenter) {
        return presenter;
    }

    @FragmentScope
    @Provides
    public FirstContract.Model providerModel(MainModel model) {
        return model;
    }

}
