package com.heyongrui.module.data.dto;

import android.os.Parcel;
import android.os.Parcelable;

public class KaiYanItemBean implements Parcelable {
    private String type;//banner banner3 videoSmallCard followCard  textCard squareCardCollection
    private KaiYanDataBean data;
    private String tag;
    private int id;
    private int adIndex;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public KaiYanDataBean getData() {
        return data;
    }

    public void setData(KaiYanDataBean data) {
        this.data = data;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAdIndex() {
        return adIndex;
    }

    public void setAdIndex(int adIndex) {
        this.adIndex = adIndex;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeParcelable(this.data, flags);
        dest.writeString(this.tag);
        dest.writeInt(this.id);
        dest.writeInt(this.adIndex);
    }

    public KaiYanItemBean() {
    }

    protected KaiYanItemBean(Parcel in) {
        this.type = in.readString();
        this.data = in.readParcelable(KaiYanDataBean.class.getClassLoader());
        this.tag = in.readString();
        this.id = in.readInt();
        this.adIndex = in.readInt();
    }

    public static final Creator<KaiYanItemBean> CREATOR = new Creator<KaiYanItemBean>() {
        @Override
        public KaiYanItemBean createFromParcel(Parcel source) {
            return new KaiYanItemBean(source);
        }

        @Override
        public KaiYanItemBean[] newArray(int size) {
            return new KaiYanItemBean[size];
        }
    };
}
