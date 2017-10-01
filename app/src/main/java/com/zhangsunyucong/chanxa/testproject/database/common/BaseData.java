package com.zhangsunyucong.chanxa.testproject.database.common;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hyj on 2017/8/7 0007.
 */

public class BaseData implements Parcelable {
    public String objectId;
    public String createdAt;
    public String updatedAt;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.objectId);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
    }

    public BaseData() {
    }

    protected BaseData(Parcel in) {
        this.objectId = in.readString();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
    }

}
