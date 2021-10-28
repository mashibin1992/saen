package com.bjsn909429077.stz.bean;

import java.util.List;

public class PackageListBean {

    /**
     * code : 0
     * data : [{"coverPath":"","firstTypeId":0,"id":0,"packageName":"","secondTypeId":0}]
     * msg :
     */

    public Integer code;
    public List<DataBean> data;
    public String msg;

    public static class DataBean {
        /**
         * coverPath :
         * firstTypeId : 0
         * id : 0
         * packageName :
         * secondTypeId : 0
         */

        public String coverPath;
        public Integer firstTypeId;
        public Integer id;
        public String packageName;
        public Integer secondTypeId;
    }
}
