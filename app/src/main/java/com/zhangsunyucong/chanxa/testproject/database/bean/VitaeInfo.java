package com.zhangsunyucong.chanxa.testproject.database.bean;

import android.os.Parcel;

import com.zhangsunyucong.chanxa.testproject.database.common.BaseData;

import javax.inject.Inject;

/**
 * Created by hyj on 2017/8/14 0014.
 */

public class VitaeInfo extends BaseData {

    @Inject
    public VitaeInfo() {}

    public String romoteUrl;
    public String userId;
    public String userName;
    public int version;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.romoteUrl);
        dest.writeString(this.userId);
        dest.writeString(this.userName);
        dest.writeInt(this.version);
    }

    protected VitaeInfo(Parcel in) {
        super(in);
        this.romoteUrl = in.readString();
        this.userId = in.readString();
        this.userName = in.readString();
        this.version = in.readInt();
    }

    public static final Creator<VitaeInfo> CREATOR = new Creator<VitaeInfo>() {
        @Override
        public VitaeInfo createFromParcel(Parcel source) {
            return new VitaeInfo(source);
        }

        @Override
        public VitaeInfo[] newArray(int size) {
            return new VitaeInfo[size];
        }
    };
}
