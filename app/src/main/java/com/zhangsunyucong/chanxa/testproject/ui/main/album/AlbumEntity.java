package com.zhangsunyucong.chanxa.testproject.ui.main.album;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by zhangsunyucong on 2017/9/17.
 */

public class AlbumEntity implements Serializable {

    @Inject
    public AlbumEntity() {}

    public String userId;

    public String newTempUrl;

    public List<String> photoDetails;

}
