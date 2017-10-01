package com.zhangsunyucong.chanxa.testproject;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.librarys.customactivityoncrash.config.CaocConfig;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.sdk.QbSdk;
import com.zhangsunyucong.chanxa.testproject.base.AppComponent;
import com.zhangsunyucong.chanxa.testproject.base.DaggerAppComponent;
import com.zhangsunyucong.chanxa.testproject.base.module.ApiServiceModule;
import com.zhangsunyucong.chanxa.testproject.base.module.AppModule;
import com.zhangsunyucong.chanxa.testproject.base.module.HttpModule;
import com.zhangsunyucong.chanxa.testproject.database.common.AESUtils;
import com.zhangsunyucong.chanxa.testproject.database.common.APIManager;
import com.zhangsunyucong.chanxa.testproject.database.common.RSAUtils;
import com.zhangsunyucong.chanxa.testproject.manager.JNIManager;
import com.zhangsunyucong.chanxa.testproject.ui.user.login.LoginActivity;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import shortbread.Shortbread;

/**
 * Created by Administrator on 2017/1/3.
 */
public class App extends Application {

    private static App mApp;
    private AppComponent mAppComponent;

    private boolean mIsDebug = false;

    private String mAesKey;
    private String mAESDecKey;


    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(APIManager.CHAT_SERVER_URL);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return mSocket;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        //dagger注入应用级组件
        mAppComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .apiServiceModule(new ApiServiceModule())
                .httpModule(new HttpModule())
                .build();
        Shortbread.create(this);
        //初始化bugly
        if(!mIsDebug) {
            initBugly();
        }

        initTBS();
        initArouter();
        mAesKey = getAESKey();
        initCrashAction();

    }

    private void initCrashAction() {
        CaocConfig.Builder.create()
               // .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //default: CaocConfig.BACKGROUND_MODE_SHOW_CUSTOM
                .enabled(true) //default: true
               // .showErrorDetails(false) //default: true
              //  .showRestartButton(false) //default: true
                .trackActivities(true) //default: false
                .minTimeBetweenCrashesMs(1000) //default: 3000
                .restartActivity(LoginActivity.class) //default: null (your app's launch activity)
                //.errorActivity(YourCustomErrorActivity.class) //default: null (default error activity)
               // .eventListener(new YourCustomEventListener()) //default: null
                .apply();
    }

    private void initArouter() {
        ARouter.init(getApp());
    }

    public String getAESKey() {
        if(TextUtils.isEmpty(mAesKey)) {
            mAesKey = AESUtils.generateKey();
            mAESDecKey = RSAUtils.encryptByServerPublicKey(mAesKey);
        }
        return mAesKey;
    }

    public String getAESDecKey() {
        return mAESDecKey;
    }

    private void initTBS() {

        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(),  cb);
    }

    /**
     * 初始化bugly
     */
    private void initBugly() {
        CrashReport.initCrashReport(getApplicationContext(),
                JNIManager.getInstance().getBuglyAppId(),
                true);
    }

    public static App getApp() {
        return mApp;
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
