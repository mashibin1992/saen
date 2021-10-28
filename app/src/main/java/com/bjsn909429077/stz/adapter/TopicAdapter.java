package com.bjsn909429077.stz.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.ShiJuanTestBean;
import com.bjsn909429077.stz.bean.SubjectQuestionBean;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/7 15:20
 **/
public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicViewHolder> {

    private Context mContext;
    private final LayoutInflater inflater;
    private final Resources resources;
    private List<?> datas;
    //private List<SubjectQuestionBean.DataBean.PaperChapterSubjectListBean> datas;

    public void setDatas(List<?> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    public TopicAdapter(Context mContext) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        resources = mContext.getResources();

    }

    @Override
    public TopicAdapter.TopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_topic, parent, false);
        return new TopicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TopicAdapter.TopicViewHolder holder, final int position) {
        if (datas.get(position) instanceof SubjectQuestionBean.DataBean.PaperChapterSubjectListBean) {
            SubjectQuestionBean.DataBean.PaperChapterSubjectListBean o = (SubjectQuestionBean.DataBean.PaperChapterSubjectListBean) datas.get(position);
            if (datas != null && o.getQuestion_select() != -1) {//表示已经做过
                holder.tv_id.setBackgroundResource(R.drawable.bg_topic);
                holder.tv_id.setTextColor(Color.parseColor("#6495ED"));
            }
        } else if (datas.get(position) instanceof ShiJuanTestBean.DataBean.PaperSubjectListBean) {
            ShiJuanTestBean.DataBean.PaperSubjectListBean o = (ShiJuanTestBean.DataBean.PaperSubjectListBean) datas.get(position);
            if (datas != null && o.getQuestion_select() != -1) {//表示已经做过
                holder.tv_id.setBackgroundResource(R.drawable.bg_topic);
                holder.tv_id.setTextColor(Color.parseColor("#6495ED"));
            }
        }
        holder.tv_id.setText((position + 1) + "");
        holder.tv_id.setTextColor(Color.parseColor("#b3afaf"));
        holder.tv_id.setBackgroundResource(R.drawable.bg_topic_no);
        if (prePosition == position) {
            holder.tv_id.setBackgroundResource(R.drawable.bg_topic_no);
            holder.tv_id.setTextColor(Color.parseColor("#b3afaf"));

        }
        if (curPosition == position) {
            holder.tv_id.setBackgroundResource(R.drawable.bg_topic_ok);
            holder.tv_id.setTextColor(Color.parseColor("#b3afaf"));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(holder, position);
            }
        });
    }

    private OnTopicClickListener listener;

    public void setOnTopicClickListener(OnTopicClickListener listener) {
        this.listener = listener;
    }

    private int curPosition;

    public void notifyCurPosition(int curPosition) {
        this.curPosition = curPosition;
        notifyItemChanged(curPosition);
    }

    private int prePosition;

    public void notifyPrePosition(int prePosition) {
        this.prePosition = prePosition;
        notifyItemChanged(prePosition);
    }


    public interface OnTopicClickListener {
        void onClick(TopicAdapter.TopicViewHolder holder, int position);
    }

    private int num;

    public void setDataNum(int num) {
        this.num = num;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return num;
    }


    public static class TopicViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_id;

        public TopicViewHolder(View itemView) {
            super(itemView);
            tv_id = (TextView) itemView.findViewById(R.id.tv_id);
        }
    }
}
