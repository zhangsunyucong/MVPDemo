package com.zhangsunyucong.chanxa.testproject.ui.main.album.inject;

import com.zhangsunyucong.chanxa.testproject.base.scope.ActivityScope;
import com.zhangsunyucong.chanxa.testproject.ui.main.album.AlbumConstract;
import com.zhangsunyucong.chanxa.testproject.ui.main.album.AlbumModel;
import com.zhangsunyucong.chanxa.testproject.ui.main.album.AlbumPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhangsunyucong on 2017/9/17.
 */

@Module
public class AlbumModule {

    private AlbumConstract.View mView;

    public AlbumModule(AlbumConstract.View view) {
        mView = view;
    }

    @ActivityScope
    @Provides
    public AlbumConstract.View provideView() {
        return mView;
    }

    @ActivityScope
    @Provides
    public AlbumConstract.Presenter providePresenter(AlbumPresenter presenter) {
        return presenter;
    }

    @ActivityScope
    @Provides
    public AlbumConstract.Model provideModel(AlbumModel model) {
        return model;
    }
}
