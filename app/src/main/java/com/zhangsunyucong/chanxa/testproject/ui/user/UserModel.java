package com.zhangsunyucong.chanxa.testproject.ui.user;

import com.zhangsunyucong.chanxa.testproject.base.mvp.BaseModel;
import com.zhangsunyucong.chanxa.testproject.database.common.BaseObjectResult;
import com.zhangsunyucong.chanxa.testproject.database.common.StringResult;
import com.zhangsunyucong.chanxa.testproject.ui.user.login.LoginByNameContract;
import com.zhangsunyucong.chanxa.testproject.ui.user.login.LoginBySMSCodeContract;
import com.zhangsunyucong.chanxa.testproject.ui.user.login.LoginContract;
import com.zhangsunyucong.chanxa.testproject.ui.user.login.userLoginResult;
import com.zhangsunyucong.chanxa.testproject.ui.user.register.RegisterByNameContract;
import com.zhangsunyucong.chanxa.testproject.ui.user.register.RegisterContract;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by hyj on 2017/8/8 0008.
 */

public class UserModel extends BaseModel
        implements LoginContract.Model, RegisterContract.Model,
                RegisterByNameContract.Model, LoginByNameContract.Model,
                LoginBySMSCodeContract.Model{

    @Inject
    public UserModel() {}

    @Override
    public Observable<BaseObjectResult<userLoginResult>> loginByName(String userName, String password) {
        Observable<BaseObjectResult<userLoginResult>> result = mServiceManager
                .mUserService
                .loginByName(userName, password);
        return result;
    }

    @Override
    public Observable<BaseObjectResult<userLoginResult>> loginBySMSCode(String userPhoneNum, String smsCode) {
        Observable<BaseObjectResult<userLoginResult>> result = mServiceManager
                .mUserService
                .loginBySMSCode(userPhoneNum, smsCode);
        return result;
    }

    @Override
    public Observable<BaseObjectResult<userLoginResult>>
    loginByMobile(String userPhoneNum, String password) {
        Observable<BaseObjectResult<userLoginResult>> result = mServiceManager
                .mUserService
                .loginByMobile(userPhoneNum, password);
        return result;
    }

    @Override
    public Observable<StringResult> registerByMobile(String userPhoneNum, String nickName,
                                                     String smsCode, String password) {
        Observable<StringResult> result = mServiceManager
                .mUserService
                .registerByPhoneNum(userPhoneNum, nickName, smsCode, password);
        return result;
    }

    @Override
    public Observable<StringResult> requestSmsCode(String userPhoneNum) {
        Observable<StringResult> result = mServiceManager
                .mUserService
                .requestSmsCode(userPhoneNum);

        return result;
    }

    @Override
    public Observable<StringResult> registerByName(String userName, String password) {
        Observable<StringResult> result = mServiceManager
                .mUserService
                .registerByName(userName, password);
        return result;
    }
}
