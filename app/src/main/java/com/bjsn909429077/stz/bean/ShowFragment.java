package com.bjsn909429077.stz.bean;

public class ShowFragment {

    private int position;

    public static final int HOME_FRAGMENT = 0;//首页
    public static final int PURCHASED_COURSE = 1;//选课
    public static final int SORT_CENTER = 2;//学习
    public static final int QUESTION = 3;//题库
    public static final int MINE = 4;//我的

    public ShowFragment(int position) {
        this.position = position;
    }

    public int getTYPE() {
        return position;
    }

    public void setTYPE(int position) {
        this.position = position;
    }

}
