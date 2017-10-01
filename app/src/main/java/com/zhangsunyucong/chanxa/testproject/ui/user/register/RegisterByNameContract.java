package com.zhangsunyucong.chanxa.testproject.ui.user.register;

import com.zhangsunyucong.chanxa.testproject.base.mvp.IModel;
import com.zhangsunyucong.chanxa.testproject.base.mvp.IPresenter;
import com.zhangsunyucong.chanxa.testproject.base.mvp.IView;
import com.zhangsunyucong.chanxa.testproject.database.common.StringResult;

import io.reactivex.Observable;

/**
 * Created by hyj on 2017/8/9 0009.
 */

public interface RegisterByNameContract {

    interface View extends IView {
        void registerSuccess();
        void registerFail(String errorMsg);
    }

    interface Presenter extends IPresenter {
        void registerByName(String userName, String password);
    }

    interface Model extends IModel {
        Observable<StringResult> registerByName(String userName, String password);
    }
}
