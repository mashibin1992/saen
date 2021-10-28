package com.bjsn909429077.stz.provider;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.PracticeFirstBean;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/6 14:47
 **/
public class PracticeFirstProvider extends BaseNodeProvider {
    @Override
    public int getItemViewType() {
        return 1;
    }

    @Override
    public int getLayoutId() {
        return R.layout.practice_first_item;
    }

    @Override
    public void convert(@NotNull BaseViewHolder baseViewHolder, BaseNode baseNode) {
        PracticeFirstBean firstBean = (PracticeFirstBean) baseNode;
        baseViewHolder.setText(R.id.practice_first_title, firstBean.getChapterName());
        TextView practice_first_jxdt = baseViewHolder.itemView.findViewById(R.id.practice_first_jxdt);
        TextView practice_first_cxdt = baseViewHolder.itemView.findViewById(R.id.practice_first_cxdt);
        TextView practice_first_jd = baseViewHolder.itemView.findViewById(R.id.practice_first_jd);
        ProgressBar practice_first_pb = baseViewHolder.itemView.findViewById(R.id.practice_first_pb);
        if (Integer.parseInt(firstBean.getCompleteNumber()) < Integer.parseInt(firstBean.getCountNumber()))
            practice_first_jxdt.setVisibility(View.VISIBLE);
        else
            practice_first_cxdt.setVisibility(View.VISIBLE);
        practice_first_jd.setText(firstBean.getCompleteNumber() + "/" + firstBean.getCountNumber());
        practice_first_pb.setMax(Integer.parseInt(firstBean.getCountNumber()));
        practice_first_pb.setProgress(Integer.parseInt(firstBean.getCompleteNumber()));
        if (firstBean.isExpanded()) {
            baseViewHolder.setImageResource(R.id.practice_first_icon, R.drawable.practice_del);
        } else {
            baseViewHolder.setImageResource(R.id.practice_first_icon, R.drawable.practice_add);
        }
    }

    @Override
    public void onClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
        super.onClick(helper, view, data, position);
        PracticeFirstBean firstBean = (PracticeFirstBean) data;
        if (firstBean.isExpanded()) {
            getAdapter().collapse(position);
        } else {
            getAdapter().expandAndCollapseOther(position);
        }
    }
}
