package com.heyongrui.module2.data.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by lambert on 2019/2/12.
 */

public class QingMangArticleListDto implements Parcelable {

    private boolean ok;
    private boolean hasMore;
    private String nextUrl;
    private boolean hasPreMore;
    private List<ArticlesBean> articles;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public String getNextUrl() {
        return nextUrl;
    }

    public void setNextUrl(String nextUrl) {
        this.nextUrl = nextUrl;
    }

    public boolean isHasPreMore() {
        return hasPreMore;
    }

    public void setHasPreMore(boolean hasPreMore) {
        this.hasPreMore = hasPreMore;
    }

    public List<ArticlesBean> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticlesBean> articles) {
        this.articles = articles;
    }

    public static class ArticlesBean implements Parcelable {

        private String articleId;
        private String title;
        private String snippet;
        private String author;
        private String webUrl;
        private String contentUrl;
        private long publishTimestamp;
        private long crawlerTimestamp;
        private String providerName;
        private String providerIcon;
        private String providerPackageName;
        private String templateType;
        private List<CoverBean> covers;
        private List<CoverBean> images;
        private List<VideosBean> videos;
        private List<MusicsBean> musics;
        private List<CategoriesBean> categories;
        private List<TagsBean> tags;

        public String getArticleId() {
            return articleId;
        }

        public void setArticleId(String articleId) {
            this.articleId = articleId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSnippet() {
            return snippet;
        }

        public void setSnippet(String snippet) {
            this.snippet = snippet;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getWebUrl() {
            return webUrl;
        }

        public void setWebUrl(String webUrl) {
            this.webUrl = webUrl;
        }

        public String getContentUrl() {
            return contentUrl;
        }

        public void setContentUrl(String contentUrl) {
            this.contentUrl = contentUrl;
        }

        public long getPublishTimestamp() {
            return publishTimestamp;
        }

        public void setPublishTimestamp(long publishTimestamp) {
            this.publishTimestamp = publishTimestamp;
        }

        public long getCrawlerTimestamp() {
            return crawlerTimestamp;
        }

        public void setCrawlerTimestamp(long crawlerTimestamp) {
            this.crawlerTimestamp = crawlerTimestamp;
        }

        public String getProviderName() {
            return providerName;
        }

        public void setProviderName(String providerName) {
            this.providerName = providerName;
        }

        public String getProviderIcon() {
            return providerIcon;
        }

        public void setProviderIcon(String providerIcon) {
            this.providerIcon = providerIcon;
        }

        public String getProviderPackageName() {
            return providerPackageName;
        }

        public void setProviderPackageName(String providerPackageName) {
            this.providerPackageName = providerPackageName;
        }

        public String getTemplateType() {
            return templateType;
        }

        public void setTemplateType(String templateType) {
            this.templateType = templateType;
        }

        public List<CoverBean> getCovers() {
            return covers;
        }

        public void setCovers(List<CoverBean> covers) {
            this.covers = covers;
        }

        public List<CoverBean> getImages() {
            return images;
        }

        public void setImages(List<CoverBean> images) {
            this.images = images;
        }

        public List<VideosBean> getVideos() {
            return videos;
        }

        public void setVideos(List<VideosBean> videos) {
            this.videos = videos;
        }

        public List<MusicsBean> getMusics() {
            return musics;
        }

        public void setMusics(List<MusicsBean> musics) {
            this.musics = musics;
        }

        public List<CategoriesBean> getCategories() {
            return categories;
        }

        public void setCategories(List<CategoriesBean> categories) {
            this.categories = categories;
        }

        public List<TagsBean> getTags() {
            return tags;
        }

        public void setTags(List<TagsBean> tags) {
            this.tags = tags;
        }

        public static class CoverBean implements Parcelable {

            private String url;
            private String originalUrl;
            private int height;
            private int width;
            private String storageKey;
            private int rgb;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getOriginalUrl() {
                return originalUrl;
            }

            public void setOriginalUrl(String originalUrl) {
                this.originalUrl = originalUrl;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public String getStorageKey() {
                return storageKey;
            }

            public void setStorageKey(String storageKey) {
                this.storageKey = storageKey;
            }

            public int getRgb() {
                return rgb;
            }

            public void setRgb(int rgb) {
                this.rgb = rgb;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.url);
                dest.writeString(this.originalUrl);
                dest.writeInt(this.height);
                dest.writeInt(this.width);
                dest.writeString(this.storageKey);
                dest.writeInt(this.rgb);
            }

            public CoverBean() {
            }

            protected CoverBean(Parcel in) {
                this.url = in.readString();
                this.originalUrl = in.readString();
                this.height = in.readInt();
                this.width = in.readInt();
                this.storageKey = in.readString();
                this.rgb = in.readInt();
            }

            public static final Creator<CoverBean> CREATOR = new Creator<CoverBean>() {
                @Override
                public CoverBean createFromParcel(Parcel source) {
                    return new CoverBean(source);
                }

                @Override
                public CoverBean[] newArray(int size) {
                    return new CoverBean[size];
                }
            };
        }

        public static class MusicsBean implements Parcelable {
            private String url;
            private String name;
            private CoverBean cover;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public CoverBean getCover() {
                return cover;
            }

            public void setCover(CoverBean cover) {
                this.cover = cover;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.url);
                dest.writeString(this.name);
                dest.writeParcelable(this.cover, flags);
            }

            public MusicsBean() {
            }

            protected MusicsBean(Parcel in) {
                this.url = in.readString();
                this.name = in.readString();
                this.cover = in.readParcelable(CoverBean.class.getClassLoader());
            }

            public static final Creator<MusicsBean> CREATOR = new Creator<MusicsBean>() {
                @Override
                public MusicsBean createFromParcel(Parcel source) {
                    return new MusicsBean(source);
                }

                @Override
                public MusicsBean[] newArray(int size) {
                    return new MusicsBean[size];
                }
            };
        }

        public static class VideosBean implements Parcelable {

            private String url;
            private CoverBean cover;
            private double duration;
            private int width;
            private int height;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public CoverBean getCover() {
                return cover;
            }

            public void setCover(CoverBean cover) {
                this.cover = cover;
            }

            public double getDuration() {
                return duration;
            }

            public void setDuration(double duration) {
                this.duration = duration;
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
                dest.writeString(this.url);
                dest.writeParcelable(this.cover, flags);
                dest.writeDouble(this.duration);
                dest.writeInt(this.width);
                dest.writeInt(this.height);
            }

            public VideosBean() {
            }

            protected VideosBean(Parcel in) {
                this.url = in.readString();
                this.cover = in.readParcelable(CoverBean.class.getClassLoader());
                this.duration = in.readDouble();
                this.width = in.readInt();
                this.height = in.readInt();
            }

            public static final Creator<VideosBean> CREATOR = new Creator<VideosBean>() {
                @Override
                public VideosBean createFromParcel(Parcel source) {
                    return new VideosBean(source);
                }

                @Override
                public VideosBean[] newArray(int size) {
                    return new VideosBean[size];
                }
            };
        }

        public static class CategoriesBean implements Parcelable {

            private String categoryId;
            private String name;

            public String getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(String categoryId) {
                this.categoryId = categoryId;
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
                dest.writeString(this.categoryId);
                dest.writeString(this.name);
            }

            public CategoriesBean() {
            }

            protected CategoriesBean(Parcel in) {
                this.categoryId = in.readString();
                this.name = in.readString();
            }

            public static final Creator<CategoriesBean> CREATOR = new Creator<CategoriesBean>() {
                @Override
                public CategoriesBean createFromParcel(Parcel source) {
                    return new CategoriesBean(source);
                }

                @Override
                public CategoriesBean[] newArray(int size) {
                    return new CategoriesBean[size];
                }
            };
        }

        public static class TagsBean implements Parcelable {
            private String name;

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
                dest.writeString(this.name);
            }

            public TagsBean() {
            }

            protected TagsBean(Parcel in) {
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

        public ArticlesBean() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.articleId);
            dest.writeString(this.title);
            dest.writeString(this.snippet);
            dest.writeString(this.author);
            dest.writeString(this.webUrl);
            dest.writeString(this.contentUrl);
            dest.writeLong(this.publishTimestamp);
            dest.writeLong(this.crawlerTimestamp);
            dest.writeString(this.providerName);
            dest.writeString(this.providerIcon);
            dest.writeString(this.providerPackageName);
            dest.writeString(this.templateType);
            dest.writeTypedList(this.covers);
            dest.writeTypedList(this.images);
            dest.writeTypedList(this.videos);
            dest.writeTypedList(this.musics);
            dest.writeTypedList(this.categories);
            dest.writeTypedList(this.tags);
        }

        protected ArticlesBean(Parcel in) {
            this.articleId = in.readString();
            this.title = in.readString();
            this.snippet = in.readString();
            this.author = in.readString();
            this.webUrl = in.readString();
            this.contentUrl = in.readString();
            this.publishTimestamp = in.readLong();
            this.crawlerTimestamp = in.readLong();
            this.providerName = in.readString();
            this.providerIcon = in.readString();
            this.providerPackageName = in.readString();
            this.templateType = in.readString();
            this.covers = in.createTypedArrayList(CoverBean.CREATOR);
            this.images = in.createTypedArrayList(CoverBean.CREATOR);
            this.videos = in.createTypedArrayList(VideosBean.CREATOR);
            this.musics = in.createTypedArrayList(MusicsBean.CREATOR);
            this.categories = in.createTypedArrayList(CategoriesBean.CREATOR);
            this.tags = in.createTypedArrayList(TagsBean.CREATOR);
        }

        public static final Creator<ArticlesBean> CREATOR = new Creator<ArticlesBean>() {
            @Override
            public ArticlesBean createFromParcel(Parcel source) {
                return new ArticlesBean(source);
            }

            @Override
            public ArticlesBean[] newArray(int size) {
                return new ArticlesBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.ok ? (byte) 1 : (byte) 0);
        dest.writeByte(this.hasMore ? (byte) 1 : (byte) 0);
        dest.writeString(this.nextUrl);
        dest.writeByte(this.hasPreMore ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.articles);
    }

    public QingMangArticleListDto() {
    }

    protected QingMangArticleListDto(Parcel in) {
        this.ok = in.readByte() != 0;
        this.hasMore = in.readByte() != 0;
        this.nextUrl = in.readString();
        this.hasPreMore = in.readByte() != 0;
        this.articles = in.createTypedArrayList(ArticlesBean.CREATOR);
    }

    public static final Creator<QingMangArticleListDto> CREATOR = new Creator<QingMangArticleListDto>() {
        @Override
        public QingMangArticleListDto createFromParcel(Parcel source) {
            return new QingMangArticleListDto(source);
        }

        @Override
        public QingMangArticleListDto[] newArray(int size) {
            return new QingMangArticleListDto[size];
        }
    };
}
