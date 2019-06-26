package com.heyongrui.module.data.dto;

import android.os.Parcel;
import android.os.Parcelable;

public class KaiYanLabelBean implements Parcelable {
    private String text;
    private String card;
    private String detail;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.text);
        dest.writeString(this.card);
        dest.writeString(this.detail);
    }

    public KaiYanLabelBean() {
    }

    protected KaiYanLabelBean(Parcel in) {
        this.text = in.readString();
        this.card = in.readString();
        this.detail = in.readString();
    }

    public static final Creator<KaiYanLabelBean> CREATOR = new Creator<KaiYanLabelBean>() {
        @Override
        public KaiYanLabelBean createFromParcel(Parcel source) {
            return new KaiYanLabelBean(source);
        }

        @Override
        public KaiYanLabelBean[] newArray(int size) {
            return new KaiYanLabelBean[size];
        }
    };
}
