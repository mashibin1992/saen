package com.bjsn909429077.stz.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bjsn909429077.stz.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ImageAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private OnItemClickListener listener;

    public ImageAdapter(ArrayList<String> filePath) {
        super(R.layout.item_image_answer, filePath);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String s) {
        ImageView imageView2 = baseViewHolder.getView(R.id.imageView2);
        ImageView image_delete = baseViewHolder.getView(R.id.image_delete);

        imageView2.setOnClickListener(v -> {
            if (listener != null) {
                listener.imageLook(s);
            }
        });
        image_delete.setOnClickListener(v -> {
            if (listener != null) {
                listener.imageDelete(baseViewHolder.getLayoutPosition());
            }
        });
    }

    public interface OnItemClickListener {
        void imageLook(String path);

        void imageDelete(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
