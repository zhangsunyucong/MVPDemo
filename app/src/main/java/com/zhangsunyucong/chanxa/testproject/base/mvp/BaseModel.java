package com.zhangsunyucong.chanxa.testproject.base.mvp;

import android.app.Application;

import com.zhangsunyucong.chanxa.testproject.database.ServiceManager;

import javax.inject.Inject;

/**
 * Created by jess on 8/5/16 12:55
 * contact with jess.yan.effort@gmail.com
 */
public class BaseModel{

    @Inject
    protected Application mApplication;

    @Inject
    protected ServiceManager mServiceManager;

}
