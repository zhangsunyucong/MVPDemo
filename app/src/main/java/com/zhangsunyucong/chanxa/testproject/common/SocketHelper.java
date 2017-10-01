package com.zhangsunyucong.chanxa.testproject.common;

import android.text.TextUtils;

import com.zhangsunyucong.chanxa.testproject.database.common.AESUtils;
import com.zhangsunyucong.chanxa.testproject.manager.AppConfig;

/**
 * Created by zhangsunyucong on 2017/9/17.
 */

public class SocketHelper {

    public static String getDecryptData(String res) {
        String respose = "";
        if(!TextUtils.isEmpty(res)) {
            respose = AESUtils.decryptData(AppConfig.ChatScrect, res);
        }
        return respose;
    }

}
