package com.heyongrui.main.data.dto;

public class IdiomDto {
    /**
     * name : 狗急跳墙
     * pinyin : gǒu jí tiào qiáng
     * pretation : 狗急了也能跳过墙去。比喻坏人在走投无路时豁出去，不顾一切地捣乱。
     * sample : 今儿我听了他的短儿，人急造反，～”，不但生事，而且我还没趣。
     * sampleFrom : 清·曹雪芹《红楼梦》第二十二回
     * source : 《敦煌变文集·燕子赋》人急烧香，狗急蓦墙。”
     */

    private String name;
    private String pinyin;
    private String pretation;
    private String sample;
    private String sampleFrom;
    private String source;

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

    public String getPretation() {
        return pretation;
    }

    public void setPretation(String pretation) {
        this.pretation = pretation;
    }

    public String getSample() {
        return sample;
    }

    public void setSample(String sample) {
        this.sample = sample;
    }

    public String getSampleFrom() {
        return sampleFrom;
    }

    public void setSampleFrom(String sampleFrom) {
        this.sampleFrom = sampleFrom;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
