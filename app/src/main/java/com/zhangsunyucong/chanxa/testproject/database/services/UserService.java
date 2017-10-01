package com.zhangsunyucong.chanxa.testproject.database.services;

import com.zhangsunyucong.chanxa.testproject.database.common.APIManager;
import com.zhangsunyucong.chanxa.testproject.database.common.BaseObjectResult;
import com.zhangsunyucong.chanxa.testproject.database.common.StringResult;
import com.zhangsunyucong.chanxa.testproject.ui.user.login.userLoginResult;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/1/3.
 */
public interface UserService {

    @FormUrlEncoded
    @POST(APIManager.login_by_sms_code)
    Observable<BaseObjectResult<userLoginResult>> loginBySMSCode(
            @Field("userPhoneNum") String userPhoneNum,
            @Field("smsCode") String smsCodes
    );

    @FormUrlEncoded
    @POST(APIManager.login_by_name)
    Observable<BaseObjectResult<userLoginResult>> loginByName(
            @Field("userName") String userName,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST(APIManager.login_by_mobile)
    Observable<BaseObjectResult<userLoginResult>> loginByMobile(
            @Field("userPhoneNum") String userPhoneNum,
            @Field("password") String password

    );

    @FormUrlEncoded
    @POST(APIManager.request_sms_code)
    Observable<StringResult> requestSmsCode(
            @Field("userPhoneNum") String userPhoneNum
    );

    @FormUrlEncoded
    @POST(APIManager.register_by_phone_num)
    Observable<StringResult> registerByPhoneNum(
            @Field("userPhoneNum") String userPhoneNum,
            @Field("nickName") String nickName,
            @Field("smsCode") String smsCode,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST(APIManager.register_by_name)
    Observable<StringResult> registerByName(
            @Field("userName") String userName,
            @Field("password") String password
    );
}
