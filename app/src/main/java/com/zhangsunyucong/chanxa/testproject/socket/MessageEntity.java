package com.zhangsunyucong.chanxa.testproject.socket;

import java.io.Serializable;

/**
 * Created by zhangsunyucong on 2017/9/17.
 */

public class MessageEntity implements Serializable{

    public String message;
    public String username;
    public int numUsers;
}
