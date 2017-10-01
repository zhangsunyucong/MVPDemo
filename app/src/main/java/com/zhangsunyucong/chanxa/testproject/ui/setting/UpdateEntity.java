package com.zhangsunyucong.chanxa.testproject.ui.setting;

import android.os.Parcel;

import com.zhangsunyucong.chanxa.testproject.database.common.BaseData;

import javax.inject.Inject;

/**
 * Created by hyj on 2017/8/17 0017.
 */

public class UpdateEntity extends BaseData {

    @Inject
    public UpdateEntity() {}

    public int versionCode;
    public String versionName;
    public String clientType;
    public String downloadUrl;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.versionCode);
        dest.writeString(this.versionName);
        dest.writeString(this.clientType);
        dest.writeString(this.downloadUrl);
    }

    protected UpdateEntity(Parcel in) {
        super(in);
        this.versionCode = in.readInt();
        this.versionName = in.readString();
        this.clientType = in.readString();
        this.downloadUrl = in.readString();
    }

    public static final Creator<UpdateEntity> CREATOR = new Creator<UpdateEntity>() {
        @Override
        public UpdateEntity createFromParcel(Parcel source) {
            return new UpdateEntity(source);
        }

        @Override
        public UpdateEntity[] newArray(int size) {
            return new UpdateEntity[size];
        }
    };
}
