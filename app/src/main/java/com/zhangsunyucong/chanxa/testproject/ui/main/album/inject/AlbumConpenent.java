package com.zhangsunyucong.chanxa.testproject.ui.main.album.inject;

import com.zhangsunyucong.chanxa.testproject.base.AppComponent;
import com.zhangsunyucong.chanxa.testproject.base.scope.ActivityScope;
import com.zhangsunyucong.chanxa.testproject.ui.main.album.AlbumActivity;

import dagger.Component;

/**
 * Created by zhangsunyucong on 2017/9/17.
 */
@ActivityScope
@Component(modules = AlbumModule.class, dependencies = AppComponent.class)
public interface AlbumConpenent {
    void inject(AlbumActivity activity);
}
