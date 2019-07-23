package com.heyongrui.module.data.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class PoemSearchDto implements Parcelable {
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
        private List<PoemSearchBean> data;

        public List<PoemSearchBean> getData() {
            return data;
        }

        public void setData(List<PoemSearchBean> data) {
            this.data = data;
        }

        public static class PoemSearchBean implements Parcelable {
            private int id;
            private String mingcheng;
            private String zuozhe;
            private String zhaiyao;
            private int voice;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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

            public String getZhaiyao() {
                return zhaiyao;
            }

            public void setZhaiyao(String zhaiyao) {
                this.zhaiyao = zhaiyao;
            }

            public int getVoice() {
                return voice;
            }

            public void setVoice(int voice) {
                this.voice = voice;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.id);
                dest.writeString(this.mingcheng);
                dest.writeString(this.zuozhe);
                dest.writeString(this.zhaiyao);
                dest.writeInt(this.voice);
            }

            public PoemSearchBean() {
            }

            protected PoemSearchBean(Parcel in) {
                this.id = in.readInt();
                this.mingcheng = in.readString();
                this.zuozhe = in.readString();
                this.zhaiyao = in.readString();
                this.voice = in.readInt();
            }

            public static final Parcelable.Creator<PoemSearchBean> CREATOR = new Parcelable.Creator<PoemSearchBean>() {
                @Override
                public PoemSearchBean createFromParcel(Parcel source) {
                    return new PoemSearchBean(source);
                }

                @Override
                public PoemSearchBean[] newArray(int size) {
                    return new PoemSearchBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeTypedList(this.data);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.data = in.createTypedArrayList(PoemSearchBean.CREATOR);
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

    public PoemSearchDto() {
    }

    protected PoemSearchDto(Parcel in) {
        this.status = in.readInt();
        this.message = in.readString();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<PoemSearchDto> CREATOR = new Parcelable.Creator<PoemSearchDto>() {
        @Override
        public PoemSearchDto createFromParcel(Parcel source) {
            return new PoemSearchDto(source);
        }

        @Override
        public PoemSearchDto[] newArray(int size) {
            return new PoemSearchDto[size];
        }
    };
}
