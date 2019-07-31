package com.heyongrui.module.data.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DouBanDto implements Parcelable {
    private List<SubjectsBean> subjects;

    public List<SubjectsBean> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectsBean> subjects) {
        this.subjects = subjects;
    }

    public static class SubjectsBean implements Parcelable {
        private String rate;
        @SerializedName("cover_x")
        private int coverX;
        @SerializedName("cover_y")
        private int coverY;
        private String title;
        private String url;
        private boolean playable;
        private String cover;
        private String id;
        @SerializedName("is_new")
        private boolean isNew;

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public int getCoverX() {
            return coverX;
        }

        public void setCoverX(int coverX) {
            this.coverX = coverX;
        }

        public int getCoverY() {
            return coverY;
        }

        public void setCoverY(int coverY) {
            this.coverY = coverY;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isPlayable() {
            return playable;
        }

        public void setPlayable(boolean playable) {
            this.playable = playable;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isNew() {
            return isNew;
        }

        public void setNew(boolean aNew) {
            isNew = aNew;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.rate);
            dest.writeInt(this.coverX);
            dest.writeInt(this.coverY);
            dest.writeString(this.title);
            dest.writeString(this.url);
            dest.writeByte(this.playable ? (byte) 1 : (byte) 0);
            dest.writeString(this.cover);
            dest.writeString(this.id);
            dest.writeByte(this.isNew ? (byte) 1 : (byte) 0);
        }

        public SubjectsBean() {
        }

        protected SubjectsBean(Parcel in) {
            this.rate = in.readString();
            this.coverX = in.readInt();
            this.coverY = in.readInt();
            this.title = in.readString();
            this.url = in.readString();
            this.playable = in.readByte() != 0;
            this.cover = in.readString();
            this.id = in.readString();
            this.isNew = in.readByte() != 0;
        }

        public static final Parcelable.Creator<SubjectsBean> CREATOR = new Parcelable.Creator<SubjectsBean>() {
            @Override
            public SubjectsBean createFromParcel(Parcel source) {
                return new SubjectsBean(source);
            }

            @Override
            public SubjectsBean[] newArray(int size) {
                return new SubjectsBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.subjects);
    }

    public DouBanDto() {
    }

    protected DouBanDto(Parcel in) {
        this.subjects = in.createTypedArrayList(SubjectsBean.CREATOR);
    }

    public static final Parcelable.Creator<DouBanDto> CREATOR = new Parcelable.Creator<DouBanDto>() {
        @Override
        public DouBanDto createFromParcel(Parcel source) {
            return new DouBanDto(source);
        }

        @Override
        public DouBanDto[] newArray(int size) {
            return new DouBanDto[size];
        }
    };
}
