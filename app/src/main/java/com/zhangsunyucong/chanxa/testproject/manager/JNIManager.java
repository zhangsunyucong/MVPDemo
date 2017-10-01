package com.zhangsunyucong.chanxa.testproject.manager;

/**
 * Created by hyj on 2017/8/1 0001.
 */

public class JNIManager {

    private static JNIManager mJNIManager;

    private JNIManager() {

    }

    public static JNIManager getInstance() {
        if(null == mJNIManager) {
            synchronized (JNIManager.class) {
                if(null == mJNIManager) {
                    mJNIManager = new JNIManager();
                }
            }
        }
        return mJNIManager;
    }

    static {
        System.loadLibrary("native-lib");
    }

    public native String stringFromJNI();

    public native String getBuglyAppId();
    public native String getAppId();
    public native String getAppResScrect();
    public native String getRsaServerPublicKey();
    public native String getRsaClientPrivateKey();
    public native String getRsaClientPublickey();
    public native String getChatScrect();


}
