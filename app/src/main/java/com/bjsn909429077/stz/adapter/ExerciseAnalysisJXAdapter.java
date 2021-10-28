package com.bjsn909429077.stz.adapter;

import android.net.Uri;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.ExerciseAnalysisJXBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/16 14:45
 **/
public class ExerciseAnalysisJXAdapter extends BaseQuickAdapter<ExerciseAnalysisJXBean, BaseViewHolder> {

    private TextView jx_type;
    private WebView jx_content_tv;
    private TextView jx_content_kaodian;
    private VideoView jx_content_vv;

    public ExerciseAnalysisJXAdapter(int layoutResId, @Nullable List<ExerciseAnalysisJXBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ExerciseAnalysisJXBean exerciseAnalysisJXBean) {
        jx_type = baseViewHolder.itemView.findViewById(R.id.jx_type);
        jx_content_tv = baseViewHolder.itemView.findViewById(R.id.jx_content_tv);
        jx_content_kaodian = baseViewHolder.itemView.findViewById(R.id.jx_content_kaodian);
        jx_content_vv = baseViewHolder.itemView.findViewById(R.id.jx_content_vv);
        jx_type.setText(exerciseAnalysisJXBean.getJxType());
        if (exerciseAnalysisJXBean.getJxType().equals("文字解析")) {
            jx_content_tv.setVisibility(View.VISIBLE);
            jx_content_kaodian.setVisibility(View.GONE);
            jx_content_vv.setVisibility(View.GONE);
            jx_content_tv.loadDataWithBaseURL(null,exerciseAnalysisJXBean.getJxContent(), "text/html" , "utf-8", null);
        } else if (exerciseAnalysisJXBean.getJxType().equals("视频解析")) {
            jx_content_tv.setVisibility(View.GONE);
            jx_content_kaodian.setVisibility(View.GONE);
            jx_content_vv.setVisibility(View.VISIBLE);
            jx_content_vv.setFocusable(false);
            //jx_content_vv.setVideoPath(exerciseAnalysisJXBean.getJxContent());
            /*jx_content_vv.requestFocus();
            jx_content_vv.start();*/
        } else if (exerciseAnalysisJXBean.getJxType().equals("考点")) {
            jx_content_tv.setVisibility(View.GONE);
            jx_content_kaodian.setVisibility(View.VISIBLE);
            jx_content_vv.setVisibility(View.GONE);
            jx_content_kaodian.setText(exerciseAnalysisJXBean.getJxContent());
        }
    }
}
