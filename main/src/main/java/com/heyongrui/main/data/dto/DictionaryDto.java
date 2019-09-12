package com.heyongrui.main.data.dto;

public class DictionaryDto {
    /**
     * bihua : 30
     * bihuaWithBushou : 26
     * brief : 爨；cuàn；烧火做饭：分居各爨。；灶：“客传萧寒爨不烟。”<br
     * bushou : 火
     * detail : 爨；cuàn；【动】；烧火做饭〖cook〗；爨,炊也。——《广雅》；取其进火谓之爨,取其气上谓之
     * name : 爨
     * pinyin : cuàn
     * wubi : wfmo
     */

    private int bihua;
    private int bihuaWithBushou;
    private String brief;
    private String bushou;
    private String detail;
    private String name;
    private String pinyin;
    private String wubi;

    public int getBihua() {
        return bihua;
    }

    public void setBihua(int bihua) {
        this.bihua = bihua;
    }

    public int getBihuaWithBushou() {
        return bihuaWithBushou;
    }

    public void setBihuaWithBushou(int bihuaWithBushou) {
        this.bihuaWithBushou = bihuaWithBushou;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getBushou() {
        return bushou;
    }

    public void setBushou(String bushou) {
        this.bushou = bushou;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getWubi() {
        return wubi;
    }

    public void setWubi(String wubi) {
        this.wubi = wubi;
    }
}
