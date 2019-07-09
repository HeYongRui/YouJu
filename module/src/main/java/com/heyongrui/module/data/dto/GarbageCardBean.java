package com.heyongrui.module.data.dto;

public class GarbageCardBean {
    private int category;//1-可回收垃圾 2-干垃圾 3-湿垃圾 4-有害垃圾
    private String iconUrl;
    private String conceptTitle;
    private String conceptContent;
    private String includeTitle;
    private String includeContent;
    private String standardTitle;
    private String standardContent;

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getConceptTitle() {
        return conceptTitle;
    }

    public void setConceptTitle(String conceptTitle) {
        this.conceptTitle = conceptTitle;
    }

    public String getConceptContent() {
        return conceptContent;
    }

    public void setConceptContent(String conceptContent) {
        this.conceptContent = conceptContent;
    }

    public String getIncludeTitle() {
        return includeTitle;
    }

    public void setIncludeTitle(String includeTitle) {
        this.includeTitle = includeTitle;
    }

    public String getIncludeContent() {
        return includeContent;
    }

    public void setIncludeContent(String includeContent) {
        this.includeContent = includeContent;
    }

    public String getStandardTitle() {
        return standardTitle;
    }

    public void setStandardTitle(String standardTitle) {
        this.standardTitle = standardTitle;
    }

    public String getStandardContent() {
        return standardContent;
    }

    public void setStandardContent(String standardContent) {
        this.standardContent = standardContent;
    }
}
