package com.bjsn909429077.stz.adapter;

import android.content.Context;

import com.bjsn909429077.stz.bean.PracticeFirstBean;
import com.bjsn909429077.stz.bean.PracticeSecondBean;
import com.bjsn909429077.stz.provider.PracticeFirstProvider;
import com.bjsn909429077.stz.provider.PracticeSecondProvider;
import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/6 14:42
 **/
public class PracticeAdapter extends BaseNodeAdapter {

    public static final int EXPAND_COLLAPSE_PAYLOAD = 110;
    private int nodeType;//类型 1.章节 2.专项
    private Context context;

    public PracticeAdapter(int nodeType,Context context) {
        addNodeProvider(new PracticeFirstProvider());
        addNodeProvider(new PracticeSecondProvider(nodeType,context));
    }

    @Override
    protected int getItemType(@NotNull List<? extends BaseNode> list, int i) {
        BaseNode node = list.get(i);
        if (node instanceof PracticeFirstBean) {
            return 1;
        } else if (node instanceof PracticeSecondBean) {
            return 2;
        }
        return -1;
    }
}
