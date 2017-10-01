package com.zhangsunyucong.chanxa.testproject.ui.user.login;

import com.zhangsunyucong.chanxa.testproject.base.mvp.IModel;
import com.zhangsunyucong.chanxa.testproject.base.mvp.IPresenter;
import com.zhangsunyucong.chanxa.testproject.base.mvp.IView;
import com.zhangsunyucong.chanxa.testproject.database.common.BaseObjectResult;
import com.zhangsunyucong.chanxa.testproject.database.common.StringResult;

import io.reactivex.Observable;

/**
 * Created by hyj on 2017/8/9 0009.
 */

public interface LoginBySMSCodeContract {

    interface View extends IView {
        void loginSuccess(String result);
        void loginFail(String errorMsg);
        void requestSmsCodeSuccess();
        void requestSmsCodeFail(String errorMsg);
    }

    interface Presenter extends IPresenter {
        void loginBySMSCode(String userPhoneNum, String smsCode);
        void requestSmsCode(String userPhoneNum);
    }

    interface Model extends IModel {
        Observable<BaseObjectResult<userLoginResult>> loginBySMSCode(String userPhoneNum, String smsCode);
        Observable<StringResult> requestSmsCode(String userPhoneNum);
    }
}
