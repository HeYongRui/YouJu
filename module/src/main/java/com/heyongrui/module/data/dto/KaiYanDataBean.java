package com.heyongrui.module.data.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class KaiYanDataBean implements Parcelable {
    //公共返回参数
    private String dataType;
    private int id;
    private String type;
    private String text;
    private String subTitle;
    private String actionUrl;
    private String adTrack;
    private String follow;
    //发现返回参数
    private int count;
    private String title;
    private String description;
    private String image;
    private boolean shade;
    private KaiYanLabelBean label;
    private KaiYanHeaderBean header;
    private boolean autoPlay;
    private List<Object> labelList;
    private List<KaiYanItemBean> itemList;

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

    public String getFollow() {
        return follow;
    }

    public void setFollow(String follow) {
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

    public boolean isAutoPlay() {
        return autoPlay;
    }

    public void setAutoPlay(boolean autoPlay) {
        this.autoPlay = autoPlay;
    }

    public List<Object> getLabelList() {
        return labelList;
    }

    public void setLabelList(List<Object> labelList) {
        this.labelList = labelList;
    }

    public List<KaiYanItemBean> getItemList() {
        return itemList;
    }

    public void setItemList(List<KaiYanItemBean> itemList) {
        this.itemList = itemList;
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
        dest.writeString(this.follow);
        dest.writeInt(this.count);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.image);
        dest.writeByte(this.shade ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.label, flags);
        dest.writeParcelable(this.header, flags);
        dest.writeByte(this.autoPlay ? (byte) 1 : (byte) 0);
        dest.writeList(this.labelList);
        dest.writeList(this.itemList);
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
        this.follow = in.readString();
        this.count = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.image = in.readString();
        this.shade = in.readByte() != 0;
        this.label = in.readParcelable(KaiYanLabelBean.class.getClassLoader());
        this.header = in.readParcelable(KaiYanHeaderBean.class.getClassLoader());
        this.autoPlay = in.readByte() != 0;
        this.labelList = new ArrayList<Object>();
        in.readList(this.labelList, Object.class.getClassLoader());
        this.itemList = new ArrayList<KaiYanItemBean>();
        in.readList(this.itemList, KaiYanItemBean.class.getClassLoader());
    }

    public static final Creator<KaiYanDataBean> CREATOR = new Creator<KaiYanDataBean>() {
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
