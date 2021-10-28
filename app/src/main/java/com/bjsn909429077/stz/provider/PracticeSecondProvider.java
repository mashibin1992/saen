package com.bjsn909429077.stz.provider;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.PracticeSecondBean;
import com.bjsn909429077.stz.ui.questionbank.activity.AnswerReportActivity;
import com.bjsn909429077.stz.ui.questionbank.activity.QuestionActivity;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/6 15:39
 **/
public class PracticeSecondProvider extends BaseNodeProvider {
    private int nodeType;//类型 1.章节 2.专项
    private Context mContext;

    public PracticeSecondProvider(int nodeType, Context context) {
        this.nodeType = nodeType;
        mContext=context;
    }

    @Override
    public int getItemViewType() {
        return 2;
    }

    @Override
    public int getLayoutId() {
        return R.layout.practice_second_item;
    }

    @Override
    public void convert(@NotNull BaseViewHolder baseViewHolder, BaseNode baseNode) {
        PracticeSecondBean entity = (PracticeSecondBean) baseNode;
        baseViewHolder.setText(R.id.practice_second_title, entity.getNodeName());
        TextView practice_second_jxdt = baseViewHolder.itemView.findViewById(R.id.practice_second_jxdt);
        TextView practice_second_cxdt = baseViewHolder.itemView.findViewById(R.id.practice_second_cxdt);
        TextView practice_second_dtbg = baseViewHolder.itemView.findViewById(R.id.practice_second_dtbg);
        ProgressBar practice_second_pb = baseViewHolder.itemView.findViewById(R.id.practice_second_pb);
        TextView practice_second_jd = baseViewHolder.itemView.findViewById(R.id.practice_second_jd);
        if (Integer.parseInt(entity.getCompleteNumber()) < Integer.parseInt(entity.getCountNumber()))
            practice_second_jxdt.setVisibility(View.VISIBLE);
        else {
            practice_second_cxdt.setVisibility(View.VISIBLE);
            practice_second_dtbg.setVisibility(View.VISIBLE);
        }
        practice_second_jd.setText(entity.getCompleteNumber() + "/" + entity.getCountNumber());
        practice_second_pb.setMax(Integer.parseInt(entity.getCountNumber()));
        practice_second_pb.setProgress(Integer.parseInt(entity.getCompleteNumber()));
        //继续答题
        practice_second_jxdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, QuestionActivity.class);
                intent.putExtra("isPractice", true);//是否是专项练习/章节练习
                intent.putExtra("nodeId", Integer.parseInt(entity.getNodeId()));//节id
                intent.putExtra("nodeType", nodeType);//类型 1.章节 2.专项
                intent.putExtra("type", 1);//类型 1.答题or继续 2.重新答题
                mContext.startActivity(intent);
            }
        });
        //重新答题
        practice_second_cxdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, QuestionActivity.class);
                intent.putExtra("isPractice", true);
                intent.putExtra("nodeId", Integer.parseInt(entity.getNodeId()));//节id
                intent.putExtra("nodeType", nodeType);//类型 1.章节 2.专项
                intent.putExtra("type", 2);//类型 1.答题or继续 2.重新答题
                mContext.startActivity(intent);
            }
        });
        //答题报告
        practice_second_dtbg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AnswerReportActivity.class);
                intent.putExtra("isPractice", true);
                intent.putExtra("nodeId", entity.getNodeId());
                intent.putExtra("nodeType", nodeType);
                mContext.startActivity(intent);
            }
        });
    }
}
