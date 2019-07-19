package com.heyongrui.module.data.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class PoetryDto implements Parcelable {

    private String status;
    private int errCode;
    private String errMessage;
    private DataBean data;
    private String token;
    private String ipAddress;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public static class DataBean implements Parcelable {
        private String id;
        private String content;
        private double popularity;
        private OriginBean origin;
        private String recommendedReason;
        private String cacheAt;
        private List<String> matchTags;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public double getPopularity() {
            return popularity;
        }

        public void setPopularity(double popularity) {
            this.popularity = popularity;
        }

        public OriginBean getOrigin() {
            return origin;
        }

        public void setOrigin(OriginBean origin) {
            this.origin = origin;
        }

        public String getRecommendedReason() {
            return recommendedReason;
        }

        public void setRecommendedReason(String recommendedReason) {
            this.recommendedReason = recommendedReason;
        }

        public String getCacheAt() {
            return cacheAt;
        }

        public void setCacheAt(String cacheAt) {
            this.cacheAt = cacheAt;
        }

        public List<String> getMatchTags() {
            return matchTags;
        }

        public void setMatchTags(List<String> matchTags) {
            this.matchTags = matchTags;
        }

        public static class OriginBean implements Parcelable {
            private String title;
            private String dynasty;
            private String author;
            private List<String> content;
            private List<String> translate;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDynasty() {
                return dynasty;
            }

            public void setDynasty(String dynasty) {
                this.dynasty = dynasty;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public List<String> getContent() {
                return content;
            }

            public void setContent(List<String> content) {
                this.content = content;
            }

            public List<String> getTranslate() {
                return translate;
            }

            public void setTranslate(List<String> translate) {
                this.translate = translate;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.title);
                dest.writeString(this.dynasty);
                dest.writeString(this.author);
                dest.writeStringList(this.content);
                dest.writeStringList(this.translate);
            }

            public OriginBean() {
            }

            protected OriginBean(Parcel in) {
                this.title = in.readString();
                this.dynasty = in.readString();
                this.author = in.readString();
                this.content = in.createStringArrayList();
                this.translate = in.createStringArrayList();
            }

            public static final Parcelable.Creator<OriginBean> CREATOR = new Parcelable.Creator<OriginBean>() {
                @Override
                public OriginBean createFromParcel(Parcel source) {
                    return new OriginBean(source);
                }

                @Override
                public OriginBean[] newArray(int size) {
                    return new OriginBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.content);
            dest.writeDouble(this.popularity);
            dest.writeParcelable(this.origin, flags);
            dest.writeString(this.recommendedReason);
            dest.writeString(this.cacheAt);
            dest.writeStringList(this.matchTags);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.id = in.readString();
            this.content = in.readString();
            this.popularity = in.readDouble();
            this.origin = in.readParcelable(OriginBean.class.getClassLoader());
            this.recommendedReason = in.readString();
            this.cacheAt = in.readString();
            this.matchTags = in.createStringArrayList();
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
        dest.writeString(this.status);
        dest.writeInt(this.errCode);
        dest.writeString(this.errMessage);
        dest.writeParcelable(this.data, flags);
        dest.writeString(this.token);
        dest.writeString(this.ipAddress);
    }

    public PoetryDto() {
    }

    protected PoetryDto(Parcel in) {
        this.status = in.readString();
        this.errCode = in.readInt();
        this.errMessage = in.readString();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
        this.token = in.readString();
        this.ipAddress = in.readString();
    }

    public static final Parcelable.Creator<PoetryDto> CREATOR = new Parcelable.Creator<PoetryDto>() {
        @Override
        public PoetryDto createFromParcel(Parcel source) {
            return new PoetryDto(source);
        }

        @Override
        public PoetryDto[] newArray(int size) {
            return new PoetryDto[size];
        }
    };
}
