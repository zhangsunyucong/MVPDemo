package com.zhangsunyucong.chanxa.testproject.base.module;

import android.app.Application;

import com.google.gson.Gson;
import com.zhangsunyucong.chanxa.testproject.base.CommonParamsInterceptor;
import com.zhangsunyucong.chanxa.testproject.base.DecodeConverterFactory;
import com.zhangsunyucong.chanxa.testproject.base.httpcommon.cookie.JavaNetCookieJar;
import com.zhangsunyucong.chanxa.testproject.base.httpcommon.cookie.PersistentCookieStore;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by Laiyimin on 2016/3/14.
 */
@Module
public class HttpModule {
    /**
     * 连接超时时间，单位s
     */
    private static final int DEFAULT_CONNECT_TIMEOUT = 60000;
    /**
     * 读超时时间，单位s
     */
    private static final int DEFAULT_READ_TIMEOUT = 60000;
    /**
     * 写超时时间，单位s
     */
    private static final int DEFAULT_WRITE_TIMEOUT = 60000;

    @Singleton
    @Provides
    Retrofit provideRetrofit(OkHttpClient client, HttpUrl baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(DecodeConverterFactory.create())
                // .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient(Application application) {
        CookieHandler cookieHandler = new CookieManager(
                new PersistentCookieStore(application),
                CookiePolicy.ACCEPT_ALL);

        //使用
    /*    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setColorLevel(Level.INFO);*/

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new CommonParamsInterceptor(application, new Gson()))
             //   .addInterceptor(loggingInterceptor)
                .cookieJar(new JavaNetCookieJar(cookieHandler));

        return builder.build();
    }

}
