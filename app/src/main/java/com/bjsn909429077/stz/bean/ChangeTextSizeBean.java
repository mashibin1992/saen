package com.bjsn909429077.stz.bean;

import com.bjsn909429077.stz.widget.ChangeTextSizeView;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/14 21:38
 **/
public class ChangeTextSizeBean {
    private ChangeTextSizeView changeTextSizeView;
    private int size;

    public ChangeTextSizeBean(ChangeTextSizeView changeTextSizeView, int size) {
        this.changeTextSizeView = changeTextSizeView;
        this.size = size;
    }

    public ChangeTextSizeView getChangeTextSizeView() {
        return changeTextSizeView;
    }

    public void setChangeTextSizeView(ChangeTextSizeView changeTextSizeView) {
        this.changeTextSizeView = changeTextSizeView;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
