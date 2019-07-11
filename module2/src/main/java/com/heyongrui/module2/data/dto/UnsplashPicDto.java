package com.heyongrui.module2.data.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lambert on 2018/10/23.
 */

public class UnsplashPicDto implements Parcelable, Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String created_at;
    private String updated_at;
    private int width;
    private int height;
    private String color;
    private String description;
    private UrlsBean urls;
    private LinksBean links;
    private boolean sponsored;
    private String sponsored_by;
    private String sponsored_impressions_id;
    private int likes;
    private boolean liked_by_user;
    private String slug;
    private UserBean user;
    private List<String> categories;
    private List<String> current_user_collections;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UrlsBean getUrls() {
        return urls;
    }

    public void setUrls(UrlsBean urls) {
        this.urls = urls;
    }

    public LinksBean getLinks() {
        return links;
    }

    public void setLinks(LinksBean links) {
        this.links = links;
    }

    public boolean isSponsored() {
        return sponsored;
    }

    public void setSponsored(boolean sponsored) {
        this.sponsored = sponsored;
    }

    public String getSponsored_by() {
        return sponsored_by;
    }

    public void setSponsored_by(String sponsored_by) {
        this.sponsored_by = sponsored_by;
    }

    public String getSponsored_impressions_id() {
        return sponsored_impressions_id;
    }

    public void setSponsored_impressions_id(String sponsored_impressions_id) {
        this.sponsored_impressions_id = sponsored_impressions_id;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public boolean isLiked_by_user() {
        return liked_by_user;
    }

    public void setLiked_by_user(boolean liked_by_user) {
        this.liked_by_user = liked_by_user;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getCurrent_user_collections() {
        return current_user_collections;
    }

    public void setCurrent_user_collections(List<String> current_user_collections) {
        this.current_user_collections = current_user_collections;
    }

    public static class UrlsBean implements Parcelable, Serializable {
        private static final long serialVersionUID = 1L;

        private String raw;
        private String full;
        private String regular;
        private String small;
        private String thumb;

        public String getRaw() {
            return raw;
        }

        public void setRaw(String raw) {
            this.raw = raw;
        }

        public String getFull() {
            return full;
        }

        public void setFull(String full) {
            this.full = full;
        }

        public String getRegular() {
            return regular;
        }

        public void setRegular(String regular) {
            this.regular = regular;
        }

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.raw);
            dest.writeString(this.full);
            dest.writeString(this.regular);
            dest.writeString(this.small);
            dest.writeString(this.thumb);
        }

        public UrlsBean() {
        }

        protected UrlsBean(Parcel in) {
            this.raw = in.readString();
            this.full = in.readString();
            this.regular = in.readString();
            this.small = in.readString();
            this.thumb = in.readString();
        }

        public static final Creator<UrlsBean> CREATOR = new Creator<UrlsBean>() {
            @Override
            public UrlsBean createFromParcel(Parcel source) {
                return new UrlsBean(source);
            }

            @Override
            public UrlsBean[] newArray(int size) {
                return new UrlsBean[size];
            }
        };
    }

    public static class LinksBean implements Parcelable, Serializable {
        private static final long serialVersionUID = 1L;

        private String self;
        private String html;
        private String download;
        private String download_location;

        public String getSelf() {
            return self;
        }

        public void setSelf(String self) {
            this.self = self;
        }

        public String getHtml() {
            return html;
        }

        public void setHtml(String html) {
            this.html = html;
        }

        public String getDownload() {
            return download;
        }

        public void setDownload(String download) {
            this.download = download;
        }

        public String getDownload_location() {
            return download_location;
        }

        public void setDownload_location(String download_location) {
            this.download_location = download_location;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.self);
            dest.writeString(this.html);
            dest.writeString(this.download);
            dest.writeString(this.download_location);
        }

        public LinksBean() {
        }

        protected LinksBean(Parcel in) {
            this.self = in.readString();
            this.html = in.readString();
            this.download = in.readString();
            this.download_location = in.readString();
        }

        public static final Creator<LinksBean> CREATOR = new Creator<LinksBean>() {
            @Override
            public LinksBean createFromParcel(Parcel source) {
                return new LinksBean(source);
            }

            @Override
            public LinksBean[] newArray(int size) {
                return new LinksBean[size];
            }
        };
    }

    public static class UserBean implements Parcelable, Serializable {
        private static final long serialVersionUID = 1L;

        private String id;
        private String updated_at;
        private String username;
        private String name;
        private String first_name;
        private String last_name;
        private String twitter_username;
        private String portfolio_url;
        private String bio;
        private String location;
        private LinksBeanX links;
        private ProfileImageBean profile_image;
        private String instagram_username;
        private int total_collections;
        private int total_likes;
        private int total_photos;
        private boolean accepted_tos;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getTwitter_username() {
            return twitter_username;
        }

        public void setTwitter_username(String twitter_username) {
            this.twitter_username = twitter_username;
        }

        public String getPortfolio_url() {
            return portfolio_url;
        }

        public void setPortfolio_url(String portfolio_url) {
            this.portfolio_url = portfolio_url;
        }

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public LinksBeanX getLinks() {
            return links;
        }

        public void setLinks(LinksBeanX links) {
            this.links = links;
        }

        public ProfileImageBean getProfile_image() {
            return profile_image;
        }

        public void setProfile_image(ProfileImageBean profile_image) {
            this.profile_image = profile_image;
        }

        public String getInstagram_username() {
            return instagram_username;
        }

        public void setInstagram_username(String instagram_username) {
            this.instagram_username = instagram_username;
        }

        public int getTotal_collections() {
            return total_collections;
        }

        public void setTotal_collections(int total_collections) {
            this.total_collections = total_collections;
        }

        public int getTotal_likes() {
            return total_likes;
        }

        public void setTotal_likes(int total_likes) {
            this.total_likes = total_likes;
        }

        public int getTotal_photos() {
            return total_photos;
        }

        public void setTotal_photos(int total_photos) {
            this.total_photos = total_photos;
        }

        public boolean isAccepted_tos() {
            return accepted_tos;
        }

        public void setAccepted_tos(boolean accepted_tos) {
            this.accepted_tos = accepted_tos;
        }

        public static class LinksBeanX implements Parcelable, Serializable {
            private static final long serialVersionUID = 1L;

            private String self;
            private String html;
            private String photos;
            private String likes;
            private String portfolio;
            private String following;
            private String followers;

            public String getSelf() {
                return self;
            }

            public void setSelf(String self) {
                this.self = self;
            }

            public String getHtml() {
                return html;
            }

            public void setHtml(String html) {
                this.html = html;
            }

            public String getPhotos() {
                return photos;
            }

            public void setPhotos(String photos) {
                this.photos = photos;
            }

            public String getLikes() {
                return likes;
            }

            public void setLikes(String likes) {
                this.likes = likes;
            }

            public String getPortfolio() {
                return portfolio;
            }

            public void setPortfolio(String portfolio) {
                this.portfolio = portfolio;
            }

            public String getFollowing() {
                return following;
            }

            public void setFollowing(String following) {
                this.following = following;
            }

            public String getFollowers() {
                return followers;
            }

            public void setFollowers(String followers) {
                this.followers = followers;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.self);
                dest.writeString(this.html);
                dest.writeString(this.photos);
                dest.writeString(this.likes);
                dest.writeString(this.portfolio);
                dest.writeString(this.following);
                dest.writeString(this.followers);
            }

            public LinksBeanX() {
            }

            protected LinksBeanX(Parcel in) {
                this.self = in.readString();
                this.html = in.readString();
                this.photos = in.readString();
                this.likes = in.readString();
                this.portfolio = in.readString();
                this.following = in.readString();
                this.followers = in.readString();
            }

            public static final Creator<LinksBeanX> CREATOR = new Creator<LinksBeanX>() {
                @Override
                public LinksBeanX createFromParcel(Parcel source) {
                    return new LinksBeanX(source);
                }

                @Override
                public LinksBeanX[] newArray(int size) {
                    return new LinksBeanX[size];
                }
            };
        }

        public static class ProfileImageBean implements Parcelable, Serializable {
            private static final long serialVersionUID = 1L;

            private String small;
            private String medium;
            private String large;

            public String getSmall() {
                return small;
            }

            public void setSmall(String small) {
                this.small = small;
            }

            public String getMedium() {
                return medium;
            }

            public void setMedium(String medium) {
                this.medium = medium;
            }

            public String getLarge() {
                return large;
            }

            public void setLarge(String large) {
                this.large = large;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.small);
                dest.writeString(this.medium);
                dest.writeString(this.large);
            }

            public ProfileImageBean() {
            }

            protected ProfileImageBean(Parcel in) {
                this.small = in.readString();
                this.medium = in.readString();
                this.large = in.readString();
            }

            public static final Creator<ProfileImageBean> CREATOR = new Creator<ProfileImageBean>() {
                @Override
                public ProfileImageBean createFromParcel(Parcel source) {
                    return new ProfileImageBean(source);
                }

                @Override
                public ProfileImageBean[] newArray(int size) {
                    return new ProfileImageBean[size];
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
            dest.writeString(this.updated_at);
            dest.writeString(this.username);
            dest.writeString(this.name);
            dest.writeString(this.first_name);
            dest.writeString(this.last_name);
            dest.writeString(this.twitter_username);
            dest.writeString(this.portfolio_url);
            dest.writeString(this.bio);
            dest.writeString(this.location);
            dest.writeParcelable(this.links, flags);
            dest.writeParcelable(this.profile_image, flags);
            dest.writeString(this.instagram_username);
            dest.writeInt(this.total_collections);
            dest.writeInt(this.total_likes);
            dest.writeInt(this.total_photos);
            dest.writeByte(this.accepted_tos ? (byte) 1 : (byte) 0);
        }

        public UserBean() {
        }

        protected UserBean(Parcel in) {
            this.id = in.readString();
            this.updated_at = in.readString();
            this.username = in.readString();
            this.name = in.readString();
            this.first_name = in.readString();
            this.last_name = in.readString();
            this.twitter_username = in.readString();
            this.portfolio_url = in.readString();
            this.bio = in.readString();
            this.location = in.readString();
            this.links = in.readParcelable(LinksBeanX.class.getClassLoader());
            this.profile_image = in.readParcelable(ProfileImageBean.class.getClassLoader());
            this.instagram_username = in.readString();
            this.total_collections = in.readInt();
            this.total_likes = in.readInt();
            this.total_photos = in.readInt();
            this.accepted_tos = in.readByte() != 0;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.created_at);
        dest.writeString(this.updated_at);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeString(this.color);
        dest.writeString(this.description);
        dest.writeParcelable(this.urls, flags);
        dest.writeParcelable(this.links, flags);
        dest.writeByte(this.sponsored ? (byte) 1 : (byte) 0);
        dest.writeString(this.sponsored_by);
        dest.writeString(this.sponsored_impressions_id);
        dest.writeInt(this.likes);
        dest.writeByte(this.liked_by_user ? (byte) 1 : (byte) 0);
        dest.writeString(this.slug);
        dest.writeParcelable(this.user, flags);
        dest.writeStringList(this.categories);
        dest.writeStringList(this.current_user_collections);
    }

    public UnsplashPicDto() {
    }

    protected UnsplashPicDto(Parcel in) {
        this.id = in.readString();
        this.created_at = in.readString();
        this.updated_at = in.readString();
        this.width = in.readInt();
        this.height = in.readInt();
        this.color = in.readString();
        this.description = in.readString();
        this.urls = in.readParcelable(UrlsBean.class.getClassLoader());
        this.links = in.readParcelable(LinksBean.class.getClassLoader());
        this.sponsored = in.readByte() != 0;
        this.sponsored_by = in.readString();
        this.sponsored_impressions_id = in.readString();
        this.likes = in.readInt();
        this.liked_by_user = in.readByte() != 0;
        this.slug = in.readString();
        this.user = in.readParcelable(UserBean.class.getClassLoader());
        this.categories = in.createStringArrayList();
        this.current_user_collections = in.createStringArrayList();
    }

    public static final Creator<UnsplashPicDto> CREATOR = new Creator<UnsplashPicDto>() {
        @Override
        public UnsplashPicDto createFromParcel(Parcel source) {
            return new UnsplashPicDto(source);
        }

        @Override
        public UnsplashPicDto[] newArray(int size) {
            return new UnsplashPicDto[size];
        }
    };
}
