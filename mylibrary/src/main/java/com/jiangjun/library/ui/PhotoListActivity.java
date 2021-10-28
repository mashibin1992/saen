package com.jiangjun.library.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.jiangjun.library.R;
import com.jiangjun.library.ui.base.BaseActivity;
import com.jiangjun.library.utils.ImageLoaderUtils;
import com.jiangjun.library.utils.RLog;
import com.jiangjun.library.utils.StatusBarUtil;
import com.jiangjun.library.widget.photo.PhotoView;

import java.util.ArrayList;

/**
 * 图片查看
 */
public class PhotoListActivity extends BaseActivity implements View.OnClickListener {
    public  Context           mContext;
    private PhotoView         photoView;
    private ViewPager vp;
    private ArrayList<String> list;
    private String type;
    private ArrayList<Integer> intList;
    private ImageView imageView;
    private TextView tv_number;



    @Override
    protected void findViews(@Nullable Bundle savedInstanceState) {
        vp = findViewById(R.id.vp);
        imageView = findViewById(R.id.imageView);
        tv_number = findViewById(R.id.tv_number);
        view_head.setBackgroundColor(getResources().getColor(R.color.black));
        StatusBarUtil.changStatusIconCollor(this, false);
        type = getIntent().getStringExtra("type");
        if (type.equals("string")){

            list = getIntent().getStringArrayListExtra("data");
        }else {
            intList = getIntent().getIntegerArrayListExtra("data");

        }

        int position = getIntent().getIntExtra("position", 0);


        if (type.equals("string")){
            tv_number.setText((position+1) + "/" + list.size());
        }else {
            tv_number.setText((position+1) + "/" + intList.size());
        }



        imageView.setOnClickListener(this);
        vp.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                if (type.equals("string")){
                    return list.size();
                }else {
                    return intList.size();
                }

            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {
                View view = View.inflate(mContext, R.layout.item_photo, null);
                photoView = view.findViewById(R.id.img);


                // 启用图片缩放功能
                photoView.enable();
                if (type.equals("string")){
                    ImageLoaderUtils.loadUrl(mContext, list.get(position), photoView);
                }else {
                    ImageLoaderUtils.loadRes(mContext,intList.get(position),photoView);
                }




                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        });
        vp.setCurrentItem(position);

        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (type.equals("string")){
                    tv_number.setText((position+1) + "/" + list.size());
                }else {
                    tv_number.setText((position+1) + "/" + intList.size());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_photo_list;
    }

    @Override
    protected boolean isShowTitleView() {
        return false;
    }

    public static void startStringActivity(Context context, int position,ArrayList<String> list) {
        Intent intent = new Intent(context, PhotoListActivity.class);
        intent.putExtra("position", position);
        intent.putStringArrayListExtra("data", list);
        intent.putExtra("type","string");
        context.startActivity(intent);
    }


    public static void startIntActivity(Context context, int position,ArrayList<Integer> list) {
        Intent intent = new Intent(context, PhotoListActivity.class);
        intent.putExtra("position", position);
        intent.putIntegerArrayListExtra("data", list);
        intent.putExtra("type","int");
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imageView) {
            finish();
        }
    }
}
