package com.heyongrui.module.data.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class KaiYanHeaderBean implements Parcelable {
    private int id;
    private String title;
    private String font;
    private String subTitle;
    private String subTitleFont;
    private String textAlign;
    private String cover;
    private String label;
    private String actionUrl;
    private List<Object> labelList;
    private String rightText;
    private String icon;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getSubTitleFont() {
        return subTitleFont;
    }

    public void setSubTitleFont(String subTitleFont) {
        this.subTitleFont = subTitleFont;
    }

    public String getTextAlign() {
        return textAlign;
    }

    public void setTextAlign(String textAlign) {
        this.textAlign = textAlign;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    public List<Object> getLabelList() {
        return labelList;
    }

    public void setLabelList(List<Object> labelList) {
        this.labelList = labelList;
    }

    public String getRightText() {
        return rightText;
    }

    public void setRightText(String rightText) {
        this.rightText = rightText;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.font);
        dest.writeString(this.subTitle);
        dest.writeString(this.subTitleFont);
        dest.writeString(this.textAlign);
        dest.writeString(this.cover);
        dest.writeString(this.label);
        dest.writeString(this.actionUrl);
        dest.writeList(this.labelList);
        dest.writeString(this.rightText);
        dest.writeString(this.icon);
        dest.writeString(this.description);
    }

    public KaiYanHeaderBean() {
    }

    protected KaiYanHeaderBean(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.font = in.readString();
        this.subTitle = in.readString();
        this.subTitleFont = in.readString();
        this.textAlign = in.readString();
        this.cover = in.readString();
        this.label = in.readString();
        this.actionUrl = in.readString();
        this.labelList = new ArrayList<Object>();
        in.readList(this.labelList, Object.class.getClassLoader());
        this.rightText = in.readString();
        this.icon = in.readString();
        this.description = in.readString();
    }

    public static final Creator<KaiYanHeaderBean> CREATOR = new Creator<KaiYanHeaderBean>() {
        @Override
        public KaiYanHeaderBean createFromParcel(Parcel source) {
            return new KaiYanHeaderBean(source);
        }

        @Override
        public KaiYanHeaderBean[] newArray(int size) {
            return new KaiYanHeaderBean[size];
        }
    };
}
