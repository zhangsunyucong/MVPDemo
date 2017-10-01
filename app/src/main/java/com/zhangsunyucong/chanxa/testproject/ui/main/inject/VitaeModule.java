package com.zhangsunyucong.chanxa.testproject.ui.main.inject;

import com.zhangsunyucong.chanxa.testproject.base.scope.FragmentScope;
import com.zhangsunyucong.chanxa.testproject.ui.main.MainModel;
import com.zhangsunyucong.chanxa.testproject.ui.main.vitae.VitaeContract;
import com.zhangsunyucong.chanxa.testproject.ui.main.vitae.VitaePresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hyj on 2017/8/14 0014.
 */

@Module
public class VitaeModule {

    private VitaeContract.View mView;

    public VitaeModule(VitaeContract.View view) {
        mView = view;
    }

    @FragmentScope
    @Provides
    public VitaeContract.View provideView() {
        return mView;
    }

    @FragmentScope
    @Provides
    public VitaeContract.Presenter providePresenter(VitaePresenter presenter) {
        return presenter;
    }

    @FragmentScope
    @Provides
    public VitaeContract.Model provideModel(MainModel model) {
        return model;
    }

}
