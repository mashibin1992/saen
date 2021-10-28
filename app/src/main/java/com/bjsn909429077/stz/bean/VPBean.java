package com.bjsn909429077.stz.bean;

public class VPBean {
    //0 添加 1图片 2视频
    private int type;
    private String url;

    public VPBean(int type, String url) {
        this.type = type;
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public VPBean setType(int type) {
        this.type = type;
        return this;
    }

    public String getUrl() {
        return url == null ? "" : url;
    }

    public VPBean setUrl(String url) {
        this.url = url;
        return this;
    }
}
