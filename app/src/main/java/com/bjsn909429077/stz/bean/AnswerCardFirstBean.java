package com.bjsn909429077.stz.bean;

import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.entity.node.NodeFooterImp;

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/9 18:30
 **/
public class AnswerCardFirstBean extends BaseExpandNode implements NodeFooterImp {

    private List<BaseNode> childNode;
    private String title;

    public AnswerCardFirstBean(List<BaseNode> childNode, String title) {
        this.childNode = childNode;
        this.title = title;
        setExpanded(true);
    }

    public String getTitle() {
        return title;
    }

    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return childNode;
    }

    @Nullable
    @Override
    public BaseNode getFooterNode() {
        return null;
    }
}
