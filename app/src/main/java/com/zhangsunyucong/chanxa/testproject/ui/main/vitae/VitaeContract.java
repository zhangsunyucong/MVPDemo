package com.zhangsunyucong.chanxa.testproject.ui.main.vitae;

import com.zhangsunyucong.chanxa.testproject.base.mvp.IModel;
import com.zhangsunyucong.chanxa.testproject.base.mvp.IPresenter;
import com.zhangsunyucong.chanxa.testproject.base.mvp.IView;
import com.zhangsunyucong.chanxa.testproject.database.bean.VitaeInfo;
import com.zhangsunyucong.chanxa.testproject.database.common.BaseObjectResult;

import io.reactivex.Observable;

/**
 * Created by hyj on 2017/8/14 0014.
 */

public interface VitaeContract {

    interface View extends IView {
        void getVitaeInfoFail(String msg);
        void getVitaeInfoSuccess(VitaeInfo vitaeInfo);
    }

    interface Presenter extends IPresenter {
        void getVitaeInfo(String userId);
    }

    interface Model extends IModel {
        Observable<BaseObjectResult<VitaeInfo>> getVitaeInfo(String userId);
    }
}
