package com.zhangsunyucong.chanxa.testproject.ui.main.firstpage;

import com.zhangsunyucong.chanxa.testproject.base.mvp.IModel;
import com.zhangsunyucong.chanxa.testproject.base.mvp.IPresenter;
import com.zhangsunyucong.chanxa.testproject.base.mvp.IView;
import com.zhangsunyucong.chanxa.testproject.database.common.BaseArrayResult;

import io.reactivex.Observable;

/**
 * Created by hyj on 2017/8/15 0015.
 */

public interface FirstContract {

    interface View extends IView {
        void getBloggerInfoFail(String msg);
        void getBloggerInfoSuccess(BaseArrayResult<Blogger> result);
    }

    interface Presenter extends IPresenter {
        void getBloggerInfo();
    }
    interface Model extends IModel {
        Observable<BaseArrayResult<Blogger>> getBloggerInfo();
    }
}
