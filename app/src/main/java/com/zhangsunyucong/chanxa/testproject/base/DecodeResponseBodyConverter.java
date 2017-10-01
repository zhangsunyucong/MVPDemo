package com.zhangsunyucong.chanxa.testproject.base;

import com.google.gson.TypeAdapter;
import com.zhangsunyucong.chanxa.testproject.database.common.AESUtils;
import com.zhangsunyucong.chanxa.testproject.manager.AppConfig;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by hyj on 2017/8/7 0007.
 */

public class DecodeResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final TypeAdapter<T> adapter;
    private Type mType;

    DecodeResponseBodyConverter(TypeAdapter<T> adapter, Type type) {
        this.adapter = adapter;
        this.mType = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        //解密字符串
        if(mType == String.class) {
            return (T) value.string();
        } /*else if(mType == UploadResult.class) {
            return adapter.fromJson(mType, value.string());
        }*/
        else {
            String result = AESUtils.decryptData(AppConfig.AppResScrect,
                    value.string());
            return adapter.fromJson(result);
        }

        //return adapter.fromJson(EncryptUtils.decode(value.string()));

    }
}
