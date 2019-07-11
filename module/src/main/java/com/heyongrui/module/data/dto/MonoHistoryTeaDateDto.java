package com.heyongrui.module.data.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by lambert on 2019/2/20.
 */

public class MonoHistoryTeaDateDto implements Parcelable {

    private int start;
    private List<RecentTeaBean> recent_tea;
    private boolean is_last_page;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public List<RecentTeaBean> getRecent_tea() {
        return recent_tea;
    }

    public void setRecent_tea(List<RecentTeaBean> recent_tea) {
        this.recent_tea = recent_tea;
    }

    public boolean isIs_last_page() {
        return is_last_page;
    }

    public void setIs_last_page(boolean is_last_page) {
        this.is_last_page = is_last_page;
    }

    public static class RecentTeaBean implements Parcelable {
        private String push_msg;
        private String bg_img_url;
        private String intro;
        private String share_image;
        private int id;
        private int kind;
        private String share_text;
        private String title;
        private BgImgUrlThumbBean bg_img_url_thumb;
        private String sub_title;
        private String read_time;
        private String release_date;

        public String getPush_msg() {
            return push_msg;
        }

        public void setPush_msg(String push_msg) {
            this.push_msg = push_msg;
        }

        public String getBg_img_url() {
            return bg_img_url;
        }

        public void setBg_img_url(String bg_img_url) {
            this.bg_img_url = bg_img_url;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getShare_image() {
            return share_image;
        }

        public void setShare_image(String share_image) {
            this.share_image = share_image;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getKind() {
            return kind;
        }

        public void setKind(int kind) {
            this.kind = kind;
        }

        public String getShare_text() {
            return share_text;
        }

        public void setShare_text(String share_text) {
            this.share_text = share_text;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public BgImgUrlThumbBean getBg_img_url_thumb() {
            return bg_img_url_thumb;
        }

        public void setBg_img_url_thumb(BgImgUrlThumbBean bg_img_url_thumb) {
            this.bg_img_url_thumb = bg_img_url_thumb;
        }

        public String getSub_title() {
            return sub_title;
        }

        public void setSub_title(String sub_title) {
            this.sub_title = sub_title;
        }

        public String getRead_time() {
            return read_time;
        }

        public void setRead_time(String read_time) {
            this.read_time = read_time;
        }

        public String getRelease_date() {
            return release_date;
        }

        public void setRelease_date(String release_date) {
            this.release_date = release_date;
        }

        public static class BgImgUrlThumbBean implements Parcelable {
            private String raw;
            private String format;
            private int error_code;
            private int width;
            private int height;

            public String getRaw() {
                return raw;
            }

            public void setRaw(String raw) {
                this.raw = raw;
            }

            public String getFormat() {
                return format;
            }

            public void setFormat(String format) {
                this.format = format;
            }

            public int getError_code() {
                return error_code;
            }

            public void setError_code(int error_code) {
                this.error_code = error_code;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.raw);
                dest.writeString(this.format);
                dest.writeInt(this.error_code);
                dest.writeInt(this.width);
                dest.writeInt(this.height);
            }

            public BgImgUrlThumbBean() {
            }

            protected BgImgUrlThumbBean(Parcel in) {
                this.raw = in.readString();
                this.format = in.readString();
                this.error_code = in.readInt();
                this.width = in.readInt();
                this.height = in.readInt();
            }

            public static final Creator<BgImgUrlThumbBean> CREATOR = new Creator<BgImgUrlThumbBean>() {
                @Override
                public BgImgUrlThumbBean createFromParcel(Parcel source) {
                    return new BgImgUrlThumbBean(source);
                }

                @Override
                public BgImgUrlThumbBean[] newArray(int size) {
                    return new BgImgUrlThumbBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.push_msg);
            dest.writeString(this.bg_img_url);
            dest.writeString(this.intro);
            dest.writeString(this.share_image);
            dest.writeInt(this.id);
            dest.writeInt(this.kind);
            dest.writeString(this.share_text);
            dest.writeString(this.title);
            dest.writeParcelable(this.bg_img_url_thumb, flags);
            dest.writeString(this.sub_title);
            dest.writeString(this.read_time);
            dest.writeString(this.release_date);
        }

        public RecentTeaBean() {
        }

        protected RecentTeaBean(Parcel in) {
            this.push_msg = in.readString();
            this.bg_img_url = in.readString();
            this.intro = in.readString();
            this.share_image = in.readString();
            this.id = in.readInt();
            this.kind = in.readInt();
            this.share_text = in.readString();
            this.title = in.readString();
            this.bg_img_url_thumb = in.readParcelable(BgImgUrlThumbBean.class.getClassLoader());
            this.sub_title = in.readString();
            this.read_time = in.readString();
            this.release_date = in.readString();
        }

        public static final Creator<RecentTeaBean> CREATOR = new Creator<RecentTeaBean>() {
            @Override
            public RecentTeaBean createFromParcel(Parcel source) {
                return new RecentTeaBean(source);
            }

            @Override
            public RecentTeaBean[] newArray(int size) {
                return new RecentTeaBean[size];
            }
        };
    }

    public MonoHistoryTeaDateDto() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.start);
        dest.writeTypedList(this.recent_tea);
        dest.writeByte(this.is_last_page ? (byte) 1 : (byte) 0);
    }

    protected MonoHistoryTeaDateDto(Parcel in) {
        this.start = in.readInt();
        this.recent_tea = in.createTypedArrayList(RecentTeaBean.CREATOR);
        this.is_last_page = in.readByte() != 0;
    }

    public static final Creator<MonoHistoryTeaDateDto> CREATOR = new Creator<MonoHistoryTeaDateDto>() {
        @Override
        public MonoHistoryTeaDateDto createFromParcel(Parcel source) {
            return new MonoHistoryTeaDateDto(source);
        }

        @Override
        public MonoHistoryTeaDateDto[] newArray(int size) {
            return new MonoHistoryTeaDateDto[size];
        }
    };
}
