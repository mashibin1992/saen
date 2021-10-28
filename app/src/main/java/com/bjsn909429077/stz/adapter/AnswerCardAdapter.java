package com.bjsn909429077.stz.adapter;

import com.bjsn909429077.stz.bean.AnswerCardFirstBean;
import com.bjsn909429077.stz.bean.AnswerCardSecondBean;
import com.bjsn909429077.stz.provider.AnswerFirstProvider;
import com.bjsn909429077.stz.provider.AnswerSecondProvider;
import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/9 18:16
 **/
public class AnswerCardAdapter extends BaseNodeAdapter {

    public static final int EXPAND_COLLAPSE_PAYLOAD = 110;

    public AnswerCardAdapter() {
        addFullSpanNodeProvider(new AnswerFirstProvider());
        addNodeProvider(new AnswerSecondProvider());
    }

    @Override
    protected int getItemType(@NotNull List<? extends BaseNode> list, int i) {
        BaseNode node = list.get(i);
        if (node instanceof AnswerCardFirstBean) {
            return 1;
        } else if (node instanceof AnswerCardSecondBean) {
            return 2;
        }
        return -1;
    }
}
