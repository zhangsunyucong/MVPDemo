package com.zhangsunyucong.chanxa.testproject.manager;

/**
 * Created by zhangsunyucong on 2017/9/17.
 */

public class AppConfig {

    //android客户端APPID
    public static final String AppId = JNIManager.getInstance().getAppId();
    //android客户端APPScrect
    public static final String AppResScrect = JNIManager.getInstance().getAppResScrect();

    public static final String ChatScrect = JNIManager.getInstance().getChatScrect();

    //服务器的RSA公钥，用于加密数据，服务器私钥解密
    public static final String RSA_SERVER_PUBLIC_KEY_STR =
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCcgzMGh8wVeUxVxsXLt6hOxwSV" +
                    "G8rzEpuCtxExQRpSXkd++4G48nYEoFGuqDvcSY2KnJQgXHkhuIdWW4ENbPOkutap" +
                    "SNRksPk7StSDLTfJ2JZOj8jq3B08M7qKVdr3KXzHMAcvVnkwyaoYs86xjGHYPeGk" +
                    "vV6hLklwYHZq6nV7KQIDAQAB";

}
