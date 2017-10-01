package com.zhangsunyucong.chanxa.testproject.event;

import java.util.List;

/**
 * Created by hyj on 2017/8/23 0023.
 */

public class MePhotoEvent {

    public List<String> photos;

    public MePhotoEvent(List<String> photos) {
        this.photos = photos;
    }
}
