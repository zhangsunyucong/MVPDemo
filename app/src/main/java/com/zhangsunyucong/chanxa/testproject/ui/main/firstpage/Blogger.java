package com.zhangsunyucong.chanxa.testproject.ui.main.firstpage;

import android.os.Parcel;

import com.zhangsunyucong.chanxa.testproject.database.common.BaseData;

/**
 * Created by hyj on 2017/8/15 0015.
 */

public class Blogger extends BaseData {
    public String enName;
    public String cnName;
    public String motto;
    public String avatarUrl;
    public String blogUrl;
    public String blogType;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.enName);
        dest.writeString(this.cnName);
        dest.writeString(this.motto);
        dest.writeString(this.avatarUrl);
        dest.writeString(this.blogUrl);
        dest.writeString(this.blogType);
    }

    public Blogger() {
    }

    protected Blogger(Parcel in) {
        super(in);
        this.enName = in.readString();
        this.cnName = in.readString();
        this.motto = in.readString();
        this.avatarUrl = in.readString();
        this.blogUrl = in.readString();
        this.blogType = in.readString();
    }

    public static final Creator<Blogger> CREATOR = new Creator<Blogger>() {
        @Override
        public Blogger createFromParcel(Parcel source) {
            return new Blogger(source);
        }

        @Override
        public Blogger[] newArray(int size) {
            return new Blogger[size];
        }
    };
}
