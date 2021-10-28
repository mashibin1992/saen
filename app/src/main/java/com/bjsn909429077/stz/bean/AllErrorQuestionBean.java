package com.bjsn909429077.stz.bean;

import com.chad.library.adapter.base.entity.node.BaseNode;

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/18 20:36
 **/
public class AllErrorQuestionBean {

    private int code;
    private List<DataBean> data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean extends BaseNode {
        private String chapterId;
        private String chapterName;
        private String completeNumber;
        private String countNumber;
        private String errorNumber;
        private List<NodeListBean> nodeList;

        public String getChapterId() {
            return chapterId;
        }

        public void setChapterId(String chapterId) {
            this.chapterId = chapterId;
        }

        public String getChapterName() {
            return chapterName;
        }

        public void setChapterName(String chapterName) {
            this.chapterName = chapterName;
        }

        public String getCompleteNumber() {
            return completeNumber;
        }

        public void setCompleteNumber(String completeNumber) {
            this.completeNumber = completeNumber;
        }

        public String getCountNumber() {
            return countNumber;
        }

        public void setCountNumber(String countNumber) {
            this.countNumber = countNumber;
        }

        public String getErrorNumber() {
            return errorNumber;
        }

        public void setErrorNumber(String errorNumber) {
            this.errorNumber = errorNumber;
        }

        public List<NodeListBean> getNodeList() {
            return nodeList;
        }

        public void setNodeList(List<NodeListBean> nodeList) {
            this.nodeList = nodeList;
        }

        @Nullable
        @Override
        public List<BaseNode> getChildNode() {
            return null;
        }

        public static class NodeListBean extends BaseNode{
            private String completeNumber;
            private String countNumber;
            private String errorNumber;
            private String nodeId;
            private String nodeName;
            private String parentId;

            public String getCompleteNumber() {
                return completeNumber;
            }

            public void setCompleteNumber(String completeNumber) {
                this.completeNumber = completeNumber;
            }

            public String getCountNumber() {
                return countNumber;
            }

            public void setCountNumber(String countNumber) {
                this.countNumber = countNumber;
            }

            public String getErrorNumber() {
                return errorNumber;
            }

            public void setErrorNumber(String errorNumber) {
                this.errorNumber = errorNumber;
            }

            public String getNodeId() {
                return nodeId;
            }

            public void setNodeId(String nodeId) {
                this.nodeId = nodeId;
            }

            public String getNodeName() {
                return nodeName;
            }

            public void setNodeName(String nodeName) {
                this.nodeName = nodeName;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }

            @Nullable
            @Override
            public List<BaseNode> getChildNode() {
                return null;
            }
        }
    }
}
