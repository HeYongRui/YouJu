package com.heyongrui.module2.data.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mr.He on 2019/3/3.
 */

public class GankMenuDto implements Parcelable {
    private String name;
    private int type;
    private int icon_res;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIcon_res() {
        return icon_res;
    }

    public void setIcon_res(int icon_res) {
        this.icon_res = icon_res;
    }

    public GankMenuDto() {
    }

    public GankMenuDto(String name, int icon_res, int type) {
        this.name = name;
        this.icon_res = icon_res;
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.type);
        dest.writeInt(this.icon_res);
    }

    protected GankMenuDto(Parcel in) {
        this.name = in.readString();
        this.type = in.readInt();
        this.icon_res = in.readInt();
    }

    public static final Creator<GankMenuDto> CREATOR = new Creator<GankMenuDto>() {
        @Override
        public GankMenuDto createFromParcel(Parcel source) {
            return new GankMenuDto(source);
        }

        @Override
        public GankMenuDto[] newArray(int size) {
            return new GankMenuDto[size];
        }
    };
}
