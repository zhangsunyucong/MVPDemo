package com.zhangsunyucong.chanxa.testproject.database;

import com.zhangsunyucong.chanxa.testproject.database.services.CSDNAPIService;
import com.zhangsunyucong.chanxa.testproject.database.services.SettingService;
import com.zhangsunyucong.chanxa.testproject.database.services.UserService;
import com.zhangsunyucong.chanxa.testproject.database.services.VitaeService;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ServiceManager {

    //不要忘记在ApiServiceModule.class中提供注入
    @Inject
    public UserService mUserService;

    @Inject
    public VitaeService mVitaeService;

    @Inject
    public CSDNAPIService mCSDNAPIService;

    @Inject
    public SettingService mSettingService;

    @Inject
    public ServiceManager(){}

}
