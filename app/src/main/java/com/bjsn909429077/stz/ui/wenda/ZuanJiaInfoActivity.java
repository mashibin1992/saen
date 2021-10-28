package com.bjsn909429077.stz.ui.wenda;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.WdInfoAdapter;
import com.bjsn909429077.stz.adapter.ZjListAdapter;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.EmptyBean;
import com.bjsn909429077.stz.bean.WdInfoBean;
import com.bjsn909429077.stz.bean.WdZhuiWenBean;
import com.bjsn909429077.stz.bean.WenDaZhuanJiaBean;
import com.bjsn909429077.stz.bean.ZjListBean;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseActivity;
import com.jiangjun.library.utils.ToastUtils;
import com.jiangjun.library.widget.TakingPicturesPopupWindow;
import com.tamic.novate.Throwable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.finalteam.rxgalleryfinal.bean.MediaBean;

public class ZuanJiaInfoActivity extends BaseActivity{
    @BindView(R.id.iv)
    ImageView iv;  @BindView(R.id.img_back)
    ImageView img_back;  @BindView(R.id.tv_name)
    TextView tv_name;
@BindView(R.id.tv_lingyu)
    TextView tv_lingyu;@BindView(R.id.tv_jianjie)
    TextView tv_jianjie;@BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private String expertName;


    @Override
    protected int setLayoutRes() {
        return R.layout.activity_zj_info;
    }

    @Override
    protected boolean isShowTitleView() {
        return false;
    }


    @Override
    protected void findViews(@Nullable Bundle savedInstanceState) {
        view_head.setVisibility(View.GONE);
        Intent intent = getIntent();
        WenDaZhuanJiaBean.DataBean bean = (WenDaZhuanJiaBean.DataBean) intent.getSerializableExtra("bean");

        //用户头像
        Glide.with(this).load(bean.getHeaderPath()).into(iv);
        expertName = bean.getExpertName();
        tv_name.setText(expertName);
        List<String> expertiseAreaNameList = bean.getExpertiseAreaNameList();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i <expertiseAreaNameList.size() ; i++) {
            if (i!=expertiseAreaNameList.size()-1){
                stringBuffer.append(expertiseAreaNameList.get(i)+"、");
            }else {
                stringBuffer.append(expertiseAreaNameList.get(i));
            }

        }
        tv_lingyu.setText(stringBuffer.toString());
        tv_jianjie.setText(bean.getIntroduction());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);


        getZjInfoList(bean.getExpertId()+"");
    }

    private void getZjInfoList(String expertId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("expertId",expertId);
        NovateUtils.getInstance().Post(mContext, BaseUrl.zjInfoList, map, true,
                new NovateUtils.setRequestReturn<ZjListBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(mContext, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(ZjListBean zjListBean) {
                        if (zjListBean!=null&&zjListBean.getData()!=null){
                            List<ZjListBean.DataBean> data = zjListBean.getData();
                            ZjListAdapter zjListAdapter = new ZjListAdapter(ZuanJiaInfoActivity.this,R.layout.item_zj,data,expertName);
                            recyclerView.setAdapter(zjListAdapter);
                            zjListAdapter.setOnItemClickListener(new OnItemClickListener() {
                                @Override
                                public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

                                    //问答详情
                                    Intent intent = new Intent(ZuanJiaInfoActivity.this, WenDaInfoActivity.class);
                                    intent.putExtra("wdId",data.get(position).getWdId()+"");
                                    startActivity(intent);
                                }
                            });
                        }
                    }
                });
    }

    @Override
    protected void initData() {


    }

    @Override
    protected void onResume() {
        super.onResume();

    }
    @OnClick({R.id.img_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
               finish();
                break;


            default:

        }
    }
}