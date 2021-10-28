package com.bjsn909429077.stz.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NoteListBean {


    /**
     * code : 0
     * data : [{"classHourId":0,"content":"","id":0,"intime":""}]
     * msg :
     */

    public Integer code;
    public List<DataBean> data;
    public String msg;

    public static class DataBean {
        /**
         * 共用
         */
        public Integer id;
        public String name;
        /**
         * 笔记
         * classHourId : 0
         * content :
         * id : 0
         * intime :
         */

        public Integer classHourId;
        public String content;
        public String intime;
    }

}
