package com.zhangsunyucong.chanxa.testproject.ui.setting;

import com.zhangsunyucong.chanxa.testproject.App;
import com.zhangsunyucong.chanxa.testproject.R;
import com.example.librarys.utils.NetworkUtils;
import com.zhangsunyucong.chanxa.testproject.Utils.RxLifeCycleUtils;
import com.example.librarys.utils.ValidateUtils;
import com.zhangsunyucong.chanxa.testproject.base.mvp.BasePresenter;
import com.zhangsunyucong.chanxa.testproject.database.common.BaseObjectResult;
import com.zhangsunyucong.chanxa.testproject.database.common.ErrorCodeUtils;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by hyj on 2017/8/17 0017.
 */

public class AboutPresenter extends BasePresenter<AboutContract.Model, AboutContract.View>
    implements AboutContract.Presenter {

    @Inject
    public AboutPresenter() {}

    @Override
    public void getUpdateInfo() {

        if(!NetworkUtils.isAvailableByPing(App.getApp())) {
            mView.getUpdateInfoFail(App.getApp().getString(R.string.network_not_connect));
            return;
        }

        mModel.getUpdateInfo()
                .compose(RxLifeCycleUtils.<BaseObjectResult<UpdateEntity>>bindToLifecycle(mView))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseObjectResult<UpdateEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                        showWaitProgress();
                    }

                    @Override
                    public void onNext(BaseObjectResult<UpdateEntity> result) {
                        hideWaitProgress();
                        if(ValidateUtils.checkNotNULL(result)) {
                            if(result.status == ErrorCodeUtils.SUCCUSS) {
                                mView.getUpdateInfoSuccess(result.data);
                            } else {
                                mView.getUpdateInfoFail(result.msg);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideWaitProgress();
                        mView.getUpdateInfoFail(e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
