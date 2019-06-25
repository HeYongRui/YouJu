package com.heyongrui.user.data.dto;

import android.os.Parcel;
import android.os.Parcelable;

public class HitokotoDto implements Parcelable {
    /**
     * id : 3880
     * hitokoto : 如果你是一匹千里马，那么请做自己的伯乐
     * type : f
     * from : Qihoo360
     * creator : Doraemon!
     * created_at : 1537159093
     */

    private int id;
    private String hitokoto;
    private String type;
    private String from;
    private String creator;
    private String created_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHitokoto() {
        return hitokoto;
    }

    public void setHitokoto(String hitokoto) {
        this.hitokoto = hitokoto;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.hitokoto);
        dest.writeString(this.type);
        dest.writeString(this.from);
        dest.writeString(this.creator);
        dest.writeString(this.created_at);
    }

    public HitokotoDto() {
    }

    protected HitokotoDto(Parcel in) {
        this.id = in.readInt();
        this.hitokoto = in.readString();
        this.type = in.readString();
        this.from = in.readString();
        this.creator = in.readString();
        this.created_at = in.readString();
    }

    public static final Parcelable.Creator<HitokotoDto> CREATOR = new Parcelable.Creator<HitokotoDto>() {
        @Override
        public HitokotoDto createFromParcel(Parcel source) {
            return new HitokotoDto(source);
        }

        @Override
        public HitokotoDto[] newArray(int size) {
            return new HitokotoDto[size];
        }
    };
}
