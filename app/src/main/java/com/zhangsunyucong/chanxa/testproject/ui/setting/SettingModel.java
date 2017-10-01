package com.zhangsunyucong.chanxa.testproject.ui.setting;

import com.zhangsunyucong.chanxa.testproject.base.mvp.BaseModel;
import com.zhangsunyucong.chanxa.testproject.database.common.BaseObjectResult;
import com.zhangsunyucong.chanxa.testproject.database.common.StringResult;
import com.zhangsunyucong.chanxa.testproject.ui.setting.feedback.FeedbackContract;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by hyj on 2017/8/17 0017.
 */

public class SettingModel extends BaseModel implements AboutContract.Model
    , FeedbackContract.Model {

    @Inject
    public SettingModel() {}

    @Override
    public Observable<BaseObjectResult<UpdateEntity>> getUpdateInfo() {
        return mServiceManager.mSettingService
                .getUpdateInfo();
    }

    @Override
    public Observable<StringResult> feedback(String clientType, String content, String userPhoneNum) {
        return mServiceManager.mSettingService
                .feedback(clientType, content, userPhoneNum);
    }
}
