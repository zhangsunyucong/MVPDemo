package com.zhangsunyucong.chanxa.testproject.base.module;

import com.zhangsunyucong.chanxa.testproject.database.common.APIManager;
import com.zhangsunyucong.chanxa.testproject.database.services.CSDNAPIService;
import com.zhangsunyucong.chanxa.testproject.database.services.SettingService;
import com.zhangsunyucong.chanxa.testproject.database.services.UserService;
import com.zhangsunyucong.chanxa.testproject.database.services.VitaeService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2017/1/3.
 */
@Module
public class ApiServiceModule {

 /*   @Singleton
    @Provides
    HttpUrl provideBaseUrl() {
        return HttpUrl.parse("http://192.168.123.200:3000/");
    }  */

/*    @Singleton
    @Provides
    HttpUrl provideBaseUrl() {
        return HttpUrl.parse("http://192.168.31.18:3000/");
    }*/
    @Singleton
    @Provides
    HttpUrl provideBaseUrl() {
        return HttpUrl.parse(APIManager.API_SERVER_BASE_URL);
    }

    @Singleton
    @Provides
    UserService provideUserService(Retrofit retrofit) {
        return retrofit.create(UserService.class);
    }

    @Singleton
    @Provides
    VitaeService provideVitaeService(Retrofit retrofit) {
        return retrofit.create(VitaeService.class);
    }

    @Singleton
    @Provides
    CSDNAPIService provideCSDNAPIService(Retrofit retrofit) {
        return retrofit.create(CSDNAPIService.class);
    }

    @Singleton
    @Provides
    SettingService provideSettingService(Retrofit retrofit) {
        return retrofit.create(SettingService.class);
    }

}
