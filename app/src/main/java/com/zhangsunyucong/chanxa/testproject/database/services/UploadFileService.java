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

package com.zhangsunyucong.chanxa.testproject.database.services;


import com.example.librarys.rxupload.model.entities.UploadEntities;
import com.example.librarys.rxupload.model.network.result.UploadResult;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * 上传文件
 * Created by JokAr on 2017/3/6.
 */
public interface UploadFileService {

    //TODO 接口名称，注意替换
    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @Multipart
    @POST("upload")
    Observable<UploadResult<UploadEntities>> uploadHead(@Part MultipartBody.Part file);
}
