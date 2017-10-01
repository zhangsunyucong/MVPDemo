package com.zhangsunyucong.chanxa.testproject.base;

import android.app.Application;

import com.google.gson.Gson;
import com.zhangsunyucong.chanxa.testproject.base.module.ApiServiceModule;
import com.zhangsunyucong.chanxa.testproject.base.module.AppModule;
import com.zhangsunyucong.chanxa.testproject.base.module.HttpModule;
import com.zhangsunyucong.chanxa.testproject.database.ServiceManager;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Administrator on 2017/1/3.
 */
@Singleton
@Component(modules = {AppModule.class, HttpModule.class, ApiServiceModule.class})
public interface AppComponent {

    Application application();

    ServiceManager serviceManager();

    Gson gson();

}
