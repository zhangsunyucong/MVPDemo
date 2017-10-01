package com.zhangsunyucong.chanxa.testproject.ui.setting.inject;

import com.zhangsunyucong.chanxa.testproject.base.scope.ActivityScope;
import com.zhangsunyucong.chanxa.testproject.ui.setting.AboutContract;
import com.zhangsunyucong.chanxa.testproject.ui.setting.AboutPresenter;
import com.zhangsunyucong.chanxa.testproject.ui.setting.SettingModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hyj on 2017/8/17 0017.
 */
@Module
public class AboutModule {

    private AboutContract.View mView;

    public AboutModule(AboutContract.View view) {
        mView = view;
    }

    @ActivityScope
    @Provides
    public AboutContract.View provideView() {
        return mView;
    }

    @ActivityScope
    @Provides
    public AboutContract.Presenter providePresenter(AboutPresenter presenter) {
        return presenter;
    }

    @ActivityScope
    @Provides
    public AboutContract.Model provideModel(SettingModel model) {
        return model;
    }
}
