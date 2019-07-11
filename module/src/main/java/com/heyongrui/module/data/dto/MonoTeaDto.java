package com.heyongrui.module.data.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by lambert on 2018/11/5.
 */

public class MonoTeaDto implements Parcelable {

    private TeaBean morning_tea;
    private TeaBean afternoon_tea;

    public TeaBean getMorning_tea() {
        return morning_tea;
    }

    public void setMorning_tea(TeaBean morning_tea) {
        this.morning_tea = morning_tea;
    }

    public TeaBean getAfternoon_tea() {
        return afternoon_tea;
    }

    public void setAfternoon_tea(TeaBean afternoon_tea) {
        this.afternoon_tea = afternoon_tea;
    }

    public static class TeaBean implements Parcelable {

        private String release_date;
        private String push_msg;
        private String title;
        private String sub_title;
        private String bg_img_url;
        private String intro;
        private String read_time;
        private List<EntityListBean> entity_list;

        public String getRelease_date() {
            return release_date;
        }

        public void setRelease_date(String release_date) {
            this.release_date = release_date;
        }

        public String getPush_msg() {
            return push_msg;
        }

        public void setPush_msg(String push_msg) {
            this.push_msg = push_msg;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSub_title() {
            return sub_title;
        }

        public void setSub_title(String sub_title) {
            this.sub_title = sub_title;
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

        public String getRead_time() {
            return read_time;
        }

        public void setRead_time(String read_time) {
            this.read_time = read_time;
        }

        public List<EntityListBean> getEntity_list() {
            return entity_list;
        }

        public void setEntity_list(List<EntityListBean> entity_list) {
            this.entity_list = entity_list;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.release_date);
            dest.writeString(this.push_msg);
            dest.writeString(this.title);
            dest.writeString(this.sub_title);
            dest.writeString(this.bg_img_url);
            dest.writeString(this.intro);
            dest.writeString(this.read_time);
            dest.writeTypedList(this.entity_list);
        }

        public TeaBean() {
        }

        protected TeaBean(Parcel in) {
            this.release_date = in.readString();
            this.push_msg = in.readString();
            this.title = in.readString();
            this.sub_title = in.readString();
            this.bg_img_url = in.readString();
            this.intro = in.readString();
            this.read_time = in.readString();
            this.entity_list = in.createTypedArrayList(EntityListBean.CREATOR);
        }

        public static final Creator<TeaBean> CREATOR = new Creator<TeaBean>() {
            @Override
            public TeaBean createFromParcel(Parcel source) {
                return new TeaBean(source);
            }

            @Override
            public TeaBean[] newArray(int size) {
                return new TeaBean[size];
            }
        };
    }

    public static class EntityListBean implements Parcelable {

        private MeowBean meow;

        public MeowBean getMeow() {
            return meow;
        }

        public void setMeow(MeowBean meow) {
            this.meow = meow;
        }

        public static class MeowBean implements Parcelable {

            private GroupBean group;
            private ThumbBean thumb;
            private UserBean user;
            private String text;
            private String title;
            private String description;
            private String rec_url;
            private List<ThumbBean> pics;
            private List<ThumbBean> images;

            public GroupBean getGroup() {
                return group;
            }

            public void setGroup(GroupBean group) {
                this.group = group;
            }

            public ThumbBean getThumb() {
                return thumb;
            }

            public void setThumb(ThumbBean thumb) {
                this.thumb = thumb;
            }

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
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

            public String getRec_url() {
                return rec_url;
            }

            public void setRec_url(String rec_url) {
                this.rec_url = rec_url;
            }

            public List<ThumbBean> getPics() {
                return pics;
            }

            public void setPics(List<ThumbBean> pics) {
                this.pics = pics;
            }

            public List<ThumbBean> getImages() {
                return images;
            }

            public void setImages(List<ThumbBean> images) {
                this.images = images;
            }

            public static class GroupBean implements Parcelable {

                private MasterInfoBean master_info;
                private ThumbBean thumb;
                private String logo_url;
                private String description;
                private String slogan;
                private String name;
                private String category;
                private int category_id;

                public MasterInfoBean getMaster_info() {
                    return master_info;
                }

                public void setMaster_info(MasterInfoBean master_info) {
                    this.master_info = master_info;
                }

                public ThumbBean getThumb() {
                    return thumb;
                }

                public void setThumb(ThumbBean thumb) {
                    this.thumb = thumb;
                }

                public String getLogo_url() {
                    return logo_url;
                }

                public void setLogo_url(String logo_url) {
                    this.logo_url = logo_url;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public String getSlogan() {
                    return slogan;
                }

                public void setSlogan(String slogan) {
                    this.slogan = slogan;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getCategory() {
                    return category;
                }

                public void setCategory(String category) {
                    this.category = category;
                }

                public int getCategory_id() {
                    return category_id;
                }

                public void setCategory_id(int category_id) {
                    this.category_id = category_id;
                }

                public static class MasterInfoBean implements Parcelable {
                    private String name;
                    private String avatar_url;

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getAvatar_url() {
                        return avatar_url;
                    }

                    public void setAvatar_url(String avatar_url) {
                        this.avatar_url = avatar_url;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(this.name);
                        dest.writeString(this.avatar_url);
                    }

                    public MasterInfoBean() {
                    }

                    protected MasterInfoBean(Parcel in) {
                        this.name = in.readString();
                        this.avatar_url = in.readString();
                    }

                    public static final Creator<MasterInfoBean> CREATOR = new Creator<MasterInfoBean>() {
                        @Override
                        public MasterInfoBean createFromParcel(Parcel source) {
                            return new MasterInfoBean(source);
                        }

                        @Override
                        public MasterInfoBean[] newArray(int size) {
                            return new MasterInfoBean[size];
                        }
                    };
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeParcelable(this.master_info, flags);
                    dest.writeParcelable(this.thumb, flags);
                    dest.writeString(this.logo_url);
                    dest.writeString(this.description);
                    dest.writeString(this.slogan);
                    dest.writeString(this.name);
                    dest.writeString(this.category);
                    dest.writeInt(this.category_id);
                }

                public GroupBean() {
                }

                protected GroupBean(Parcel in) {
                    this.master_info = in.readParcelable(MasterInfoBean.class.getClassLoader());
                    this.thumb = in.readParcelable(ThumbBean.class.getClassLoader());
                    this.logo_url = in.readString();
                    this.description = in.readString();
                    this.slogan = in.readString();
                    this.name = in.readString();
                    this.category = in.readString();
                    this.category_id = in.readInt();
                }

                public static final Creator<GroupBean> CREATOR = new Creator<GroupBean>() {
                    @Override
                    public GroupBean createFromParcel(Parcel source) {
                        return new GroupBean(source);
                    }

                    @Override
                    public GroupBean[] newArray(int size) {
                        return new GroupBean[size];
                    }
                };
            }

            public static class ThumbBean implements Parcelable {
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

                public ThumbBean() {
                }

                protected ThumbBean(Parcel in) {
                    this.raw = in.readString();
                    this.format = in.readString();
                    this.error_code = in.readInt();
                    this.width = in.readInt();
                    this.height = in.readInt();
                }

                public static final Creator<ThumbBean> CREATOR = new Creator<ThumbBean>() {
                    @Override
                    public ThumbBean createFromParcel(Parcel source) {
                        return new ThumbBean(source);
                    }

                    @Override
                    public ThumbBean[] newArray(int size) {
                        return new ThumbBean[size];
                    }
                };
            }

            public static class UserBean implements Parcelable {

                private String name;
                private String avatar_url;
                private ThumbBean avatar_url_thumb;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getAvatar_url() {
                    return avatar_url;
                }

                public void setAvatar_url(String avatar_url) {
                    this.avatar_url = avatar_url;
                }

                public ThumbBean getAvatar_url_thumb() {
                    return avatar_url_thumb;
                }

                public void setAvatar_url_thumb(ThumbBean avatar_url_thumb) {
                    this.avatar_url_thumb = avatar_url_thumb;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.name);
                    dest.writeString(this.avatar_url);
                    dest.writeParcelable(this.avatar_url_thumb, flags);
                }

                public UserBean() {
                }

                protected UserBean(Parcel in) {
                    this.name = in.readString();
                    this.avatar_url = in.readString();
                    this.avatar_url_thumb = in.readParcelable(ThumbBean.class.getClassLoader());
                }

                public static final Creator<UserBean> CREATOR = new Creator<UserBean>() {
                    @Override
                    public UserBean createFromParcel(Parcel source) {
                        return new UserBean(source);
                    }

                    @Override
                    public UserBean[] newArray(int size) {
                        return new UserBean[size];
                    }
                };
            }

            public MeowBean() {
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeParcelable(this.group, flags);
                dest.writeParcelable(this.thumb, flags);
                dest.writeParcelable(this.user, flags);
                dest.writeString(this.text);
                dest.writeString(this.title);
                dest.writeString(this.description);
                dest.writeString(this.rec_url);
                dest.writeTypedList(this.pics);
                dest.writeTypedList(this.images);
            }

            protected MeowBean(Parcel in) {
                this.group = in.readParcelable(GroupBean.class.getClassLoader());
                this.thumb = in.readParcelable(ThumbBean.class.getClassLoader());
                this.user = in.readParcelable(UserBean.class.getClassLoader());
                this.text = in.readString();
                this.title = in.readString();
                this.description = in.readString();
                this.rec_url = in.readString();
                this.pics = in.createTypedArrayList(ThumbBean.CREATOR);
                this.images = in.createTypedArrayList(ThumbBean.CREATOR);
            }

            public static final Creator<MeowBean> CREATOR = new Creator<MeowBean>() {
                @Override
                public MeowBean createFromParcel(Parcel source) {
                    return new MeowBean(source);
                }

                @Override
                public MeowBean[] newArray(int size) {
                    return new MeowBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(this.meow, flags);
        }

        public EntityListBean() {
        }

        protected EntityListBean(Parcel in) {
            this.meow = in.readParcelable(MeowBean.class.getClassLoader());
        }

        public static final Creator<EntityListBean> CREATOR = new Creator<EntityListBean>() {
            @Override
            public EntityListBean createFromParcel(Parcel source) {
                return new EntityListBean(source);
            }

            @Override
            public EntityListBean[] newArray(int size) {
                return new EntityListBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.morning_tea, flags);
        dest.writeParcelable(this.afternoon_tea, flags);
    }

    public MonoTeaDto() {
    }

    protected MonoTeaDto(Parcel in) {
        this.morning_tea = in.readParcelable(TeaBean.class.getClassLoader());
        this.afternoon_tea = in.readParcelable(TeaBean.class.getClassLoader());
    }

    public static final Creator<MonoTeaDto> CREATOR = new Creator<MonoTeaDto>() {
        @Override
        public MonoTeaDto createFromParcel(Parcel source) {
            return new MonoTeaDto(source);
        }

        @Override
        public MonoTeaDto[] newArray(int size) {
            return new MonoTeaDto[size];
        }
    };
}
