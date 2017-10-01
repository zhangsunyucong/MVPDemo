package com.zhangsunyucong.chanxa.testproject.ui.setting.inject;

import com.zhangsunyucong.chanxa.testproject.base.scope.ActivityScope;
import com.zhangsunyucong.chanxa.testproject.ui.setting.SettingModel;
import com.zhangsunyucong.chanxa.testproject.ui.setting.feedback.FeedbackContract;
import com.zhangsunyucong.chanxa.testproject.ui.setting.feedback.FeedbackPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hyj on 2017/8/17 0017.
 */
@Module
public class FeedbackModule {

    private FeedbackContract.View mView;

    public FeedbackModule(FeedbackContract.View view) {
        mView = view;
    }

    @ActivityScope
    @Provides
    public FeedbackContract.View provideView() {
        return mView;
    }

    @ActivityScope
    @Provides
    public FeedbackContract.Presenter providePresenter(FeedbackPresenter presenter) {
        return presenter;
    }

    @ActivityScope
    @Provides
    public FeedbackContract.Model provideModel(SettingModel model) {
        return model;
    }
}
