package com.zhangsunyucong.chanxa.testproject.base.module;

import android.app.Application;

import com.google.gson.Gson;
import com.zhangsunyucong.chanxa.testproject.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/1/3.
 */
@Module
public class AppModule {

    private Application mApplication;

    public AppModule(App application) {
        this.mApplication = application;
    }

    @Singleton
    @Provides
    public Application provideApplication() {
        return mApplication;
    }

    @Singleton
    @Provides
    public Gson provideGson(){
        return new Gson();
    }

}