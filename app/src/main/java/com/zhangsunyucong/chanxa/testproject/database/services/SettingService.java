package com.zhangsunyucong.chanxa.testproject.database.services;

import com.zhangsunyucong.chanxa.testproject.database.common.APIManager;
import com.zhangsunyucong.chanxa.testproject.database.common.BaseObjectResult;
import com.zhangsunyucong.chanxa.testproject.database.common.StringResult;
import com.zhangsunyucong.chanxa.testproject.ui.setting.UpdateEntity;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by hyj on 2017/8/17 0017.
 */

public interface SettingService {

    @GET(APIManager.get_update_info)
    Observable<BaseObjectResult<UpdateEntity>> getUpdateInfo();

    @FormUrlEncoded
    @POST(APIManager.feedback_feed)
    Observable<StringResult> feedback(
            @Field("clientType") String clientType,
            @Field("content") String content,
            @Field("usePhoneNum") String usePhoneNum
    );
}
