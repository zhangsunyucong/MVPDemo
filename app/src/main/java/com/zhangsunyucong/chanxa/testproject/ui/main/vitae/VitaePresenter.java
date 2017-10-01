package com.zhangsunyucong.chanxa.testproject.ui.main.vitae;

import com.zhangsunyucong.chanxa.testproject.App;
import com.zhangsunyucong.chanxa.testproject.R;
import com.example.librarys.utils.NetworkUtils;
import com.zhangsunyucong.chanxa.testproject.Utils.RxLifeCycleUtils;
import com.example.librarys.utils.ValidateUtils;
import com.zhangsunyucong.chanxa.testproject.base.mvp.BasePresenter;
import com.zhangsunyucong.chanxa.testproject.database.bean.VitaeInfo;
import com.zhangsunyucong.chanxa.testproject.database.common.BaseObjectResult;
import com.zhangsunyucong.chanxa.testproject.database.common.ErrorCodeUtils;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by hyj on 2017/8/14 0014.
 */

public class VitaePresenter extends BasePresenter<VitaeContract.Model, VitaeContract.View>
        implements VitaeContract.Presenter {

    @Inject
    public VitaePresenter() {}

    @Override
    public void getVitaeInfo(String userId) {

        if(!NetworkUtils.isAvailableByPing(App.getApp())) {
            mView.getVitaeInfoFail(App.getApp().getString(R.string.network_not_connect));
            return;
        }

        mModel.getVitaeInfo(encryptByServerPublicKey(userId))
                .compose(RxLifeCycleUtils.<BaseObjectResult<VitaeInfo>>bindToLifecycle(mView))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseObjectResult<VitaeInfo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(BaseObjectResult<VitaeInfo> vitaeInfoBaseObjectResult) {
                        if(ValidateUtils.checkNotNULL(vitaeInfoBaseObjectResult)
                                && vitaeInfoBaseObjectResult.status == ErrorCodeUtils.SUCCUSS) {
                            mView.getVitaeInfoSuccess(vitaeInfoBaseObjectResult.data);
                        } else {
                            if(ValidateUtils.checkNotNULL(vitaeInfoBaseObjectResult)) {
                                mView.getVitaeInfoFail(vitaeInfoBaseObjectResult.msg);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                            mView.getVitaeInfoFail(e.getLocalizedMessage());
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
