package com.jiangjun.library.widget;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.jiangjun.library.R;
import com.jiangjun.library.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.RxGalleryFinalApi;
import cn.finalteam.rxgalleryfinal.bean.MediaBean;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultDisposable;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;
import cn.finalteam.rxgalleryfinal.ui.RxGalleryListener;
import cn.finalteam.rxgalleryfinal.ui.base.IMultiImageCheckedListener;


/**
 * Created by Administrator姜军 on 2018/6/6.
 * 视频和照片选择
 */

public class VideoPhotoListPopupWindow extends PopupWindow implements View.OnClickListener {
    private final TextView camera_photographic;
    private final TextView camera_photo;
    private final TextView camera_cancel;
    private final View line_view;
    private final Context mContext;
    private onCameraClickListener onCameraClickListener;
    private boolean camera;
    private int max = 9;
    private final int PermissionsReturn = 1222;
    private boolean enableCrop = false;

    private List<MediaBean> list = null;
    private final String name = "RapidApp";

    public VideoPhotoListPopupWindow(Context context) {
        super(context);
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popupwindow_vieo_photo, null);
        camera_photographic = view.findViewById(R.id.camera_photographic);
        camera_photo = view.findViewById(R.id.camera_photo);
        camera_cancel = view.findViewById(R.id.camera_cancel);
        line_view = view.findViewById(R.id.line_view);

        camera_photographic.setOnClickListener(this);
        camera_photo.setOnClickListener(this);
        camera_cancel.setOnClickListener(this);


        this.setContentView(view);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置PopupWindow可获得焦点
        this.setFocusable(true);
        //设置PopupWindow可触摸
        this.setTouchable(true);
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1f);//设置屏幕透明度
            }
        });
        // 设置动画效果
        this.setAnimationStyle(R.style.take_photo_anim);
        ColorDrawable dw = new ColorDrawable(0x00000000);
        this.setBackgroundDrawable(dw);

        RxGalleryFinalApi.setImgSaveRxSDCard(name);
        RxGalleryFinalApi.setImgSaveRxCropSDCard(name + "/crop");//裁剪会自动生成路径；也可以手动设置裁剪的路径；
        //得到图片多选的事件
        RxGalleryListener.getInstance().setMultiImageCheckedListener(new IMultiImageCheckedListener() {
            @Override
            public void selectedImg(Object t, boolean isChecked) {
                //这个主要点击或者按到就会触发，所以不建议在这里进行 Toast
            }

            @Override
            public void selectedImgMax(Object t, boolean isChecked, int maxSize) {
                ToastUtils.Toast(mContext, "你最多只能选择" + maxSize + "张图片");
            }
        });

    }

    public void isShowVideoPic(boolean v, boolean p) {
        camera_photographic.setVisibility(v ? View.GONE : View.VISIBLE);
        camera_photo.setVisibility(p ? View.GONE : View.VISIBLE);
    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.camera_photographic) {//视频
            camera = true;
            Camera();
            setBackgroundAlpha(1f);//设置屏幕透明度
            this.dismiss();

        } else if (i == R.id.camera_photo) {//相册
            camera = false;
            Camera();
            setBackgroundAlpha(1f);//设置屏幕透明度
            this.dismiss();

        } else if (i == R.id.camera_cancel) {//取消
            this.dismiss();
            setBackgroundAlpha(1f);//设置屏幕透明度

        }
    }

    public List<MediaBean> getList() {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    private void Camera() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //申请写入权限
            ActivityCompat.requestPermissions((Activity) mContext, new String[]
                    {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PermissionsReturn);
        } else {
            if (camera) {
                RxGalleryFinalApi
                        .getInstance((Activity) mContext)
                        .setType(RxGalleryFinalApi.SelectRXType.TYPE_VIDEO, RxGalleryFinalApi.SelectRXType.TYPE_SELECT_RADIO)
                        .setVDRadioResultEvent(new RxBusResultDisposable<ImageRadioResultEvent>() {
                            @Override
                            protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) throws Exception {
                                onCameraClickListener.onReturnVideo(camera, imageRadioResultEvent.getResult().getOriginalPath());
                            }
                        })
                        .open();

            } else {
                //多选
                //自定义方法的多选
                RxGalleryFinal.with(mContext)
                        .image()
                        .multiple()
                        .maxSize(max)
                        .selected(list)
                        .imageLoader(ImageLoaderType.UNIVERSAL)
                        .subscribe(new RxBusResultDisposable<ImageMultipleResultEvent>() {
                            @Override
                            protected void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) throws Exception {
                                list = imageMultipleResultEvent.getResult();
                                onCameraClickListener.onReturnPicture(camera, imageMultipleResultEvent.getResult());

                                ToastUtils.Toast(mContext, "已选择" + imageMultipleResultEvent.getResult().size() + "张图片");
                            }
                        }).openGallery();
            }


        }
    }

    public void onListRemove(int p) {
        if (list != null) {
            try {
                list.remove(p);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //设置选择图片最大数
    public void setMax(int max) {
        if (max > 0 && max < 9) {
            this.max = max;
        }
    }

    //不展示相册
    public void setPhotoAlbumHidden() {
        camera_photo.setVisibility(View.GONE);
        line_view.setVisibility(View.GONE);
    }

    public void show(View view, boolean enableCrop) {

        this.enableCrop = enableCrop;

        setBackgroundAlpha(0.7f);//设置屏幕透明度
        this.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha 屏幕透明度0.0-1.0 1表示完全不透明
     */
    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) mContext).getWindow().setAttributes(lp);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        //权限回调
        switch (requestCode) {
            case PermissionsReturn:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Camera();

                } else {// 没有获取到权限，做特殊处理
                    Toast.makeText(mContext, "请授予权限！", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }


    public void setOnCameraClickListener(VideoPhotoListPopupWindow.onCameraClickListener
                                                 onCameraClickListener) {
        this.onCameraClickListener = onCameraClickListener;
    }

    public interface onCameraClickListener {
        void onReturnPicture(boolean isVideo, List<MediaBean> list);

        void onReturnVideo(boolean isVideo, String realFilePath);
    }


}
