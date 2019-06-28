package com.heyongrui.module.data.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class KaiYanDataBean implements Parcelable {
    private String dataType;
    private int id;
    private String type;
    private String text;
    private String subTitle;
    private String actionUrl;
    private String adTrack;
    private AuthorBean.FollowBean follow;

    private int count;
    private String title;
    private String description;
    private String library;
    private String image;
    private boolean shade;
    private KaiYanLabelBean label;
    private KaiYanHeaderBean header;
    private KaiYanHeaderBean footer;
    private boolean autoPlay;
    private List<KaiYanLabelBean> labelList;
    private List<KaiYanItemBean> itemList;

    private KaiYanItemBean content;
    private String resourceType;
    private String slogan;
    private ConsumptionBean consumption;
    private ProviderBean provider;
    private String category;
    private AuthorBean author;
    private CoverBean cover;
    private String playUrl;
    private String thumbPlayUrl;
    private int duration;
    private WebUrlBean webUrl;
    private long releaseTime;
    private boolean ad;
    //    private Object campaign;
    //    private Object waterMarks;
    //    private Object titlePgc;
//    private Object descriptionPgc;
    private String remark;
    private boolean ifLimitVideo;
    private int searchWeight;
    private int idx;
    private String shareAdTrack;
    private String favoriteAdTrack;
    private String webAdTrack;
    private long date;
    private String promotion;
    private String descriptionEditor;
    private boolean collected;
    private boolean played;
    private String lastViewTime;
    //    private Object playlists;
    private String src;
    private List<TagsBean> tags;
    private List<PlayInfoBean> playInfo;
    private List<Object> subtitles;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    public String getAdTrack() {
        return adTrack;
    }

    public void setAdTrack(String adTrack) {
        this.adTrack = adTrack;
    }

    public AuthorBean.FollowBean getFollow() {
        return follow;
    }

    public void setFollow(AuthorBean.FollowBean follow) {
        this.follow = follow;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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

    public String getLibrary() {
        return library;
    }

    public void setLibrary(String library) {
        this.library = library;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isShade() {
        return shade;
    }

    public void setShade(boolean shade) {
        this.shade = shade;
    }

    public KaiYanLabelBean getLabel() {
        return label;
    }

    public void setLabel(KaiYanLabelBean label) {
        this.label = label;
    }

    public KaiYanHeaderBean getHeader() {
        return header;
    }

    public void setHeader(KaiYanHeaderBean header) {
        this.header = header;
    }

    public KaiYanHeaderBean getFooter() {
        return footer;
    }

    public void setFooter(KaiYanHeaderBean footer) {
        this.footer = footer;
    }

    public boolean isAutoPlay() {
        return autoPlay;
    }

    public void setAutoPlay(boolean autoPlay) {
        this.autoPlay = autoPlay;
    }

    public List<KaiYanLabelBean> getLabelList() {
        return labelList;
    }

    public void setLabelList(List<KaiYanLabelBean> labelList) {
        this.labelList = labelList;
    }

    public List<KaiYanItemBean> getItemList() {
        return itemList;
    }

    public void setItemList(List<KaiYanItemBean> itemList) {
        this.itemList = itemList;
    }

    public KaiYanItemBean getContent() {
        return content;
    }

    public void setContent(KaiYanItemBean content) {
        this.content = content;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public ConsumptionBean getConsumption() {
        return consumption;
    }

    public void setConsumption(ConsumptionBean consumption) {
        this.consumption = consumption;
    }

    public ProviderBean getProvider() {
        return provider;
    }

    public void setProvider(ProviderBean provider) {
        this.provider = provider;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public AuthorBean getAuthor() {
        return author;
    }

    public void setAuthor(AuthorBean author) {
        this.author = author;
    }

    public CoverBean getCover() {
        return cover;
    }

    public void setCover(CoverBean cover) {
        this.cover = cover;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public String getThumbPlayUrl() {
        return thumbPlayUrl;
    }

    public void setThumbPlayUrl(String thumbPlayUrl) {
        this.thumbPlayUrl = thumbPlayUrl;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public WebUrlBean getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(WebUrlBean webUrl) {
        this.webUrl = webUrl;
    }

    public long getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(long releaseTime) {
        this.releaseTime = releaseTime;
    }

    public boolean isAd() {
        return ad;
    }

    public void setAd(boolean ad) {
        this.ad = ad;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isIfLimitVideo() {
        return ifLimitVideo;
    }

    public void setIfLimitVideo(boolean ifLimitVideo) {
        this.ifLimitVideo = ifLimitVideo;
    }

    public int getSearchWeight() {
        return searchWeight;
    }

    public void setSearchWeight(int searchWeight) {
        this.searchWeight = searchWeight;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getShareAdTrack() {
        return shareAdTrack;
    }

    public void setShareAdTrack(String shareAdTrack) {
        this.shareAdTrack = shareAdTrack;
    }

    public String getFavoriteAdTrack() {
        return favoriteAdTrack;
    }

    public void setFavoriteAdTrack(String favoriteAdTrack) {
        this.favoriteAdTrack = favoriteAdTrack;
    }

    public String getWebAdTrack() {
        return webAdTrack;
    }

    public void setWebAdTrack(String webAdTrack) {
        this.webAdTrack = webAdTrack;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public String getDescriptionEditor() {
        return descriptionEditor;
    }

    public void setDescriptionEditor(String descriptionEditor) {
        this.descriptionEditor = descriptionEditor;
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    public boolean isPlayed() {
        return played;
    }

    public void setPlayed(boolean played) {
        this.played = played;
    }

    public String getLastViewTime() {
        return lastViewTime;
    }

    public void setLastViewTime(String lastViewTime) {
        this.lastViewTime = lastViewTime;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public List<TagsBean> getTags() {
        return tags;
    }

    public void setTags(List<TagsBean> tags) {
        this.tags = tags;
    }

    public List<PlayInfoBean> getPlayInfo() {
        return playInfo;
    }

    public void setPlayInfo(List<PlayInfoBean> playInfo) {
        this.playInfo = playInfo;
    }

    public List<Object> getSubtitles() {
        return subtitles;
    }

    public void setSubtitles(List<Object> subtitles) {
        this.subtitles = subtitles;
    }

    public static class ConsumptionBean implements Parcelable {
        private int collectionCount;
        private int shareCount;
        private int replyCount;

        public int getCollectionCount() {
            return collectionCount;
        }

        public void setCollectionCount(int collectionCount) {
            this.collectionCount = collectionCount;
        }

        public int getShareCount() {
            return shareCount;
        }

        public void setShareCount(int shareCount) {
            this.shareCount = shareCount;
        }

        public int getReplyCount() {
            return replyCount;
        }

        public void setReplyCount(int replyCount) {
            this.replyCount = replyCount;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.collectionCount);
            dest.writeInt(this.shareCount);
            dest.writeInt(this.replyCount);
        }

        public ConsumptionBean() {
        }

        protected ConsumptionBean(Parcel in) {
            this.collectionCount = in.readInt();
            this.shareCount = in.readInt();
            this.replyCount = in.readInt();
        }

        public static final Parcelable.Creator<ConsumptionBean> CREATOR = new Parcelable.Creator<ConsumptionBean>() {
            @Override
            public ConsumptionBean createFromParcel(Parcel source) {
                return new ConsumptionBean(source);
            }

            @Override
            public ConsumptionBean[] newArray(int size) {
                return new ConsumptionBean[size];
            }
        };
    }

    public static class ProviderBean implements Parcelable {
        private String name;
        private String alias;
        private String icon;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.name);
            dest.writeString(this.alias);
            dest.writeString(this.icon);
        }

        public ProviderBean() {
        }

        protected ProviderBean(Parcel in) {
            this.name = in.readString();
            this.alias = in.readString();
            this.icon = in.readString();
        }

        public static final Parcelable.Creator<ProviderBean> CREATOR = new Parcelable.Creator<ProviderBean>() {
            @Override
            public ProviderBean createFromParcel(Parcel source) {
                return new ProviderBean(source);
            }

            @Override
            public ProviderBean[] newArray(int size) {
                return new ProviderBean[size];
            }
        };
    }

    public static class AuthorBean implements Parcelable {
        private int id;
        private String icon;
        private String name;
        private String description;
        private String link;
        private long latestReleaseTime;
        private int videoNum;
        private String adTrack;
        private FollowBean follow;
        private ShieldBean shield;
        private int approvedNotReadyVideoCount;
        private boolean ifPgc;
        private int recSort;
        private boolean expert;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public long getLatestReleaseTime() {
            return latestReleaseTime;
        }

        public void setLatestReleaseTime(long latestReleaseTime) {
            this.latestReleaseTime = latestReleaseTime;
        }

        public int getVideoNum() {
            return videoNum;
        }

        public void setVideoNum(int videoNum) {
            this.videoNum = videoNum;
        }

        public String getAdTrack() {
            return adTrack;
        }

        public void setAdTrack(String adTrack) {
            this.adTrack = adTrack;
        }

        public FollowBean getFollow() {
            return follow;
        }

        public void setFollow(FollowBean follow) {
            this.follow = follow;
        }

        public ShieldBean getShield() {
            return shield;
        }

        public void setShield(ShieldBean shield) {
            this.shield = shield;
        }

        public int getApprovedNotReadyVideoCount() {
            return approvedNotReadyVideoCount;
        }

        public void setApprovedNotReadyVideoCount(int approvedNotReadyVideoCount) {
            this.approvedNotReadyVideoCount = approvedNotReadyVideoCount;
        }

        public boolean isIfPgc() {
            return ifPgc;
        }

        public void setIfPgc(boolean ifPgc) {
            this.ifPgc = ifPgc;
        }

        public int getRecSort() {
            return recSort;
        }

        public void setRecSort(int recSort) {
            this.recSort = recSort;
        }

        public boolean isExpert() {
            return expert;
        }

        public void setExpert(boolean expert) {
            this.expert = expert;
        }

        public static class FollowBean implements Parcelable {
            private String itemType;
            private int itemId;
            private boolean followed;

            public String getItemType() {
                return itemType;
            }

            public void setItemType(String itemType) {
                this.itemType = itemType;
            }

            public int getItemId() {
                return itemId;
            }

            public void setItemId(int itemId) {
                this.itemId = itemId;
            }

            public boolean isFollowed() {
                return followed;
            }

            public void setFollowed(boolean followed) {
                this.followed = followed;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.itemType);
                dest.writeInt(this.itemId);
                dest.writeByte(this.followed ? (byte) 1 : (byte) 0);
            }

            public FollowBean() {
            }

            protected FollowBean(Parcel in) {
                this.itemType = in.readString();
                this.itemId = in.readInt();
                this.followed = in.readByte() != 0;
            }

            public static final Parcelable.Creator<FollowBean> CREATOR = new Parcelable.Creator<FollowBean>() {
                @Override
                public FollowBean createFromParcel(Parcel source) {
                    return new FollowBean(source);
                }

                @Override
                public FollowBean[] newArray(int size) {
                    return new FollowBean[size];
                }
            };
        }

        public static class ShieldBean implements Parcelable {
            private String itemType;
            private int itemId;
            private boolean shielded;

            public String getItemType() {
                return itemType;
            }

            public void setItemType(String itemType) {
                this.itemType = itemType;
            }

            public int getItemId() {
                return itemId;
            }

            public void setItemId(int itemId) {
                this.itemId = itemId;
            }

            public boolean isShielded() {
                return shielded;
            }

            public void setShielded(boolean shielded) {
                this.shielded = shielded;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.itemType);
                dest.writeInt(this.itemId);
                dest.writeByte(this.shielded ? (byte) 1 : (byte) 0);
            }

            public ShieldBean() {
            }

            protected ShieldBean(Parcel in) {
                this.itemType = in.readString();
                this.itemId = in.readInt();
                this.shielded = in.readByte() != 0;
            }

            public static final Parcelable.Creator<ShieldBean> CREATOR = new Parcelable.Creator<ShieldBean>() {
                @Override
                public ShieldBean createFromParcel(Parcel source) {
                    return new ShieldBean(source);
                }

                @Override
                public ShieldBean[] newArray(int size) {
                    return new ShieldBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.icon);
            dest.writeString(this.name);
            dest.writeString(this.description);
            dest.writeString(this.link);
            dest.writeLong(this.latestReleaseTime);
            dest.writeInt(this.videoNum);
            dest.writeString(this.adTrack);
            dest.writeParcelable(this.follow, flags);
            dest.writeParcelable(this.shield, flags);
            dest.writeInt(this.approvedNotReadyVideoCount);
            dest.writeByte(this.ifPgc ? (byte) 1 : (byte) 0);
            dest.writeInt(this.recSort);
            dest.writeByte(this.expert ? (byte) 1 : (byte) 0);
        }

        public AuthorBean() {
        }

        protected AuthorBean(Parcel in) {
            this.id = in.readInt();
            this.icon = in.readString();
            this.name = in.readString();
            this.description = in.readString();
            this.link = in.readString();
            this.latestReleaseTime = in.readLong();
            this.videoNum = in.readInt();
            this.adTrack = in.readString();
            this.follow = in.readParcelable(FollowBean.class.getClassLoader());
            this.shield = in.readParcelable(ShieldBean.class.getClassLoader());
            this.approvedNotReadyVideoCount = in.readInt();
            this.ifPgc = in.readByte() != 0;
            this.recSort = in.readInt();
            this.expert = in.readByte() != 0;
        }

        public static final Parcelable.Creator<AuthorBean> CREATOR = new Parcelable.Creator<AuthorBean>() {
            @Override
            public AuthorBean createFromParcel(Parcel source) {
                return new AuthorBean(source);
            }

            @Override
            public AuthorBean[] newArray(int size) {
                return new AuthorBean[size];
            }
        };
    }

    public static class CoverBean implements Parcelable {
        private String feed;
        private String detail;
        private String blurred;
        private String sharing;
        private String homepage;

        public String getFeed() {
            return feed;
        }

        public void setFeed(String feed) {
            this.feed = feed;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getBlurred() {
            return blurred;
        }

        public void setBlurred(String blurred) {
            this.blurred = blurred;
        }

        public String getSharing() {
            return sharing;
        }

        public void setSharing(String sharing) {
            this.sharing = sharing;
        }

        public String getHomepage() {
            return homepage;
        }

        public void setHomepage(String homepage) {
            this.homepage = homepage;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.feed);
            dest.writeString(this.detail);
            dest.writeString(this.blurred);
            dest.writeString(this.sharing);
            dest.writeString(this.homepage);
        }

        public CoverBean() {
        }

        protected CoverBean(Parcel in) {
            this.feed = in.readString();
            this.detail = in.readString();
            this.blurred = in.readString();
            this.sharing = in.readString();
            this.homepage = in.readString();
        }

        public static final Parcelable.Creator<CoverBean> CREATOR = new Parcelable.Creator<CoverBean>() {
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

    public static class WebUrlBean implements Parcelable {
        private String raw;
        private String forWeibo;

        public String getRaw() {
            return raw;
        }

        public void setRaw(String raw) {
            this.raw = raw;
        }

        public String getForWeibo() {
            return forWeibo;
        }

        public void setForWeibo(String forWeibo) {
            this.forWeibo = forWeibo;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.raw);
            dest.writeString(this.forWeibo);
        }

        public WebUrlBean() {
        }

        protected WebUrlBean(Parcel in) {
            this.raw = in.readString();
            this.forWeibo = in.readString();
        }

        public static final Parcelable.Creator<WebUrlBean> CREATOR = new Parcelable.Creator<WebUrlBean>() {
            @Override
            public WebUrlBean createFromParcel(Parcel source) {
                return new WebUrlBean(source);
            }

            @Override
            public WebUrlBean[] newArray(int size) {
                return new WebUrlBean[size];
            }
        };
    }

    public static class TagsBean implements Parcelable {
        private int id;
        private String name;
        private String actionUrl;
        private String adTrack;
        private String desc;
        private String bgPicture;
        private String headerImage;
        private String tagRecType;
        private int communityIndex;
//        private Object childTagList;
//        private Object childTagIdList;

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

        public String getActionUrl() {
            return actionUrl;
        }

        public void setActionUrl(String actionUrl) {
            this.actionUrl = actionUrl;
        }

        public String getAdTrack() {
            return adTrack;
        }

        public void setAdTrack(String adTrack) {
            this.adTrack = adTrack;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getBgPicture() {
            return bgPicture;
        }

        public void setBgPicture(String bgPicture) {
            this.bgPicture = bgPicture;
        }

        public String getHeaderImage() {
            return headerImage;
        }

        public void setHeaderImage(String headerImage) {
            this.headerImage = headerImage;
        }

        public String getTagRecType() {
            return tagRecType;
        }

        public void setTagRecType(String tagRecType) {
            this.tagRecType = tagRecType;
        }

        public int getCommunityIndex() {
            return communityIndex;
        }

        public void setCommunityIndex(int communityIndex) {
            this.communityIndex = communityIndex;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.name);
            dest.writeString(this.actionUrl);
            dest.writeString(this.adTrack);
            dest.writeString(this.desc);
            dest.writeString(this.bgPicture);
            dest.writeString(this.headerImage);
            dest.writeString(this.tagRecType);
            dest.writeInt(this.communityIndex);
        }

        public TagsBean() {
        }

        protected TagsBean(Parcel in) {
            this.id = in.readInt();
            this.name = in.readString();
            this.actionUrl = in.readString();
            this.adTrack = in.readString();
            this.desc = in.readString();
            this.bgPicture = in.readString();
            this.headerImage = in.readString();
            this.tagRecType = in.readString();
            this.communityIndex = in.readInt();
        }

        public static final Parcelable.Creator<TagsBean> CREATOR = new Parcelable.Creator<TagsBean>() {
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

    public static class PlayInfoBean implements Parcelable {
        private int height;
        private int width;
        private String name;
        private String type;
        private String url;
        private List<UrlListBean> urlList;

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public List<UrlListBean> getUrlList() {
            return urlList;
        }

        public void setUrlList(List<UrlListBean> urlList) {
            this.urlList = urlList;
        }

        public static class UrlListBean implements Parcelable {
            private String name;
            private String url;
            private long size;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public long getSize() {
                return size;
            }

            public void setSize(long size) {
                this.size = size;
            }

            public UrlListBean() {
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.name);
                dest.writeString(this.url);
                dest.writeLong(this.size);
            }

            protected UrlListBean(Parcel in) {
                this.name = in.readString();
                this.url = in.readString();
                this.size = in.readLong();
            }

            public static final Creator<UrlListBean> CREATOR = new Creator<UrlListBean>() {
                @Override
                public UrlListBean createFromParcel(Parcel source) {
                    return new UrlListBean(source);
                }

                @Override
                public UrlListBean[] newArray(int size) {
                    return new UrlListBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.height);
            dest.writeInt(this.width);
            dest.writeString(this.name);
            dest.writeString(this.type);
            dest.writeString(this.url);
            dest.writeTypedList(this.urlList);
        }

        public PlayInfoBean() {
        }

        protected PlayInfoBean(Parcel in) {
            this.height = in.readInt();
            this.width = in.readInt();
            this.name = in.readString();
            this.type = in.readString();
            this.url = in.readString();
            this.urlList = in.createTypedArrayList(UrlListBean.CREATOR);
        }

        public static final Parcelable.Creator<PlayInfoBean> CREATOR = new Parcelable.Creator<PlayInfoBean>() {
            @Override
            public PlayInfoBean createFromParcel(Parcel source) {
                return new PlayInfoBean(source);
            }

            @Override
            public PlayInfoBean[] newArray(int size) {
                return new PlayInfoBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.dataType);
        dest.writeInt(this.id);
        dest.writeString(this.type);
        dest.writeString(this.text);
        dest.writeString(this.subTitle);
        dest.writeString(this.actionUrl);
        dest.writeString(this.adTrack);
        dest.writeParcelable(this.follow, flags);
        dest.writeInt(this.count);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.library);
        dest.writeString(this.image);
        dest.writeByte(this.shade ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.label, flags);
        dest.writeParcelable(this.header, flags);
        dest.writeParcelable(this.footer, flags);
        dest.writeByte(this.autoPlay ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.labelList);
        dest.writeTypedList(this.itemList);
        dest.writeParcelable(this.content, flags);
        dest.writeString(this.resourceType);
        dest.writeString(this.slogan);
        dest.writeParcelable(this.consumption, flags);
        dest.writeParcelable(this.provider, flags);
        dest.writeString(this.category);
        dest.writeParcelable(this.author, flags);
        dest.writeParcelable(this.cover, flags);
        dest.writeString(this.playUrl);
        dest.writeString(this.thumbPlayUrl);
        dest.writeInt(this.duration);
        dest.writeParcelable(this.webUrl, flags);
        dest.writeLong(this.releaseTime);
        dest.writeByte(this.ad ? (byte) 1 : (byte) 0);
        dest.writeString(this.remark);
        dest.writeByte(this.ifLimitVideo ? (byte) 1 : (byte) 0);
        dest.writeInt(this.searchWeight);
        dest.writeInt(this.idx);
        dest.writeString(this.shareAdTrack);
        dest.writeString(this.favoriteAdTrack);
        dest.writeString(this.webAdTrack);
        dest.writeLong(this.date);
        dest.writeString(this.promotion);
        dest.writeString(this.descriptionEditor);
        dest.writeByte(this.collected ? (byte) 1 : (byte) 0);
        dest.writeByte(this.played ? (byte) 1 : (byte) 0);
        dest.writeString(this.lastViewTime);
        dest.writeString(this.src);
        dest.writeTypedList(this.tags);
        dest.writeTypedList(this.playInfo);
        dest.writeList(this.subtitles);
    }

    public KaiYanDataBean() {
    }

    protected KaiYanDataBean(Parcel in) {
        this.dataType = in.readString();
        this.id = in.readInt();
        this.type = in.readString();
        this.text = in.readString();
        this.subTitle = in.readString();
        this.actionUrl = in.readString();
        this.adTrack = in.readString();
        this.follow = in.readParcelable(AuthorBean.FollowBean.class.getClassLoader());
        this.count = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.library = in.readString();
        this.image = in.readString();
        this.shade = in.readByte() != 0;
        this.label = in.readParcelable(KaiYanLabelBean.class.getClassLoader());
        this.header = in.readParcelable(KaiYanHeaderBean.class.getClassLoader());
        this.footer = in.readParcelable(KaiYanHeaderBean.class.getClassLoader());
        this.autoPlay = in.readByte() != 0;
        this.labelList = in.createTypedArrayList(KaiYanLabelBean.CREATOR);
        this.itemList = in.createTypedArrayList(KaiYanItemBean.CREATOR);
        this.content = in.readParcelable(KaiYanItemBean.class.getClassLoader());
        this.resourceType = in.readString();
        this.slogan = in.readString();
        this.consumption = in.readParcelable(ConsumptionBean.class.getClassLoader());
        this.provider = in.readParcelable(ProviderBean.class.getClassLoader());
        this.category = in.readString();
        this.author = in.readParcelable(AuthorBean.class.getClassLoader());
        this.cover = in.readParcelable(CoverBean.class.getClassLoader());
        this.playUrl = in.readString();
        this.thumbPlayUrl = in.readString();
        this.duration = in.readInt();
        this.webUrl = in.readParcelable(WebUrlBean.class.getClassLoader());
        this.releaseTime = in.readLong();
        this.ad = in.readByte() != 0;
        this.remark = in.readString();
        this.ifLimitVideo = in.readByte() != 0;
        this.searchWeight = in.readInt();
        this.idx = in.readInt();
        this.shareAdTrack = in.readString();
        this.favoriteAdTrack = in.readString();
        this.webAdTrack = in.readString();
        this.date = in.readLong();
        this.promotion = in.readString();
        this.descriptionEditor = in.readString();
        this.collected = in.readByte() != 0;
        this.played = in.readByte() != 0;
        this.lastViewTime = in.readString();
        this.src = in.readString();
        this.tags = in.createTypedArrayList(TagsBean.CREATOR);
        this.playInfo = in.createTypedArrayList(PlayInfoBean.CREATOR);
        this.subtitles = new ArrayList<Object>();
        in.readList(this.subtitles, Object.class.getClassLoader());
    }

    public static final Parcelable.Creator<KaiYanDataBean> CREATOR = new Parcelable.Creator<KaiYanDataBean>() {
        @Override
        public KaiYanDataBean createFromParcel(Parcel source) {
            return new KaiYanDataBean(source);
        }

        @Override
        public KaiYanDataBean[] newArray(int size) {
            return new KaiYanDataBean[size];
        }
    };
}
