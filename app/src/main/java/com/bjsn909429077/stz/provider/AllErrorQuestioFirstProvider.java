package com.bjsn909429077.stz.provider;

import android.view.View;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.AllErrorQuestionFirstBean;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/15 15:13
 **/
public class AllErrorQuestioFirstProvider extends BaseNodeProvider {
    @Override
    public int getItemViewType() {
        return 1;
    }

    @Override
    public int getLayoutId() {
        return R.layout.all_erro_first_item;
    }

    @Override
    public void convert(@NotNull BaseViewHolder baseViewHolder, BaseNode baseNode) {
        AllErrorQuestionFirstBean firstBean = (AllErrorQuestionFirstBean) baseNode;
        baseViewHolder.setText(R.id.all_error_name, firstBean.getChapterName());
        baseViewHolder.setText(R.id.all_error_num, "错题" + firstBean.getErrorNumber() + "个");
    }

    @Override
    public void onClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
        super.onClick(helper, view, data, position);
        AllErrorQuestionFirstBean firstBean = (AllErrorQuestionFirstBean) data;
        if (firstBean.isExpanded()) {
            getAdapter().collapse(position);
        } else {
            getAdapter().expandAndCollapseOther(position);
        }
    }
}