package com.bjsn909429077.stz.provider;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.AllErrorQuestionSecondBean;
import com.bjsn909429077.stz.ui.questionbank.activity.QuestionActivity;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/15 15:13
 **/
public class AllErrorQuestioSecondProvider extends BaseNodeProvider {
    @Override
    public int getItemViewType() {
        return 2;
    }

    @Override
    public int getLayoutId() {
        return R.layout.all_error_second_item;
    }

    @Override
    public void convert(@NotNull BaseViewHolder baseViewHolder, BaseNode baseNode) {
        AllErrorQuestionSecondBean secondBean = (AllErrorQuestionSecondBean) baseNode;
        baseViewHolder.setText(R.id.all_error_name, secondBean.getNodeName());
        baseViewHolder.setText(R.id.all_error_num, "错题" + secondBean.getErrorNumber() + "个");
        TextView all_error_redo = baseViewHolder.itemView.findViewById(R.id.all_error_redo);
        all_error_redo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), QuestionActivity.class);
                intent.putExtra("isPractice", true);
                getContext().startActivity(intent);
            }
        });
    }
}
