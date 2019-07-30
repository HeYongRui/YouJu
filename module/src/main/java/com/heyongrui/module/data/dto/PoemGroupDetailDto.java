package com.heyongrui.module.data.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class PoemGroupDetailDto implements Parcelable {
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
        private String topicId;
        private String createdUserId;
        private String title;
        private String description;
        private int reply;
        private int view;
        private int follow;
        private int followStatus;
        private int status;
        private int newCount;
        private String createdNickname;
        private String createdSportrait;
        private String createdPortrait;
        private long createdTime;
        private long topTime;
        private long updatedTime;
        private int type;
        private int top;
        private String groupId;
        private int hide;
//        private String cover;

        public String getTopicId() {
            return topicId;
        }

        public void setTopicId(String topicId) {
            this.topicId = topicId;
        }

        public String getCreatedUserId() {
            return createdUserId;
        }

        public void setCreatedUserId(String createdUserId) {
            this.createdUserId = createdUserId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getReply() {
            return reply;
        }

        public void setReply(int reply) {
            this.reply = reply;
        }

        public int getView() {
            return view;
        }

        public void setView(int view) {
            this.view = view;
        }

        public int getFollow() {
            return follow;
        }

        public void setFollow(int follow) {
            this.follow = follow;
        }

        public int getFollowStatus() {
            return followStatus;
        }

        public void setFollowStatus(int followStatus) {
            this.followStatus = followStatus;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getNewCount() {
            return newCount;
        }

        public void setNewCount(int newCount) {
            this.newCount = newCount;
        }

        public String getCreatedNickname() {
            return createdNickname;
        }

        public void setCreatedNickname(String createdNickname) {
            this.createdNickname = createdNickname;
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

        public long getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(long createdTime) {
            this.createdTime = createdTime;
        }

        public long getTopTime() {
            return topTime;
        }

        public void setTopTime(long topTime) {
            this.topTime = topTime;
        }

        public long getUpdatedTime() {
            return updatedTime;
        }

        public void setUpdatedTime(long updatedTime) {
            this.updatedTime = updatedTime;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getTop() {
            return top;
        }

        public void setTop(int top) {
            this.top = top;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public int getHide() {
            return hide;
        }

        public void setHide(int hide) {
            this.hide = hide;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.topicId);
            dest.writeString(this.createdUserId);
            dest.writeString(this.title);
            dest.writeString(this.description);
            dest.writeInt(this.reply);
            dest.writeInt(this.view);
            dest.writeInt(this.follow);
            dest.writeInt(this.followStatus);
            dest.writeInt(this.status);
            dest.writeInt(this.newCount);
            dest.writeString(this.createdNickname);
            dest.writeString(this.createdSportrait);
            dest.writeString(this.createdPortrait);
            dest.writeLong(this.createdTime);
            dest.writeLong(this.topTime);
            dest.writeLong(this.updatedTime);
            dest.writeInt(this.type);
            dest.writeInt(this.top);
            dest.writeString(this.groupId);
            dest.writeInt(this.hide);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.topicId = in.readString();
            this.createdUserId = in.readString();
            this.title = in.readString();
            this.description = in.readString();
            this.reply = in.readInt();
            this.view = in.readInt();
            this.follow = in.readInt();
            this.followStatus = in.readInt();
            this.status = in.readInt();
            this.newCount = in.readInt();
            this.createdNickname = in.readString();
            this.createdSportrait = in.readString();
            this.createdPortrait = in.readString();
            this.createdTime = in.readLong();
            this.topTime = in.readLong();
            this.updatedTime = in.readLong();
            this.type = in.readInt();
            this.top = in.readInt();
            this.groupId = in.readString();
            this.hide = in.readInt();
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

    public PoemGroupDetailDto() {
    }

    protected PoemGroupDetailDto(Parcel in) {
        this.status = in.readInt();
        this.message = in.readString();
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Parcelable.Creator<PoemGroupDetailDto> CREATOR = new Parcelable.Creator<PoemGroupDetailDto>() {
        @Override
        public PoemGroupDetailDto createFromParcel(Parcel source) {
            return new PoemGroupDetailDto(source);
        }

        @Override
        public PoemGroupDetailDto[] newArray(int size) {
            return new PoemGroupDetailDto[size];
        }
    };
}
