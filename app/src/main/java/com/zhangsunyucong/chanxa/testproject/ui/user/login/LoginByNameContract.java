package com.zhangsunyucong.chanxa.testproject.ui.user.login;

import com.zhangsunyucong.chanxa.testproject.base.mvp.IModel;
import com.zhangsunyucong.chanxa.testproject.base.mvp.IPresenter;
import com.zhangsunyucong.chanxa.testproject.base.mvp.IView;
import com.zhangsunyucong.chanxa.testproject.database.common.BaseObjectResult;

import io.reactivex.Observable;

/**
 * Created by hyj on 2017/8/9 0009.
 */

public interface LoginByNameContract {

    interface View extends IView {
        void loginSuccess(String result);
        void loginFail(String errorMsg);
    }

    interface Presenter extends IPresenter {
        void loginByName(String userName, String password);
    }

    interface Model extends IModel {
        Observable<BaseObjectResult<userLoginResult>> loginByName(String userName, String password);
    }


}
