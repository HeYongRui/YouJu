package com.heyongrui.main.data.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lambert on 2019/9/11.
 */

public class FloatingDto implements Parcelable {
    private String name;
    private int icon_res;
    private int type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon_res() {
        return icon_res;
    }

    public void setIcon_res(int icon_res) {
        this.icon_res = icon_res;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public FloatingDto() {
    }

    public FloatingDto(String name, int icon_res, int type) {
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
        dest.writeInt(this.icon_res);
        dest.writeInt(this.type);
    }

    protected FloatingDto(Parcel in) {
        this.name = in.readString();
        this.icon_res = in.readInt();
        this.type = in.readInt();
    }

    public static final Creator<FloatingDto> CREATOR = new Creator<FloatingDto>() {
        @Override
        public FloatingDto createFromParcel(Parcel source) {
            return new FloatingDto(source);
        }

        @Override
        public FloatingDto[] newArray(int size) {
            return new FloatingDto[size];
        }
    };
}
