package com.bjsn909429077.stz.adapter;

import com.bjsn909429077.stz.bean.AllErrorQuestionFirstBean;
import com.bjsn909429077.stz.bean.AllErrorQuestionSecondBean;
import com.bjsn909429077.stz.provider.AllErrorQuestioFirstProvider;
import com.bjsn909429077.stz.provider.AllErrorQuestioSecondProvider;
import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/15 15:10
 **/
public class AllErrorQuestionAdapter extends BaseNodeAdapter {
    public static final int EXPAND_COLLAPSE_PAYLOAD = 110;

    public AllErrorQuestionAdapter() {
        addNodeProvider(new AllErrorQuestioFirstProvider());
        addNodeProvider(new AllErrorQuestioSecondProvider());
    }

    @Override
    protected int getItemType(@NotNull List<? extends BaseNode> list, int i) {
        BaseNode node = list.get(i);
        if (node instanceof AllErrorQuestionFirstBean) {
            return 1;
        } else if (node instanceof AllErrorQuestionSecondBean) {
            return 2;
        }
        return -1;
    }
}
