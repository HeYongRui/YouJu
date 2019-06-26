package com.heyongrui.module.data.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class KaiYanDto implements Parcelable {
    private int count;
    private int total;
    private String nextPageUrl;
    private boolean adExist;
    private List<KaiYanItemBean> itemList;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public boolean isAdExist() {
        return adExist;
    }

    public void setAdExist(boolean adExist) {
        this.adExist = adExist;
    }

    public List<KaiYanItemBean> getItemList() {
        return itemList;
    }

    public void setItemList(List<KaiYanItemBean> itemList) {
        this.itemList = itemList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.count);
        dest.writeInt(this.total);
        dest.writeString(this.nextPageUrl);
        dest.writeByte(this.adExist ? (byte) 1 : (byte) 0);
        dest.writeList(this.itemList);
    }

    public KaiYanDto() {
    }

    protected KaiYanDto(Parcel in) {
        this.count = in.readInt();
        this.total = in.readInt();
        this.nextPageUrl = in.readString();
        this.adExist = in.readByte() != 0;
        this.itemList = new ArrayList<KaiYanItemBean>();
        in.readList(this.itemList, KaiYanItemBean.class.getClassLoader());
    }

    public static final Creator<KaiYanDto> CREATOR = new Creator<KaiYanDto>() {
        @Override
        public KaiYanDto createFromParcel(Parcel source) {
            return new KaiYanDto(source);
        }

        @Override
        public KaiYanDto[] newArray(int size) {
            return new KaiYanDto[size];
        }
    };
}
