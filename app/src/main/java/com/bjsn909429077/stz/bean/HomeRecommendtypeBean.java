package com.bjsn909429077.stz.bean;

import java.util.List;

public class HomeRecommendtypeBean {


    /**
     * code : 0
     * msg : ok
     * data : [{"wdId":1,"type":1,"questionName":"公司注销，账上多余的余额怎么处理？","answerContent":"这个目前最好的处理方法就是把个税交到国税后，在进行分类处理。","answerPics":["http://xiaoxiong-yrj-public.oss-cn-beijing.aliyuncs.com/web/AhREpY8BEN1630633530498.jpg"]},{"wdId":5,"type":1,"questionName":"睡了吗","answerContent":"没","answerPics":["http://xiaoxiong-yrj-public.oss-cn-beijing.aliyuncs.com/web/AhREpY8BEN1630633530498.jpg"]},{"wdId":6,"type":2,"questionName":"九月份戴尔G15值得购买吗","answerContent":"不值得购买，现在游戏本溢价太厉害，等待降价再购买","answerPics":[]}]
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

    public static class DataBean {
        /**
         * wdId : 1
         * type : 1
         * questionName : 公司注销，账上多余的余额怎么处理？
         * answerContent : 这个目前最好的处理方法就是把个税交到国税后，在进行分类处理。
         * answerPics : ["http://xiaoxiong-yrj-public.oss-cn-beijing.aliyuncs.com/web/AhREpY8BEN1630633530498.jpg"]
         */

        private int wdId;
        private int type;
        private String questionName;
        private String answerContent;
        private List<String> answerPics;

        public int getWdId() {
            return wdId;
        }

        public void setWdId(int wdId) {
            this.wdId = wdId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getQuestionName() {
            return questionName;
        }

        public void setQuestionName(String questionName) {
            this.questionName = questionName;
        }

        public String getAnswerContent() {
            return answerContent;
        }

        public void setAnswerContent(String answerContent) {
            this.answerContent = answerContent;
        }

        public List<String> getAnswerPics() {
            return answerPics;
        }

        public void setAnswerPics(List<String> answerPics) {
            this.answerPics = answerPics;
        }
    }
}
