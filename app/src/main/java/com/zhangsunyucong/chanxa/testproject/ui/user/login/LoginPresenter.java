package com.zhangsunyucong.chanxa.testproject.ui.user.login;

import android.app.Application;
import android.util.Log;

import com.zhangsunyucong.chanxa.testproject.App;
import com.zhangsunyucong.chanxa.testproject.R;
import com.example.librarys.utils.NetworkUtils;
import com.zhangsunyucong.chanxa.testproject.Utils.RxLifeCycleUtils;
import com.zhangsunyucong.chanxa.testproject.base.mvp.BasePresenter;
import com.zhangsunyucong.chanxa.testproject.base.scope.ActivityScope;
import com.zhangsunyucong.chanxa.testproject.database.common.BaseObjectResult;
import com.zhangsunyucong.chanxa.testproject.database.common.ErrorCodeUtils;
import com.zhangsunyucong.chanxa.testproject.ui.user.AppUser;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/**
 * Created by Administrator on 2017/1/3.
 */
@ActivityScope
public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View> implements LoginContract.Presenter {

    @Inject
    Application application;

    private RxErrorHandler mErrorHandler;

    @Inject
    LoginPresenter() {
    }

    /**
     * 登录**
     */
    @Override
    public void login(String userPhoneNum, String password) {

        if(!NetworkUtils.isAvailableByPing(App.getApp())) {
            mView.loginFail(App.getApp().getString(R.string.network_not_connect));
            return;
        }

        mModel.loginByMobile(encryptByServerPublicKey(userPhoneNum),
                encryptByServerPublicKey(password))
                .compose(RxLifeCycleUtils.<BaseObjectResult<userLoginResult>>bindToLifecycle(mView))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseObjectResult<userLoginResult>>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        showWaitProgress();
                        addDisposable(d);
                    }
//
                    @Override
                    public void onNext(BaseObjectResult<userLoginResult> result) {
                        Log.d("LoginPresenter", "onNext");
                        if(result != null) {
                          //  RSAUtils.decryptByPrivateKey(result.msg)
                            if(ErrorCodeUtils.SUCCUSS == result.status) {
                                mView.loginSuccess(result.msg);
                                AppUser.getAppUser().setuserLoginResult(result.data);
                            } else {
                                mView.loginFail(result.msg);
                            }
                        }
                        hideWaitProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("LoginPresenter", "onError");
                        mView.loginFail(e.getMessage());
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
        super.onDestroy();
    }


}
