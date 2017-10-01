package com.zhangsunyucong.chanxa.testproject.database.common;

/**
 * Created by hyj on 2017/8/14 0014.
 */

public class APIManager {

    //聊天的服务器地址
    public static final String CHAT_SERVER_URL = "https://heyunjian.leanapp.cn/";
   // public static final String CHAT_SERVER_URL = "http://192.168.1.103:3000/";
   // public static final String CHAT_SERVER_URL = "http://192.168.199.213:3000/";
    //API的服务器地址
    public static final String API_SERVER_BASE_URL = "http://heyunjian.leanapp.cn/";
    //public static final String API_SERVER_BASE_URL = "http://192.168.1.103:3000/";
   // public static final String API_SERVER_BASE_URL = "http://192.168.199.213:3000/";
    //上传服务器地址
    public static final String UPLOAD_SERVER_BASE_URL = "http://heyunjian.leanapp.cn/";
    //public static final String UPLOAD_SERVER_BASE_URL = "http://192.168.1.103:3000/";
  //  public static final String UPLOAD_SERVER_BASE_URL = "http://192.168.199.213:3000/";

    /**登录*/
    public final static String login_by_sms_code = "user/loginBySMSCode";
    public final static String login_by_name = "user/loginByName";
    public final static String login_by_mobile = "user/loginByMobile";
    public final static String request_sms_code = "user/register/requestSmsCode";

    /**注册*/
    public final static String register_by_phone_num = "user/register/byPhoneNum";
    public final static String register_by_name = "user/register/ByUserName";

    /**简历*/
    public final static String get_vitae_info = "vitae/getInfo";
    public final static String get_blog_info = "blog/getInfo";


    public final static String get_album_info = "album/getInfo";
    public final static String add_album_info = "album/add";
    public final static String delete_album_info = "album/delete";

    /**版本检测*/
    public final static String get_update_info = "update/getUpdateInfo";
    public final static String feedback_feed = "feedback/feed";
}
