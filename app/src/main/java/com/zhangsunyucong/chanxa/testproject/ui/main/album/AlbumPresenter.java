package com.zhangsunyucong.chanxa.testproject.ui.main.album;

import android.util.Log;

import com.zhangsunyucong.chanxa.testproject.App;
import com.zhangsunyucong.chanxa.testproject.R;
import com.example.librarys.utils.NetworkUtils;
import com.zhangsunyucong.chanxa.testproject.Utils.RxLifeCycleUtils;
import com.zhangsunyucong.chanxa.testproject.base.mvp.BasePresenter;
import com.zhangsunyucong.chanxa.testproject.database.common.BaseObjectResult;
import com.zhangsunyucong.chanxa.testproject.database.common.ErrorCodeUtils;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

/**
 * Created by zhangsunyucong on 2017/9/17.
 */

public class AlbumPresenter extends BasePresenter<AlbumConstract.Model, AlbumConstract.View> implements AlbumConstract.Presenter {


    @Inject
    public AlbumPresenter() {}

    @Override
    public void getAlbumInfo(String userId) {
        if(!NetworkUtils.isAvailableByPing(App.getApp())) {
            mView.getAlbumInfoFail(App.getApp().getString(R.string.network_not_connect));
            return;
        }

        mModel.getAlbumInfo(encryptByServerPublicKey(userId))
                .retryWhen(new RetryWithDelay(3, 2))
                .compose(RxLifeCycleUtils.<BaseObjectResult<AlbumEntity>>bindToLifecycle(mView))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseObjectResult<AlbumEntity>>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        showWaitProgress();
                        addDisposable(d);
                    }
                    //
                    @Override
                    public void onNext(BaseObjectResult<AlbumEntity> result) {
                        Log.d("LoginPresenter", "onNext");
                        if(result != null) {
                            //  RSAUtils.decryptByPrivateKey(result.msg)
                            if(ErrorCodeUtils.SUCCUSS == result.status) {
                                mView.getAlbumIInfoSuccess(result.data);
                            } else {
                                mView.getAlbumInfoFail(result.msg);
                            }
                        }
                        hideWaitProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("LoginPresenter", "onError");
                        mView.getAlbumInfoFail(e.getMessage());
                        hideWaitProgress();
                    }

                    @Override
                    public void onComplete() {
                        Log.d("LoginPresenter", "onComplete");
                    }
                });
    }

    @Override
    public void addAlbum(String userId, final AlbumEntity albumEntity) {
        if(!NetworkUtils.isAvailableByPing(App.getApp())) {
            mView.addAlbumInfoFail(App.getApp().getString(R.string.network_not_connect));
            return;
        }

        mModel.addAlbum(encryptByServerPublicKey(userId),
                albumEntity)
                .retryWhen(new RetryWithDelay(3, 2))
                .compose(RxLifeCycleUtils.<BaseObjectResult<AlbumEntity>>bindToLifecycle(mView))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseObjectResult<AlbumEntity>>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        showWaitProgress();
                        addDisposable(d);
                    }
                    //
                    @Override
                    public void onNext(BaseObjectResult<AlbumEntity> result) {
                        Log.d("LoginPresenter", "onNext");
                        if(result != null) {
                            //  RSAUtils.decryptByPrivateKey(result.msg)
                            if(ErrorCodeUtils.SUCCUSS == result.status) {
                                mView.addAlbumISuccess(albumEntity);
                            } else {
                                mView.addAlbumInfoFail(result.msg);
                            }
                        }
                        hideWaitProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("LoginPresenter", "onError");
                        mView.addAlbumInfoFail(e.getMessage());
                        hideWaitProgress();
                    }

                    @Override
                    public void onComplete() {
                        Log.d("LoginPresenter", "onComplete");
                    }
                });
    }

    @Override
    public void deletePhoto(String userId, final AlbumEntity albumEntity, final int position) {
        if(!NetworkUtils.isAvailableByPing(App.getApp())) {
            mView.deletePhotoFail(App.getApp().getString(R.string.network_not_connect));
            return;
        }

        mModel.deletePhoto(encryptByServerPublicKey(userId),
                albumEntity)
                .retryWhen(new RetryWithDelay(3, 2))
                .compose(RxLifeCycleUtils.<BaseObjectResult<AlbumEntity>>bindToLifecycle(mView))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseObjectResult<AlbumEntity>>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        showWaitProgress();
                        addDisposable(d);
                    }
                    //
                    @Override
                    public void onNext(BaseObjectResult<AlbumEntity> result) {
                        Log.d("LoginPresenter", "onNext");
                        if(result != null) {
                            //  RSAUtils.decryptByPrivateKey(result.msg)
                            if(ErrorCodeUtils.SUCCUSS == result.status) {
                                mView.deletePhotoSuccess(albumEntity, position);
                            } else {
                                mView.deletePhotoFail(result.msg);
                            }
                        }
                        hideWaitProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("LoginPresenter", "onError");
                        mView.addAlbumInfoFail(e.getMessage());
                        hideWaitProgress();
                    }

                    @Override
                    public void onComplete() {
                        Log.d("LoginPresenter", "onComplete");
                    }
                });
    }

    @Override
    public void onDestroy() {

    }
}
