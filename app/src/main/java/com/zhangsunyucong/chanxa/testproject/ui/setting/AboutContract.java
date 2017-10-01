package com.zhangsunyucong.chanxa.testproject.ui.setting;

import com.zhangsunyucong.chanxa.testproject.base.mvp.IModel;
import com.zhangsunyucong.chanxa.testproject.base.mvp.IPresenter;
import com.zhangsunyucong.chanxa.testproject.base.mvp.IView;
import com.zhangsunyucong.chanxa.testproject.database.common.BaseObjectResult;

import io.reactivex.Observable;

/**
 * Created by hyj on 2017/8/17 0017.
 */

public interface AboutContract {

    interface View extends IView {
        void getUpdateInfoSuccess(UpdateEntity updateEntity);
        void getUpdateInfoFail(String msg);
    }

    interface Presenter extends IPresenter {
        void getUpdateInfo();
    }

    interface Model extends IModel {
        Observable<BaseObjectResult<UpdateEntity>> getUpdateInfo();
    }

}
