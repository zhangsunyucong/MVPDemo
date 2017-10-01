package com.zhangsunyucong.chanxa.testproject.ui.main.album;

import com.zhangsunyucong.chanxa.testproject.base.mvp.BaseModel;
import com.zhangsunyucong.chanxa.testproject.database.common.BaseObjectResult;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by zhangsunyucong on 2017/9/17.
 */

public class AlbumModel extends BaseModel implements AlbumConstract.Model {

    @Inject
    public AlbumModel() {
       // mVitaeService = mServiceManager.mVitaeService;
    }

    @Override
    public Observable<BaseObjectResult<AlbumEntity>> addAlbum(String userId, AlbumEntity albumEntity) {
        return mServiceManager.mVitaeService.addAlbum(userId, albumEntity.newTempUrl);
    }

    @Override
    public Observable<BaseObjectResult<AlbumEntity>> getAlbumInfo(String userId) {
        return mServiceManager.mVitaeService.getAlbumInfo(userId);
    }

    @Override
    public Observable<BaseObjectResult<AlbumEntity>> deletePhoto(String userId, AlbumEntity albumEntity) {
        return mServiceManager.mVitaeService.deletePhoto(userId, albumEntity.newTempUrl);
    }
}
