package com.zhangsunyucong.chanxa.testproject.ui.main;

import com.zhangsunyucong.chanxa.testproject.base.mvp.BaseModel;
import com.zhangsunyucong.chanxa.testproject.database.bean.VitaeInfo;
import com.zhangsunyucong.chanxa.testproject.database.common.BaseArrayResult;
import com.zhangsunyucong.chanxa.testproject.database.common.BaseObjectResult;
import com.zhangsunyucong.chanxa.testproject.jsoup.Config;
import com.zhangsunyucong.chanxa.testproject.ui.main.firstpage.BlogListContrat;
import com.zhangsunyucong.chanxa.testproject.ui.main.firstpage.Blogger;
import com.zhangsunyucong.chanxa.testproject.ui.main.firstpage.FirstContract;
import com.zhangsunyucong.chanxa.testproject.ui.main.vitae.VitaeContract;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by hyj on 2017/8/14 0014.
 */

public class MainModel extends BaseModel implements VitaeContract.Model,
        FirstContract.Model, BlogListContrat.Model {


    @Inject
    public MainModel() {}

    @Override
    public Observable<BaseObjectResult<VitaeInfo>> getVitaeInfo(String userId) {
        return mServiceManager
                .mVitaeService
                .getVitaeInfo(userId);
    }

    @Override
    public Observable<BaseArrayResult<Blogger>> getBloggerInfo() {
        return mServiceManager
                .mCSDNAPIService
                .getBloggerInfo();
    }

    @Override
    public Observable<String> getBlogList(String userId, int page) {
        String url = Config.BLOG_HOST + userId + "/article/list/" + page;
        return mServiceManager
                .mCSDNAPIService
                .getBlogList(url);
    }
}
