package com.zhangsunyucong.chanxa.testproject.database.services;

import com.zhangsunyucong.chanxa.testproject.database.common.APIManager;
import com.zhangsunyucong.chanxa.testproject.database.common.BaseArrayResult;
import com.zhangsunyucong.chanxa.testproject.ui.main.firstpage.Blogger;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by hyj on 2017/8/15 0015.
 */

public interface CSDNAPIService {

    @POST(APIManager.get_blog_info)
    Observable<BaseArrayResult<Blogger>> getBloggerInfo();

    @GET
    Observable<String> getBlogList(@Url String url);

}
