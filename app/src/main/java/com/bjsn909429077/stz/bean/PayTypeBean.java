package com.bjsn909429077.stz.bean;

public class PayTypeBean {

    public PayTypeBean(String type, boolean isSelect,int payTypeSelect) {
        this.type = type;
        this.isSelect = isSelect;
        this.payTypeSelect = payTypeSelect;
    }

    public  String type;

        public boolean isSelect=false;
    public int payTypeSelect=0;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getPayTypeSelect() {
        return payTypeSelect;
    }

    public void setPayTypeSelect(int payTypeSelect) {
        this.payTypeSelect = payTypeSelect;
    }
}
