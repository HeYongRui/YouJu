package com.heyongrui.module.data.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by lambert on 2018/11/21.
 */

public class MonoCategoryDto implements Parcelable {

    private int start;
    private boolean is_last_page;
    private GroupsBean groups;
    private List<MeowsBean> meows;
    private List<TagsBean> tags;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public boolean isIs_last_page() {
        return is_last_page;
    }

    public void setIs_last_page(boolean is_last_page) {
        this.is_last_page = is_last_page;
    }

    public GroupsBean getGroups() {
        return groups;
    }

    public void setGroups(GroupsBean groups) {
        this.groups = groups;
    }

    public List<MeowsBean> getMeows() {
        return meows;
    }

    public void setMeows(List<MeowsBean> meows) {
        this.meows = meows;
    }

    public List<TagsBean> getTags() {
        return tags;
    }

    public void setTags(List<TagsBean> tags) {
        this.tags = tags;
    }

    public static class GroupsBean implements Parcelable {
        private String type;
        private String name;
        private List<String> entity_list;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getEntity_list() {
            return entity_list;
        }

        public void setEntity_list(List<String> entity_list) {
            this.entity_list = entity_list;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.type);
            dest.writeString(this.name);
            dest.writeStringList(this.entity_list);
        }

        public GroupsBean() {
        }

        protected GroupsBean(Parcel in) {
            this.type = in.readString();
            this.name = in.readString();
            this.entity_list = in.createStringArrayList();
        }

        public static final Creator<GroupsBean> CREATOR = new Creator<GroupsBean>() {
            @Override
            public GroupsBean createFromParcel(Parcel source) {
                return new GroupsBean(source);
            }

            @Override
            public GroupsBean[] newArray(int size) {
                return new GroupsBean[size];
            }
        };
    }

    public static class MeowsBean implements Parcelable {
        private MeowBean meow;

        public MeowBean getMeow() {
            return meow;
        }

        public void setMeow(MeowBean meow) {
            this.meow = meow;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(this.meow, flags);
        }

        public MeowsBean() {
        }

        protected MeowsBean(Parcel in) {
            this.meow = in.readParcelable(MeowBean.class.getClassLoader());
        }

        public static final Creator<MeowsBean> CREATOR = new Creator<MeowsBean>() {
            @Override
            public MeowsBean createFromParcel(Parcel source) {
                return new MeowsBean(source);
            }

            @Override
            public MeowsBean[] newArray(int size) {
                return new MeowsBean[size];
            }
        };
    }

    public static class MeowBean implements Parcelable {

        private int bang_count;
        private boolean is_folded;
        private String banner_img_url;
        private int object_type;
        private int create_time;
        private List<ThumbBean> images;
        private int id;
        private GroupBean group;
        private ThumbBean thumb;
        private String title;
        private String exposure_url;
        private boolean has_video;
        private int comment_count;
        private int is_post_by_master;
        private String description;
        private String meow_status;
        private UserBean user;
        private boolean is_external_link;
        private int kind;
        private String share_text;
        private String share_img;
        private String intro;
        private int is_filtered;
        private int meow_type;

        public int getBang_count() {
            return bang_count;
        }

        public void setBang_count(int bang_count) {
            this.bang_count = bang_count;
        }

        public boolean isIs_folded() {
            return is_folded;
        }

        public void setIs_folded(boolean is_folded) {
            this.is_folded = is_folded;
        }

        public String getBanner_img_url() {
            return banner_img_url;
        }

        public void setBanner_img_url(String banner_img_url) {
            this.banner_img_url = banner_img_url;
        }

        public int getObject_type() {
            return object_type;
        }

        public void setObject_type(int object_type) {
            this.object_type = object_type;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public List<ThumbBean> getImages() {
            return images;
        }

        public void setImages(List<ThumbBean> images) {
            this.images = images;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getExposure_url() {
            return exposure_url;
        }

        public void setExposure_url(String exposure_url) {
            this.exposure_url = exposure_url;
        }

        public boolean isHas_video() {
            return has_video;
        }

        public void setHas_video(boolean has_video) {
            this.has_video = has_video;
        }

        public int getComment_count() {
            return comment_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public int getIs_post_by_master() {
            return is_post_by_master;
        }

        public void setIs_post_by_master(int is_post_by_master) {
            this.is_post_by_master = is_post_by_master;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getMeow_status() {
            return meow_status;
        }

        public void setMeow_status(String meow_status) {
            this.meow_status = meow_status;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public boolean isIs_external_link() {
            return is_external_link;
        }

        public void setIs_external_link(boolean is_external_link) {
            this.is_external_link = is_external_link;
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

        public String getShare_img() {
            return share_img;
        }

        public void setShare_img(String share_img) {
            this.share_img = share_img;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public int getIs_filtered() {
            return is_filtered;
        }

        public void setIs_filtered(int is_filtered) {
            this.is_filtered = is_filtered;
        }

        public int getMeow_type() {
            return meow_type;
        }

        public void setMeow_type(int meow_type) {
            this.meow_type = meow_type;
        }

        public static class GroupBean implements Parcelable {
            private String status;
            private String category;
            private int kind;
            private int topic_content_num;
            private String slogan;
            private String name;
            private int discuss_content_num;
            private int cert_kind_id;
            private ThumbBean thumb;
            private MasterInfoBean master_info;
            private int member_num;
            private int publisher_type;
            private int campaign_num;
            private boolean has_playlist;
            private ThumbBean logo_url_thumb;
            private int category_id;
            private int id;
            private String logo_url;
            private String description;

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public int getKind() {
                return kind;
            }

            public void setKind(int kind) {
                this.kind = kind;
            }

            public int getTopic_content_num() {
                return topic_content_num;
            }

            public void setTopic_content_num(int topic_content_num) {
                this.topic_content_num = topic_content_num;
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

            public int getDiscuss_content_num() {
                return discuss_content_num;
            }

            public void setDiscuss_content_num(int discuss_content_num) {
                this.discuss_content_num = discuss_content_num;
            }

            public int getCert_kind_id() {
                return cert_kind_id;
            }

            public void setCert_kind_id(int cert_kind_id) {
                this.cert_kind_id = cert_kind_id;
            }

            public ThumbBean getThumb() {
                return thumb;
            }

            public void setThumb(ThumbBean thumb) {
                this.thumb = thumb;
            }

            public MasterInfoBean getMaster_info() {
                return master_info;
            }

            public void setMaster_info(MasterInfoBean master_info) {
                this.master_info = master_info;
            }

            public int getMember_num() {
                return member_num;
            }

            public void setMember_num(int member_num) {
                this.member_num = member_num;
            }

            public int getPublisher_type() {
                return publisher_type;
            }

            public void setPublisher_type(int publisher_type) {
                this.publisher_type = publisher_type;
            }

            public int getCampaign_num() {
                return campaign_num;
            }

            public void setCampaign_num(int campaign_num) {
                this.campaign_num = campaign_num;
            }

            public boolean isHas_playlist() {
                return has_playlist;
            }

            public void setHas_playlist(boolean has_playlist) {
                this.has_playlist = has_playlist;
            }

            public ThumbBean getLogo_url_thumb() {
                return logo_url_thumb;
            }

            public void setLogo_url_thumb(ThumbBean logo_url_thumb) {
                this.logo_url_thumb = logo_url_thumb;
            }

            public int getCategory_id() {
                return category_id;
            }

            public void setCategory_id(int category_id) {
                this.category_id = category_id;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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

            public static class MasterInfoBean implements Parcelable {

                private String user_id;
                private String name;
                private boolean is_anonymous;
                private int horoscope;
                private int gender;
                private String self_description;
                private String avatar_url;
                private ThumbBean avatar_url_thumb;
                private CoordinateBean coordinate;

                public String getUser_id() {
                    return user_id;
                }

                public void setUser_id(String user_id) {
                    this.user_id = user_id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public boolean isIs_anonymous() {
                    return is_anonymous;
                }

                public void setIs_anonymous(boolean is_anonymous) {
                    this.is_anonymous = is_anonymous;
                }

                public int getHoroscope() {
                    return horoscope;
                }

                public void setHoroscope(int horoscope) {
                    this.horoscope = horoscope;
                }

                public int getGender() {
                    return gender;
                }

                public void setGender(int gender) {
                    this.gender = gender;
                }

                public String getSelf_description() {
                    return self_description;
                }

                public void setSelf_description(String self_description) {
                    this.self_description = self_description;
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

                public CoordinateBean getCoordinate() {
                    return coordinate;
                }

                public void setCoordinate(CoordinateBean coordinate) {
                    this.coordinate = coordinate;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.user_id);
                    dest.writeString(this.name);
                    dest.writeByte(this.is_anonymous ? (byte) 1 : (byte) 0);
                    dest.writeInt(this.horoscope);
                    dest.writeInt(this.gender);
                    dest.writeString(this.self_description);
                    dest.writeString(this.avatar_url);
                    dest.writeParcelable(this.avatar_url_thumb, flags);
                    dest.writeParcelable(this.coordinate, flags);
                }

                public MasterInfoBean() {
                }

                protected MasterInfoBean(Parcel in) {
                    this.user_id = in.readString();
                    this.name = in.readString();
                    this.is_anonymous = in.readByte() != 0;
                    this.horoscope = in.readInt();
                    this.gender = in.readInt();
                    this.self_description = in.readString();
                    this.avatar_url = in.readString();
                    this.avatar_url_thumb = in.readParcelable(ThumbBean.class.getClassLoader());
                    this.coordinate = in.readParcelable(CoordinateBean.class.getClassLoader());
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
                dest.writeString(this.status);
                dest.writeString(this.category);
                dest.writeInt(this.kind);
                dest.writeInt(this.topic_content_num);
                dest.writeString(this.slogan);
                dest.writeString(this.name);
                dest.writeInt(this.discuss_content_num);
                dest.writeInt(this.cert_kind_id);
                dest.writeParcelable(this.thumb, flags);
                dest.writeParcelable(this.master_info, flags);
                dest.writeInt(this.member_num);
                dest.writeInt(this.publisher_type);
                dest.writeInt(this.campaign_num);
                dest.writeByte(this.has_playlist ? (byte) 1 : (byte) 0);
                dest.writeParcelable(this.logo_url_thumb, flags);
                dest.writeInt(this.category_id);
                dest.writeInt(this.id);
                dest.writeString(this.logo_url);
                dest.writeString(this.description);
            }

            public GroupBean() {
            }

            protected GroupBean(Parcel in) {
                this.status = in.readString();
                this.category = in.readString();
                this.kind = in.readInt();
                this.topic_content_num = in.readInt();
                this.slogan = in.readString();
                this.name = in.readString();
                this.discuss_content_num = in.readInt();
                this.cert_kind_id = in.readInt();
                this.thumb = in.readParcelable(ThumbBean.class.getClassLoader());
                this.master_info = in.readParcelable(MasterInfoBean.class.getClassLoader());
                this.member_num = in.readInt();
                this.publisher_type = in.readInt();
                this.campaign_num = in.readInt();
                this.has_playlist = in.readByte() != 0;
                this.logo_url_thumb = in.readParcelable(ThumbBean.class.getClassLoader());
                this.category_id = in.readInt();
                this.id = in.readInt();
                this.logo_url = in.readString();
                this.description = in.readString();
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

        public static class UserBean implements Parcelable {

            private String user_id;
            private String name;
            private boolean is_anonymous;
            private int horoscope;
            private int gender;
            private String self_description;
            private String avatar_url;
            private ThumbBean avatar_url_thumb;
            private CoordinateBean coordinate;

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public boolean isIs_anonymous() {
                return is_anonymous;
            }

            public void setIs_anonymous(boolean is_anonymous) {
                this.is_anonymous = is_anonymous;
            }

            public int getHoroscope() {
                return horoscope;
            }

            public void setHoroscope(int horoscope) {
                this.horoscope = horoscope;
            }

            public int getGender() {
                return gender;
            }

            public void setGender(int gender) {
                this.gender = gender;
            }

            public String getSelf_description() {
                return self_description;
            }

            public void setSelf_description(String self_description) {
                this.self_description = self_description;
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

            public CoordinateBean getCoordinate() {
                return coordinate;
            }

            public void setCoordinate(CoordinateBean coordinate) {
                this.coordinate = coordinate;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.user_id);
                dest.writeString(this.name);
                dest.writeByte(this.is_anonymous ? (byte) 1 : (byte) 0);
                dest.writeInt(this.horoscope);
                dest.writeInt(this.gender);
                dest.writeString(this.self_description);
                dest.writeString(this.avatar_url);
                dest.writeParcelable(this.avatar_url_thumb, flags);
                dest.writeParcelable(this.coordinate, flags);
            }

            public UserBean() {
            }

            protected UserBean(Parcel in) {
                this.user_id = in.readString();
                this.name = in.readString();
                this.is_anonymous = in.readByte() != 0;
                this.horoscope = in.readInt();
                this.gender = in.readInt();
                this.self_description = in.readString();
                this.avatar_url = in.readString();
                this.avatar_url_thumb = in.readParcelable(ThumbBean.class.getClassLoader());
                this.coordinate = in.readParcelable(CoordinateBean.class.getClassLoader());
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

        public static class CoordinateBean implements Parcelable {

            private double latitude;
            private String area_name;
            private double longitude;

            public double getLatitude() {
                return latitude;
            }

            public void setLatitude(double latitude) {
                this.latitude = latitude;
            }

            public String getArea_name() {
                return area_name;
            }

            public void setArea_name(String area_name) {
                this.area_name = area_name;
            }

            public double getLongitude() {
                return longitude;
            }

            public void setLongitude(double longitude) {
                this.longitude = longitude;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeDouble(this.latitude);
                dest.writeString(this.area_name);
                dest.writeDouble(this.longitude);
            }

            public CoordinateBean() {
            }

            protected CoordinateBean(Parcel in) {
                this.latitude = in.readDouble();
                this.area_name = in.readString();
                this.longitude = in.readDouble();
            }

            public static final Creator<CoordinateBean> CREATOR = new Creator<CoordinateBean>() {
                @Override
                public CoordinateBean createFromParcel(Parcel source) {
                    return new CoordinateBean(source);
                }

                @Override
                public CoordinateBean[] newArray(int size) {
                    return new CoordinateBean[size];
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
            dest.writeInt(this.bang_count);
            dest.writeByte(this.is_folded ? (byte) 1 : (byte) 0);
            dest.writeString(this.banner_img_url);
            dest.writeInt(this.object_type);
            dest.writeInt(this.create_time);
            dest.writeTypedList(this.images);
            dest.writeInt(this.id);
            dest.writeParcelable(this.group, flags);
            dest.writeParcelable(this.thumb, flags);
            dest.writeString(this.title);
            dest.writeString(this.exposure_url);
            dest.writeByte(this.has_video ? (byte) 1 : (byte) 0);
            dest.writeInt(this.comment_count);
            dest.writeInt(this.is_post_by_master);
            dest.writeString(this.description);
            dest.writeString(this.meow_status);
            dest.writeParcelable(this.user, flags);
            dest.writeByte(this.is_external_link ? (byte) 1 : (byte) 0);
            dest.writeInt(this.kind);
            dest.writeString(this.share_text);
            dest.writeString(this.share_img);
            dest.writeString(this.intro);
            dest.writeInt(this.is_filtered);
            dest.writeInt(this.meow_type);
        }

        protected MeowBean(Parcel in) {
            this.bang_count = in.readInt();
            this.is_folded = in.readByte() != 0;
            this.banner_img_url = in.readString();
            this.object_type = in.readInt();
            this.create_time = in.readInt();
            this.images = in.createTypedArrayList(ThumbBean.CREATOR);
            this.id = in.readInt();
            this.group = in.readParcelable(GroupBean.class.getClassLoader());
            this.thumb = in.readParcelable(ThumbBean.class.getClassLoader());
            this.title = in.readString();
            this.exposure_url = in.readString();
            this.has_video = in.readByte() != 0;
            this.comment_count = in.readInt();
            this.is_post_by_master = in.readInt();
            this.description = in.readString();
            this.meow_status = in.readString();
            this.user = in.readParcelable(UserBean.class.getClassLoader());
            this.is_external_link = in.readByte() != 0;
            this.kind = in.readInt();
            this.share_text = in.readString();
            this.share_img = in.readString();
            this.intro = in.readString();
            this.is_filtered = in.readInt();
            this.meow_type = in.readInt();
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

    public static class TagsBean implements Parcelable {
        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.name);
        }

        public TagsBean() {
        }

        protected TagsBean(Parcel in) {
            this.id = in.readInt();
            this.name = in.readString();
        }

        public static final Creator<TagsBean> CREATOR = new Creator<TagsBean>() {
            @Override
            public TagsBean createFromParcel(Parcel source) {
                return new TagsBean(source);
            }

            @Override
            public TagsBean[] newArray(int size) {
                return new TagsBean[size];
            }
        };
    }

    public MonoCategoryDto() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.start);
        dest.writeByte(this.is_last_page ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.groups, flags);
        dest.writeTypedList(this.meows);
        dest.writeTypedList(this.tags);
    }

    protected MonoCategoryDto(Parcel in) {
        this.start = in.readInt();
        this.is_last_page = in.readByte() != 0;
        this.groups = in.readParcelable(GroupsBean.class.getClassLoader());
        this.meows = in.createTypedArrayList(MeowsBean.CREATOR);
        this.tags = in.createTypedArrayList(TagsBean.CREATOR);
    }

    public static final Creator<MonoCategoryDto> CREATOR = new Creator<MonoCategoryDto>() {
        @Override
        public MonoCategoryDto createFromParcel(Parcel source) {
            return new MonoCategoryDto(source);
        }

        @Override
        public MonoCategoryDto[] newArray(int size) {
            return new MonoCategoryDto[size];
        }
    };
}
