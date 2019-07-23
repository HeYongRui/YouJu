package com.heyongrui.module.data.dto;

import android.os.Parcel;
import android.os.Parcelable;

public class PoemDetailDto implements Parcelable {
    private int status;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        private int id;
        private String yuanwen;
        private String chaodai;
        private String zhaiyao;
        private String mingcheng;
        private String zuozhe;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getYuanwen() {
            return yuanwen;
        }

        public void setYuanwen(String yuanwen) {
            this.yuanwen = yuanwen;
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.yuanwen);
            dest.writeString(this.chaodai);
            dest.writeString(this.zhaiyao);
            dest.writeString(this.mingcheng);
            dest.writeString(this.zuozhe);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.id = in.readInt();
            this.yuanwen = in.readString();
            this.chaodai = in.readString();
            this.zhaiyao = in.readString();
            this.mingcheng = in.readString();
            this.zuozhe = in.readString();
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
        dest.writeParcelable(this.data, flags);
    }

    public PoemDetailDto() {
    }

    protected PoemDetailDto(Parcel in) {
        this.status = in.readInt();
        this.message = in.readString();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<PoemDetailDto> CREATOR = new Parcelable.Creator<PoemDetailDto>() {
        @Override
        public PoemDetailDto createFromParcel(Parcel source) {
            return new PoemDetailDto(source);
        }

        @Override
        public PoemDetailDto[] newArray(int size) {
            return new PoemDetailDto[size];
        }
    };
}
