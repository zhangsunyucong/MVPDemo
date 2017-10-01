package com.zhangsunyucong.chanxa.testproject.ui.user.login;

import com.zhangsunyucong.chanxa.testproject.base.mvp.IModel;
import com.zhangsunyucong.chanxa.testproject.base.mvp.IPresenter;
import com.zhangsunyucong.chanxa.testproject.base.mvp.IView;
import com.zhangsunyucong.chanxa.testproject.database.common.BaseObjectResult;

import io.reactivex.Observable;

public interface LoginContract {

    interface View extends IView {
        void loginSuccess(String result);
        void loginFail(String errorMsg);
    }

    interface Presenter extends IPresenter {
        void login(String userPhoneNum, String password);
    }

    interface Model extends IModel {
        Observable<BaseObjectResult<userLoginResult>>
        loginByMobile(String userPhoneNum, String password);
    }
}
