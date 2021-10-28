package com.bjsn909429077.stz.bean;

import java.io.Serializable;
import java.util.List;

public class WenDaZhuanJiaBean {


    /**
     * code : 0
     * msg : ok
     * data : [{"expertId":10,"expertName":"王军","introduction":" 萨恩教育教学专家老师，拥有多年的教学经验 萨恩教育教学专家老师，拥有多年的教学经验 萨恩教育教学专家老师，拥有多年的教学经验 萨恩教育教学专家老师，拥有多年的教学经验 萨恩教育教学专家老师，拥有多年的教学经验\u2026","imagePath":"http://xiaoxiong-yrj-public.oss-cn-beijing.aliyuncs.com/web/s4cQ53P7cf1632415439348.png","headerPath":"http://xiaoxiong-yrj-public.oss-cn-beijing.aliyuncs.com/web/3kHahBYZfA1632405926208.png","expertiseAreaNameList":["电脑网络","医疗健康"]},{"expertId":11,"expertName":"王秀英","introduction":" 萨恩教育教学专家老师，拥有多年的教学经验 萨恩教育教学专家老师，拥有多年的教学经验 萨恩教育教学专家老师，拥有多年的教学经验 萨恩教育教学专家老师，拥有多年的教学经验 萨恩教育教学专家老师，拥有多年的教学经验\u2026","imagePath":"http://xiaoxiong-yrj-public.oss-cn-beijing.aliyuncs.com/web/s4cQ53P7cf1632415439348.png","headerPath":"http://xiaoxiong-yrj-public.oss-cn-beijing.aliyuncs.com/web/3kHahBYZfA1632405926208.png","expertiseAreaNameList":["医疗健康","体育运动"]},{"expertId":12,"expertName":"韩勇","introduction":" 萨恩教育教学专家老师，拥有多年的教学经验 萨恩教育教学专家老师，拥有多年的教学经验 萨恩教育教学专家老师，拥有多年的教学经验 萨恩教育教学专家老师，拥有多年的教学经验 萨恩教育教学专家老师，拥有多年的教学经验\u2026","imagePath":"http://xiaoxiong-yrj-public.oss-cn-beijing.aliyuncs.com/web/s4cQ53P7cf1632415439348.png","headerPath":"http://xiaoxiong-yrj-public.oss-cn-beijing.aliyuncs.com/web/3kHahBYZfA1632405926208.png","expertiseAreaNameList":["体育运动","电子数码"]},{"expertId":13,"expertName":"夏西洋","introduction":" 萨恩教育教学专家老师，拥有多年的教学经验 萨恩教育教学专家老师，拥有多年的教学经验 萨恩教育教学专家老师，拥有多年的教学经验 萨恩教育教学专家老师，拥有多年的教学经验 萨恩教育教学专家老师，拥有多年的教学经验\u2026","imagePath":"http://xiaoxiong-yrj-public.oss-cn-beijing.aliyuncs.com/web/s4cQ53P7cf1632415439348.png","headerPath":"http://xiaoxiong-yrj-public.oss-cn-beijing.aliyuncs.com/web/3kHahBYZfA1632405926208.png","expertiseAreaNameList":["商业理财"]}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * expertId : 10
         * expertName : 王军
         * introduction :  萨恩教育教学专家老师，拥有多年的教学经验 萨恩教育教学专家老师，拥有多年的教学经验 萨恩教育教学专家老师，拥有多年的教学经验 萨恩教育教学专家老师，拥有多年的教学经验 萨恩教育教学专家老师，拥有多年的教学经验…
         * imagePath : http://xiaoxiong-yrj-public.oss-cn-beijing.aliyuncs.com/web/s4cQ53P7cf1632415439348.png
         * headerPath : http://xiaoxiong-yrj-public.oss-cn-beijing.aliyuncs.com/web/3kHahBYZfA1632405926208.png
         * expertiseAreaNameList : ["电脑网络","医疗健康"]
         */

        private int expertId;
        private String expertName;
        private String introduction;
        private String imagePath;
        private String headerPath;
        private List<String> expertiseAreaNameList;

        public int getExpertId() {
            return expertId;
        }

        public void setExpertId(int expertId) {
            this.expertId = expertId;
        }

        public String getExpertName() {
            return expertName;
        }

        public void setExpertName(String expertName) {
            this.expertName = expertName;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        public String getHeaderPath() {
            return headerPath;
        }

        public void setHeaderPath(String headerPath) {
            this.headerPath = headerPath;
        }

        public List<String> getExpertiseAreaNameList() {
            return expertiseAreaNameList;
        }

        public void setExpertiseAreaNameList(List<String> expertiseAreaNameList) {
            this.expertiseAreaNameList = expertiseAreaNameList;
        }
    }
}
