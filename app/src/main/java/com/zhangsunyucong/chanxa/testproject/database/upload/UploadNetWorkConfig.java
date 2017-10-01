/*
 * Copyright (C) 2016 JokAr
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zhangsunyucong.chanxa.testproject.database.upload;

import com.example.librarys.rxupload.model.network.upload.UpLoadProgressInterceptor;
import com.example.librarys.rxupload.model.network.upload.UploadListener;
import com.zhangsunyucong.chanxa.testproject.base.DecodeConverterFactory;
import com.zhangsunyucong.chanxa.testproject.database.common.APIManager;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by JokAr on 2017/3/6.
 */

public class UploadNetWorkConfig {
    //// TODO: BASE_URL,因为测试用的自己服务器，为了缓解自己服务器压力，所以请各位自己填写url
    private static final String BASE_URL = APIManager.UPLOAD_SERVER_BASE_URL;
    private static final int DEFAULT_CONNECT_TIMEOUT = 50;
    private static final int DEFAULT_READ_TIMEOUT = 50;
    private static final int DEFAULT_WRITE_TIMEOUT = 50;

    public static Retrofit getRetrofit(UploadListener listener) {
        UpLoadProgressInterceptor interceptor = new UpLoadProgressInterceptor(listener);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.MINUTES)
                .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.MINUTES)
                .writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.MINUTES)
                .build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(DecodeConverterFactory.create())
                // .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}
