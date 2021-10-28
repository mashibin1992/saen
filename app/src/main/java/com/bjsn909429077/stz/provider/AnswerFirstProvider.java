package com.bjsn909429077.stz.provider;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.AnswerCardFirstBean;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/9 18:23
 **/
public class AnswerFirstProvider extends BaseNodeProvider {
    @Override
    public int getItemViewType() {
        return 1;
    }

    @Override
    public int getLayoutId() {
        return R.layout.answer_first_item;
    }

    @Override
    public void convert(@NotNull BaseViewHolder baseViewHolder, BaseNode baseNode) {
        AnswerCardFirstBean firstBean= (AnswerCardFirstBean) baseNode;
        baseViewHolder.setText(R.id.answer_first_item_tv, firstBean.getTitle());
    }
}
