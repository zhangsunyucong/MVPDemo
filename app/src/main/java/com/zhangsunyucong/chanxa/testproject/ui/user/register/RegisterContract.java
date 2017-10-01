package com.zhangsunyucong.chanxa.testproject.ui.user.register;

import com.zhangsunyucong.chanxa.testproject.base.mvp.IModel;
import com.zhangsunyucong.chanxa.testproject.base.mvp.IPresenter;
import com.zhangsunyucong.chanxa.testproject.base.mvp.IView;
import com.zhangsunyucong.chanxa.testproject.database.common.StringResult;

import io.reactivex.Observable;

/**
 * Created by hyj on 2017/8/8 0008.
 */

public interface RegisterContract {

    interface View extends IView {
        void registerSuccess();
        void registerFail(String errorMsg);
        void requestSmsCodeSuccess();
        void requestSmsCodeFail(String errorMsg);
    }

    interface Presenter extends IPresenter {
         void registerByMobile(String userPhoneNum, String nickName,
                               String smsCode, String password);
         void requestSmsCode(String userPhoneNum);
    }

    interface Model extends IModel {
        Observable<StringResult> requestSmsCode(String userPhoneNum);
        Observable<StringResult> registerByMobile(String userPhoneNum, String nickName,
                                                  String smsCode, String password);
    }
}
