package com.fast.dev.frame.sample.Bean;

/**
 * 说明：MainBean
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/4/6 17:26
 * <p/>
 * 版本：verson 1.0
 */
public class MainBean {


    private String url;
    private String des;

    public MainBean(String url, String des) {
        this.url = url;
        this.des = des;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

}
