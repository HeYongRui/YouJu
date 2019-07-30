package com.heyongrui.module.data.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class PoemGroupDto implements Parcelable {
    private int status;
    private String message;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        private String groupId;
        private String name;
        private int memberCount;
        private String createdUserId;
        private String createdNickname;
        private int topicCount;
        private String description;
        private String img;
        private int status;
        private int joinStatus;
        private long createdTime;
        private String createdSportrait;
        private String createdPortrait;

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getMemberCount() {
            return memberCount;
        }

        public void setMemberCount(int memberCount) {
            this.memberCount = memberCount;
        }

        public String getCreatedUserId() {
            return createdUserId;
        }

        public void setCreatedUserId(String createdUserId) {
            this.createdUserId = createdUserId;
        }

        public String getCreatedNickname() {
            return createdNickname;
        }

        public void setCreatedNickname(String createdNickname) {
            this.createdNickname = createdNickname;
        }

        public int getTopicCount() {
            return topicCount;
        }

        public void setTopicCount(int topicCount) {
            this.topicCount = topicCount;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getJoinStatus() {
            return joinStatus;
        }

        public void setJoinStatus(int joinStatus) {
            this.joinStatus = joinStatus;
        }

        public long getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(long createdTime) {
            this.createdTime = createdTime;
        }

        public String getCreatedSportrait() {
            return createdSportrait;
        }

        public void setCreatedSportrait(String createdSportrait) {
            this.createdSportrait = createdSportrait;
        }

        public String getCreatedPortrait() {
            return createdPortrait;
        }

        public void setCreatedPortrait(String createdPortrait) {
            this.createdPortrait = createdPortrait;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.groupId);
            dest.writeString(this.name);
            dest.writeInt(this.memberCount);
            dest.writeString(this.createdUserId);
            dest.writeString(this.createdNickname);
            dest.writeInt(this.topicCount);
            dest.writeString(this.description);
            dest.writeString(this.img);
            dest.writeInt(this.status);
            dest.writeInt(this.joinStatus);
            dest.writeLong(this.createdTime);
            dest.writeString(this.createdSportrait);
            dest.writeString(this.createdPortrait);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.groupId = in.readString();
            this.name = in.readString();
            this.memberCount = in.readInt();
            this.createdUserId = in.readString();
            this.createdNickname = in.readString();
            this.topicCount = in.readInt();
            this.description = in.readString();
            this.img = in.readString();
            this.status = in.readInt();
            this.joinStatus = in.readInt();
            this.createdTime = in.readLong();
            this.createdSportrait = in.readString();
            this.createdPortrait = in.readString();
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
        dest.writeTypedList(this.data);
    }

    public PoemGroupDto() {
    }

    protected PoemGroupDto(Parcel in) {
        this.status = in.readInt();
        this.message = in.readString();
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Parcelable.Creator<PoemGroupDto> CREATOR = new Parcelable.Creator<PoemGroupDto>() {
        @Override
        public PoemGroupDto createFromParcel(Parcel source) {
            return new PoemGroupDto(source);
        }

        @Override
        public PoemGroupDto[] newArray(int size) {
            return new PoemGroupDto[size];
        }
    };
}
