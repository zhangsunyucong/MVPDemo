package com.zhangsunyucong.chanxa.testproject.base;

import android.content.Context;

import com.google.gson.Gson;
import com.zhangsunyucong.chanxa.testproject.App;
import com.zhangsunyucong.chanxa.testproject.database.common.AESUtils;
import com.zhangsunyucong.chanxa.testproject.database.common.RSAUtils;
import com.zhangsunyucong.chanxa.testproject.database.common.SignUrlUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by zhangsunyucong on 2017/8/21.
 */

public class CommonParamsInterceptor implements Interceptor {


    private Context mContext;
    private Gson mGson;

    public CommonParamsInterceptor(Context context, Gson gson) {
        mContext = context;
        mGson = gson;
    }

    /**
     * 请求头可能要处理
     * @param chain
     * @return
     * @throws IOException
     */
    @Override
    public Response intercept(Chain chain) throws IOException {
        //String newJsonParams = mGson.toJson(rootMap);  //装换成json字符串
        //获取到request

        Request request = chain.request();
        //获取到方法
        String method = request.method();
        try {
            //添加公共参数
            HashMap<String, Object> commomParamsMap = new HashMap<>();
            //get请求的封装
            if (method.equals("GET")) {
                //获取到请求地址api
                HttpUrl httpUrlurl = request.url();
                HashMap<String, Object> rootMap = new HashMap<>();
                //通过请求地址(最初始的请求地址)获取到参数列表
                Set<String> parameterNames = httpUrlurl.queryParameterNames();
                for (String key : parameterNames) {  //循环参数列表
                    rootMap.put(key, httpUrlurl.queryParameter(key));
                }
                //添加公共参数
                rootMap.putAll(commomParamsMap);
                //生成签名
                String sign = SignUrlUtils.getInstance().getSign(rootMap);
                String signString = SignUrlUtils.getInstance().getSignParamsString(rootMap);
                //重新拼接url
                HttpUrl.Builder httpUrlBuilder = request.url().newBuilder();
                //添加参数
                httpUrlBuilder.addQueryParameter("sign", sign);
                httpUrlBuilder.addQueryParameter("signString", signString);
                Request.Builder requestBuilder = request.newBuilder();
                requestBuilder.headers(request.headers());
                requestBuilder.url(httpUrlBuilder.build());
                request = requestBuilder.build();
            }
            //post请求的封装
            else if (method.equals("POST")) {
                //FormBody.Builder builder = new FormBody.Builder();
                //builder.addEncoded("phone","phone");
                //请求体
                RequestBody requestBody = request.body();

                HashMap<String, Object> rootMap = new HashMap<>();
                if (requestBody instanceof FormBody) {
                    for (int i = 0; i < ((FormBody) requestBody).size(); i++) {
                        rootMap.put(((FormBody) requestBody).encodedName(i), ((FormBody) requestBody).encodedValue(i));
                    }
                    //添加公共参数
                    rootMap.putAll(commomParamsMap);
                    //生成签名
                    String sign = SignUrlUtils.getInstance().getSign(rootMap);
                    String signString = SignUrlUtils.getInstance().getSignParamsString(rootMap);
                    //请求定制：添加请求头
                    Request.Builder requestBuilder = request.newBuilder();
                    Headers headers = request.headers();
                    requestBuilder.headers(headers);
                    //请求体定制：统一添加token参数
                    FormBody.Builder newFormBody = new FormBody.Builder();
                    FormBody oldFormBody = (FormBody) request.body();
                    for (int i = 0;i<oldFormBody.size();i++){
                        newFormBody.addEncoded(oldFormBody.encodedName(i),oldFormBody.encodedValue(i));
                    }
                    //添加签名信息
                    newFormBody.add("aesKey", RSAUtils.encryptByServerPublicKey(App.getApp().getAESKey()));
                    newFormBody.add("signString", AESUtils.encryptData(App.getApp().getAESKey(), signString));
                    newFormBody.add("sign", sign);
                    requestBuilder.method(method, newFormBody.build());
                    request = requestBuilder.build();

                } /*else {
                    //buffer流
                    Buffer buffer = new Buffer();
                    requestBody.writeTo(buffer);
                    String oldParamsJson = buffer.readUtf8();
                    rootMap = mGson.fromJson(oldParamsJson, HashMap.class);  //原始参数
                    rootMap.put("publicParams", commomParamsMap);  //重新组装
                    String newJsonParams = mGson.toJson(rootMap);  //装换成json字符串
                    request = request.newBuilder().post(RequestBody.create(JSON, newJsonParams)).build();*//*
                }*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //最后通过chain.proceed(request)进行返回
        return chain.proceed(request);
    }
}