package com.heyongrui.module.data.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class TodayRecommendPoemDto implements Parcelable {

    private int status;
    private String message;
    private String cover;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        private int type;
        private int poemId;
        private String mingcheng;
        private String zuozhe;
        private String chaodai;
        private String zhaiyao;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getPoemId() {
            return poemId;
        }

        public void setPoemId(int poemId) {
            this.poemId = poemId;
        }

        public String getMingcheng() {
            return mingcheng;
        }

        public void setMingcheng(String mingcheng) {
            this.mingcheng = mingcheng;
        }

        public String getZuozhe() {
            return zuozhe;
        }

        public void setZuozhe(String zuozhe) {
            this.zuozhe = zuozhe;
        }

        public String getChaodai() {
            return chaodai;
        }

        public void setChaodai(String chaodai) {
            this.chaodai = chaodai;
        }

        public String getZhaiyao() {
            return zhaiyao;
        }

        public void setZhaiyao(String zhaiyao) {
            this.zhaiyao = zhaiyao;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.type);
            dest.writeInt(this.poemId);
            dest.writeString(this.mingcheng);
            dest.writeString(this.zuozhe);
            dest.writeString(this.chaodai);
            dest.writeString(this.zhaiyao);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.type = in.readInt();
            this.poemId = in.readInt();
            this.mingcheng = in.readString();
            this.zuozhe = in.readString();
            this.chaodai = in.readString();
            this.zhaiyao = in.readString();
        }

        public static final Parcelable.Creator<DataBean> CREATOR = new Parcelable.Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.status);
        dest.writeString(this.message);
        dest.writeString(this.cover);
        dest.writeTypedList(this.data);
    }

    public TodayRecommendPoemDto() {
    }

    protected TodayRecommendPoemDto(Parcel in) {
        this.status = in.readInt();
        this.message = in.readString();
        this.cover = in.readString();
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Parcelable.Creator<TodayRecommendPoemDto> CREATOR = new Parcelable.Creator<TodayRecommendPoemDto>() {
        @Override
        public TodayRecommendPoemDto createFromParcel(Parcel source) {
            return new TodayRecommendPoemDto(source);
        }

        @Override
        public TodayRecommendPoemDto[] newArray(int size) {
            return new TodayRecommendPoemDto[size];
        }
    };
}
