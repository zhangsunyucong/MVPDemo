package com.zhangsunyucong.chanxa.testproject.ui.main.album;

import com.zhangsunyucong.chanxa.testproject.base.mvp.IModel;
import com.zhangsunyucong.chanxa.testproject.base.mvp.IPresenter;
import com.zhangsunyucong.chanxa.testproject.base.mvp.IView;
import com.zhangsunyucong.chanxa.testproject.database.common.BaseObjectResult;

import io.reactivex.Observable;

/**
 * Created by zhangsunyucong on 2017/9/17.
 */

public interface AlbumConstract {

    interface View extends IView {
        void getAlbumInfoFail(String msg);
        void getAlbumIInfoSuccess(AlbumEntity albumEntitys);
        void addAlbumInfoFail(String msg);
        void addAlbumISuccess(AlbumEntity albumEntity);
        void deletePhotoFail(String msg);
        void deletePhotoSuccess(AlbumEntity albumEntity, int position);
    }

    interface Presenter extends IPresenter {
        void getAlbumInfo(String userId);
        void addAlbum(String userId, AlbumEntity albumEntity);
        void deletePhoto(String userId, AlbumEntity albumEntity, int position);
    }

    interface Model extends IModel {
        Observable<BaseObjectResult<AlbumEntity>> getAlbumInfo(String userId);
        Observable<BaseObjectResult<AlbumEntity>> addAlbum(String userId, AlbumEntity albumEntity);
        Observable<BaseObjectResult<AlbumEntity>> deletePhoto(String userId, AlbumEntity albumEntity);
    }

}
