package com.zhangsunyucong.chanxa.testproject.ui.setting.feedback;

import com.zhangsunyucong.chanxa.testproject.base.mvp.IModel;
import com.zhangsunyucong.chanxa.testproject.base.mvp.IPresenter;
import com.zhangsunyucong.chanxa.testproject.base.mvp.IView;
import com.zhangsunyucong.chanxa.testproject.database.common.StringResult;

import io.reactivex.Observable;

/**
 * Created by hyj on 2017/8/17 0017.
 */

public interface FeedbackContract {

    interface View extends IView {
        void feedbackSuccess();
        void feedbackFail(String msg);
    }

    interface Presenter extends IPresenter {
        void feedback(String clientType, String content, String userPhoneNum);
    }

    interface Model extends IModel {
        Observable<StringResult> feedback(String clientType, String content, String userPhoneNum);
    }
}
