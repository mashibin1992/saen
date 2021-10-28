package com.bjsn909429077.stz.bean;

import java.util.List;

public class VipBean {


    /**
     * code : 0
     * msg : ok
     * data : [{"memberItemId":1,"price":50},{"memberItemId":2,"price":118},{"memberItemId":3,"price":448}]
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
         * memberItemId : 1
         * price : 50
         */

        private int memberItemId;
        private int price;
        private boolean isSelect=false;

        public DataBean(int memberItemId, boolean isSelect) {
            this.memberItemId = memberItemId;
            this.isSelect = isSelect;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }
        public int getMemberItemId() {
            return memberItemId;
        }

        public void setMemberItemId(int memberItemId) {
            this.memberItemId = memberItemId;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }
}
