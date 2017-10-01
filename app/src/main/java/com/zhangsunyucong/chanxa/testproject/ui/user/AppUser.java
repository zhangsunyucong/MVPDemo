package com.zhangsunyucong.chanxa.testproject.ui.user;

import android.app.Activity;

import com.zhangsunyucong.chanxa.testproject.App;
import com.example.librarys.utils.ValidateUtils;
import com.zhangsunyucong.chanxa.testproject.common.ActivitysManager;
import com.zhangsunyucong.chanxa.testproject.database.common.BaseData;
import com.zhangsunyucong.chanxa.testproject.manager.ACache;
import com.zhangsunyucong.chanxa.testproject.manager.ACacheConstant;
import com.zhangsunyucong.chanxa.testproject.ui.user.login.userLoginResult;

/**
 * Created by hyj on 2017/8/8 0008.
 */

public class AppUser extends BaseData {

  /*  @Inject
    public AppUser() {}*/

    public String userName;
    public boolean emailVerified;
    public boolean mobilePhoneVerified;
    public String password;

    public userLoginResult muserLoginResult;

    private Activity mActivity;

    public void setuserLoginResult(userLoginResult result) {
        mActivity = ActivitysManager.getInstance().getFirstActivity();
        if(mActivity != null) {
            ACache.get(mActivity).put(ACacheConstant.KEY_APP_USER, result);
            ACache.get(mActivity).put(ACacheConstant.KEY_USER_PHONE_NUM, result.mobilePhoneNumber);
            ACache.get(mActivity).put(ACacheConstant.KEY_USER_NAME, result.username);
        }

        muserLoginResult = result;
    }

    public boolean isLogin(Activity activity) {
        if(activity != null) {
            if(ValidateUtils.checkNotNULL(getUserLoginResult(activity))) {
                return true;
            }
        }
        return false;
    }

    public userLoginResult getUserLoginResult(Activity activity) {
        if(muserLoginResult == null) {
            if(activity != null) {
                muserLoginResult = (userLoginResult)ACache
                        .get(activity)
                        .getAsObject(ACacheConstant.KEY_APP_USER);
            }
        }
        return muserLoginResult;
    }

    public userLoginResult getuserLoginResult() {
        return muserLoginResult;
    }

    public void clearUser(Activity activity) {
        if(ValidateUtils.checkNotNULL(activity)) {
            muserLoginResult = null;
            ACache.get(activity).remove(ACacheConstant.KEY_APP_USER);
        }
    }

    public String getLastLoginUserPhoneNum() {
        mActivity = ActivitysManager.getInstance().getFirstActivity();
        if(ValidateUtils.checkNotNULL(mActivity)) {
            return ACache.get(mActivity).getAsString(ACacheConstant.KEY_USER_PHONE_NUM);
        }
        return ACache.get(App.getApp()).getAsString(ACacheConstant.KEY_USER_PHONE_NUM);
    }

    public String getLastLoginUserName() {
        mActivity = ActivitysManager.getInstance().getFirstActivity();
        if(ValidateUtils.checkNotNULL(mActivity)) {
            return ACache.get(mActivity).getAsString(ACacheConstant.KEY_USER_NAME);
        }
        return ACache.get(App.getApp()).getAsString(ACacheConstant.KEY_USER_NAME);
    }

    private static AppUser mAppUser;

    private AppUser() {}

    public static AppUser getAppUser() {
        if(null == mAppUser) {
            synchronized (AppUser.class) {
                if(null == mAppUser) {
                    mAppUser = new AppUser();
                }
            }
        }
        return mAppUser;
    }

}
