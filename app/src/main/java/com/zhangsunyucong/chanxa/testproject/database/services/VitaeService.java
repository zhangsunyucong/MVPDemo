package com.zhangsunyucong.chanxa.testproject.database.services;

import com.zhangsunyucong.chanxa.testproject.database.bean.VitaeInfo;
import com.zhangsunyucong.chanxa.testproject.database.common.APIManager;
import com.zhangsunyucong.chanxa.testproject.database.common.BaseObjectResult;
import com.zhangsunyucong.chanxa.testproject.ui.main.album.AlbumEntity;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by hyj on 2017/8/14 0014.
 */

public interface VitaeService {

    @FormUrlEncoded
    @POST(APIManager.get_vitae_info)
    Observable<BaseObjectResult<VitaeInfo>> getVitaeInfo(
            @Field("userId") String userId
    );

    @FormUrlEncoded
    @POST(APIManager.get_album_info)
    Observable<BaseObjectResult<AlbumEntity>> getAlbumInfo(
            @Field("userId") String userId
    );

    @FormUrlEncoded
    @POST(APIManager.add_album_info)
    Observable<BaseObjectResult<AlbumEntity>> addAlbum(
            @Field("userId") String userId,
            @Field("url") String url
    );

    @FormUrlEncoded
    @POST(APIManager.delete_album_info)
    Observable<BaseObjectResult<AlbumEntity>> deletePhoto(
            @Field("userId") String userId,
            @Field("url") String url
    );

}
