package com.bjsn909429077.stz.bean;

public class DetailBean {

    public boolean empty;
    public ModelBean model;
    public ModelMapBean modelMap;
    public boolean reference;
    public String status;
    public ViewBean view;
    public String viewName;

    public static class ModelBean {
    }

    public static class ModelMapBean {
    }

    public static class ViewBean {
        public String contentType;

        @Override
        public String toString() {
            return "ViewBean{" +
                    "contentType='" + contentType + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "DetailBean{" +
                "empty=" + empty +
                ", model=" + model +
                ", modelMap=" + modelMap +
                ", reference=" + reference +
                ", status='" + status + '\'' +
                ", view=" + view +
                ", viewName='" + viewName + '\'' +
                '}';
    }
}
