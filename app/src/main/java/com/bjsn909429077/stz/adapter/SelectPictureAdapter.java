package com.bjsn909429077.stz.adapter;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.VPBean;
import com.bjsn909429077.stz.utils.LoadingImgFrescoUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;

public class SelectPictureAdapter extends BaseQuickAdapter<VPBean, BaseViewHolder> {
    public SelectPictureAdapter() {
        super(R.layout.item_select_image);
        addChildClickViewIds(R.id.image_clear);
    }

    @Override
    protected void convert(BaseViewHolder helper, VPBean item) {

        SimpleDraweeView image_pic = helper.getView(R.id.image_pic);


        if (item.getType() == 0) {
            LoadingImgFrescoUtils.loadRidImg_round(R.drawable.icon_upload, image_pic,0);
            helper.setVisible(R.id.image_clear,false);
        } else if (item.getType() == 1) {
            LoadingImgFrescoUtils.loadFileUrlImg_round(item.getUrl(), image_pic,2);
            helper.setVisible(R.id.image_clear,true);
        }

    }
}