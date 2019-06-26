package com.heyongrui.module.data.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class DuJiTang2Dto implements Parcelable {

    private String msg;
    private int status;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {

        private String content;
        private int id;
        private int tid;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getTid() {
            return tid;
        }

        public void setTid(int tid) {
            this.tid = tid;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.content);
            dest.writeInt(this.id);
            dest.writeInt(this.tid);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.content = in.readString();
            this.id = in.readInt();
            this.tid = in.readInt();
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
        dest.writeString(this.msg);
        dest.writeInt(this.status);
        dest.writeTypedList(this.data);
    }

    public DuJiTang2Dto() {
    }

    protected DuJiTang2Dto(Parcel in) {
        this.msg = in.readString();
        this.status = in.readInt();
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Parcelable.Creator<DuJiTang2Dto> CREATOR = new Parcelable.Creator<DuJiTang2Dto>() {
        @Override
        public DuJiTang2Dto createFromParcel(Parcel source) {
            return new DuJiTang2Dto(source);
        }

        @Override
        public DuJiTang2Dto[] newArray(int size) {
            return new DuJiTang2Dto[size];
        }
    };
}
