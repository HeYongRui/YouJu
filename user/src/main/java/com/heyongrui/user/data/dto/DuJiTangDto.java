package com.heyongrui.user.data.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 毒鸡汤
 */
public class DuJiTangDto implements Parcelable {

    private String content;
    private String author;
    private String source;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.content);
        dest.writeString(this.author);
        dest.writeString(this.source);
    }

    public DuJiTangDto() {
    }

    protected DuJiTangDto(Parcel in) {
        this.content = in.readString();
        this.author = in.readString();
        this.source = in.readString();
    }

    public static final Parcelable.Creator<DuJiTangDto> CREATOR = new Parcelable.Creator<DuJiTangDto>() {
        @Override
        public DuJiTangDto createFromParcel(Parcel source) {
            return new DuJiTangDto(source);
        }

        @Override
        public DuJiTangDto[] newArray(int size) {
            return new DuJiTangDto[size];
        }
    };
}
