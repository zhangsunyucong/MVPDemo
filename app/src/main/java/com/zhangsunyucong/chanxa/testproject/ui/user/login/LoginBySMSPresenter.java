package com.zhangsunyucong.chanxa.testproject.ui.user.login;

import com.zhangsunyucong.chanxa.testproject.App;
import com.zhangsunyucong.chanxa.testproject.R;
import com.example.librarys.utils.NetworkUtils;
import com.zhangsunyucong.chanxa.testproject.Utils.RxLifeCycleUtils;
import com.example.librarys.utils.ValidateUtils;
import com.zhangsunyucong.chanxa.testproject.base.mvp.BasePresenter;
import com.zhangsunyucong.chanxa.testproject.database.common.BaseObjectResult;
import com.zhangsunyucong.chanxa.testproject.database.common.ErrorCodeUtils;
import com.zhangsunyucong.chanxa.testproject.database.common.StringResult;
import com.zhangsunyucong.chanxa.testproject.ui.user.AppUser;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by hyj on 2017/8/9 0009.
 */

public class LoginBySMSPresenter extends BasePresenter<LoginBySMSCodeContract.Model, LoginBySMSCodeContract.View>
        implements LoginBySMSCodeContract.Presenter {

    @Inject
    public LoginBySMSPresenter() {}

    @Override
    public void loginBySMSCode(String userPhoneNum, final String smsCode) {

        if(!NetworkUtils.isAvailableByPing(App.getApp())) {
            mView.loginFail(App.getApp().getString(R.string.network_not_connect));
            return;
        }

        mModel.loginBySMSCode(encryptByServerPublicKey(userPhoneNum), smsCode)
                .compose(RxLifeCycleUtils.<BaseObjectResult<userLoginResult>>bindToLifecycle(mView))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseObjectResult<userLoginResult>>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        showWaitProgress();
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(BaseObjectResult<userLoginResult> stringResult) {
                        if(stringResult.status == ErrorCodeUtils.SUCCUSS) {
                            mView.loginSuccess(stringResult.msg);
                            AppUser.getAppUser().setuserLoginResult(stringResult.data);
                        } else {
                            mView.loginFail(stringResult.msg);
                        }
                        hideWaitProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.loginFail(e.getMessage());
                        hideWaitProgress();
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
                        if(ValidateUtils.checkNotNULL(stringResult)) {
                            if(stringResult.status == ErrorCodeUtils.SUCCUSS) {
                                mView.requestSmsCodeSuccess();
                            } else {
                                mView.requestSmsCodeFail(stringResult.msg);
                            }
                        }
                        hideWaitProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.loginFail(e.getMessage());
                        hideWaitProgress();
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
