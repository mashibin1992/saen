package com.bjsn909429077.stz.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.NoteListBean;
import com.bjsn909429077.stz.ui.video.fragment.NoteFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class NoteAdapter extends BaseQuickAdapter<NoteListBean.DataBean, BaseViewHolder> {
    private final NoteFragment noteFragment;
    private OnEditListener mListener;

    public NoteAdapter(int item_handout, ArrayList<NoteListBean.DataBean> strings, NoteFragment noteFragment) {
        super(item_handout, strings);
        this.noteFragment = noteFragment;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, NoteListBean.DataBean s) {
        ImageView image_edit = baseViewHolder.findView(R.id.image_edit);
        TextView tv_date = baseViewHolder.findView(R.id.tv_date);
        TextView tv_title = baseViewHolder.findView(R.id.tv_title);

        tv_title.setText(s.content);
        tv_date.setText("上架时间:" + s.intime);

        image_edit.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onEditClick(s, baseViewHolder.getLayoutPosition());
            }
        });
    }

    public interface OnEditListener {
        void onEditClick(NoteListBean.DataBean data, int position);
    }

    public void setOnEditListener(OnEditListener listener) {

        mListener = listener;
    }
}
