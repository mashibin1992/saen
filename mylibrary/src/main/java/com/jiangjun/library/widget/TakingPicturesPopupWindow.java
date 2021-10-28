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
import com.jiangjun.library.utils.RLog;
import com.jiangjun.library.utils.ToastUtils;

import java.io.File;
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
import cn.finalteam.rxgalleryfinal.ui.activity.PhotographActivity;
import cn.finalteam.rxgalleryfinal.ui.base.IMultiImageCheckedListener;
import cn.finalteam.rxgalleryfinal.ui.base.IRadioImageCheckedListener;
import cn.finalteam.rxgalleryfinal.utils.FileUtils;
import cn.finalteam.rxgalleryfinal.utils.Logger;
import cn.finalteam.rxgalleryfinal.utils.MediaUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator姜军 on 2018/6/6.
 * 选择照片
 */
public class TakingPicturesPopupWindow extends PopupWindow implements View.OnClickListener {
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

    public TakingPicturesPopupWindow(Context context) {
        super(context);
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popupwindow_camera, null);
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

    public int getMax() {
        return max;
    }

    public void setList(boolean isList) {
       if (isList){
           list=new ArrayList<>();
       }else {
           list=null;
       }
    }

    public List<MediaBean> getList() {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.camera_photographic) {//拍照
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



    public void Camera() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //申请写入权限
            ActivityCompat.requestPermissions((Activity) mContext, new String[]
                    {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PermissionsReturn);
        } else {
            if (camera) {
                //调用相机拍照
                PhotographActivity.startActivity(mContext, enableCrop, name);
                PhotographActivity.setOnPhotographCallback(new PhotographActivity.onPhotographCallback() {
                    @Override
                    public void onReturnSuccess(String path) {
                        //拍照返回添加到相册选中里
                        if (list!=null){
                            onScanCompleted(path);
                        }


                        onCameraClickListener.onReturnPicture(path);
                    }

                    @Override
                    public void onReturnFailure(String meg) {
                        RLog.e("TakingPicturesPopupWindow", meg);
                    }
                });

            } else {

                //调用相册
                if (list != null ) {
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

                                    onCameraClickListener.onReturnPicture(list);
                                    ToastUtils.Toast(mContext, "已选择" + imageMultipleResultEvent.getResult().size() + "张图片");
                                }
                            }).openGallery();
                } else {
                    //单选

                    //快速打开单选图片,flag使用true不裁剪
                    RxGalleryFinalApi
                            .openRadioSelectImage((Activity) mContext, new RxBusResultDisposable<ImageRadioResultEvent>() {
                                @Override
                                protected void onEvent(ImageRadioResultEvent radioResultEvent) throws Exception {
                                    if (!enableCrop) {


                                        onCameraClickListener.onReturnPicture(radioResultEvent.getResult().getOriginalPath());
                                    }

                                }
                            }, !enableCrop).onCropImageResult(
                            new IRadioImageCheckedListener() {
                                @Override
                                public void cropAfter(Object t) {
                                    File file = (File) t;
                                    ToastUtils.Toast(mContext, "裁剪完成");
                                    onCameraClickListener.onReturnPicture(file.getAbsolutePath());
                                }

                                @Override
                                public boolean isActivityFinish() {
                                    Logger.i("返回false不关闭，返回true则为关闭");
                                    return true;
                                }
                            });

                }

            }

        }
    }


    public void onScanCompleted(String images) {
        if (images == null ) {
            Logger.i("images empty");
            return;
        }

        // mediaBean 有可能为Null，onNext 做了处理，在 getMediaBeanWithImage 时候就不处理Null了
        Observable.create((ObservableOnSubscribe<MediaBean>) subscriber -> {
            MediaBean mediaBean = MediaUtils.getMediaBeanWithImage(mContext, images);
            subscriber.onNext(mediaBean);
            subscriber.onComplete();
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<MediaBean>() {
                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.i("获取MediaBean异常" + e.toString());
                    }

                    @Override
                    public void onNext(MediaBean mediaBean) {
                        if ( mediaBean != null) {
                            int bk = FileUtils.existImageDir(mediaBean.getOriginalPath());
                            if (bk != -1) {
                               list.add(mediaBean);
                                onCameraClickListener.onReturnPicture(list);
                            } else {
                                Logger.i("获取：无");
                            }
                        }

                    }
                });
    }


    //设置选择图片最大数
    public void setMax(int max) {
        this.max = max;
    }

    //不展示相册
    public void setPhotoAlbumHidden() {
        camera_photo.setVisibility(View.GONE);
        line_view.setVisibility(View.GONE);
    }

    //不展示拍照
    public void setPicHidden() {
//        camera_photographic.setVisibility(View.GONE);
//        line_view.setVisibility(View.GONE);
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
//
//    //图片压缩
//    public void compressBmpToFile(final String realFilePath) {
//        if (realFilePath != null) {
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inJustDecodeBounds = true;
//            BitmapFactory.decodeFile(realFilePath, options);
//            String type = options.outMimeType;
//            if (TextUtils.isEmpty(type)) {
//                ToastUtils.Toast(mContext, "");
//                return;
//            }
//
//            //图片压缩开始
//            ImageUtil.luBanCompress(mContext, realFilePath, new ImageUtil.ImageCompressCallBack() {
//                @Override
//                public void onSucceed(String data) {
//                    //压缩后的结果回调
//                    RLog.e("TakingPicturesPopupWindow", "压缩完成" + data);
//
//                    onCameraClickListener.onReturnPicture(data);
//                }
//
//                @Override
//                public void onFailure(String msg) {
//                    onCameraClickListener.onReturnPicture(realFilePath);
//                    RLog.e("TakingPicturesPopupWindow", "错误" + msg);
//                }
//            });
//
//        }
//    }


    public void setOnCameraClickListener(TakingPicturesPopupWindow.onCameraClickListener
                                                 onCameraClickListener) {
        this.onCameraClickListener = onCameraClickListener;
    }

    public interface onCameraClickListener {
        void onReturnPicture(List<MediaBean> list);

        void onReturnPicture(String realFilePath);
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
}
