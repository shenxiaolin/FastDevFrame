package com.fast.dev.frame.sample.Bean;

/**
 * 说明：BannerBean
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/4/8 21:31
 * <p/>
 * 版本：verson 1.0
 */
public class BannerBean {

    private String picUrl;
    private String link;
    private String des;

    public BannerBean() {
    }

    public BannerBean(String picUrl, String link, String des) {
        this.picUrl = picUrl;
        this.link = link;
        this.des = des;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
