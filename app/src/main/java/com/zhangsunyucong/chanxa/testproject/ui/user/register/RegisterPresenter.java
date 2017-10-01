package com.zhangsunyucong.chanxa.testproject.ui.user.register;

import com.zhangsunyucong.chanxa.testproject.App;
import com.zhangsunyucong.chanxa.testproject.R;
import com.example.librarys.utils.NetworkUtils;
import com.zhangsunyucong.chanxa.testproject.Utils.RxLifeCycleUtils;
import com.example.librarys.utils.ValidateUtils;
import com.zhangsunyucong.chanxa.testproject.base.mvp.BasePresenter;
import com.zhangsunyucong.chanxa.testproject.database.common.ErrorCodeUtils;
import com.zhangsunyucong.chanxa.testproject.database.common.StringResult;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by hyj on 2017/8/8 0008.
 */

public class RegisterPresenter extends BasePresenter<RegisterContract.Model, RegisterContract.View>
    implements RegisterContract.Presenter {

    @Inject
    public RegisterPresenter() {}


    @Override
    public void registerByMobile(String userPhoneNum, String nickName, final String smsCode, String password) {

        if(!NetworkUtils.isAvailableByPing(App.getApp())) {
            mView.registerFail(App.getApp().getString(R.string.network_not_connect));
            return;
        }

        mModel.registerByMobile(encryptByServerPublicKey(userPhoneNum),
                encryptByServerPublicKey(nickName), smsCode,
                encryptByServerPublicKey(password))
                .compose(RxLifeCycleUtils.<StringResult>bindToLifecycle(mView))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StringResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        showWaitProgress();
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(StringResult stringResult) {
                        if(ValidateUtils.checkNotNULL(stringResult)) {
                            if(ErrorCodeUtils.SUCCUSS == stringResult.status) {
                                mView.registerSuccess();
                            } else {
                                mView.registerFail(stringResult.msg);
                            }
                        }
                        hideWaitProgress();

                    }

                    @Override
                    public void onError(Throwable e) {
                        hideWaitProgress();
                        mView.registerFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void requestSmsCode(String userPhoneNum) {

        if(!NetworkUtils.isAvailableByPing(App.getApp())) {
            mView.requestSmsCodeFail(App.getApp().getString(R.string.network_not_connect));
            return;
        }

        mModel.requestSmsCode(encryptByServerPublicKey(userPhoneNum))
                .compose(RxLifeCycleUtils.<StringResult>bindToLifecycle(mView))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StringResult>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        showWaitProgress();
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(StringResult stringResult) {

                        if(ErrorCodeUtils.SUCCUSS == stringResult.status) {
                            mView.requestSmsCodeSuccess();
                        } else {
                            mView.requestSmsCodeFail(stringResult.msg);
                        }
                        hideWaitProgress();

                    }

                    @Override
                    public void onError(Throwable e) {
                        hideWaitProgress();
                        mView.requestSmsCodeFail(e.getMessage());
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
