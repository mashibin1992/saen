package cn.finalteam.rxgalleryfinal.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import cn.finalteam.rxgalleryfinal.R;
import cn.finalteam.rxgalleryfinal.RxGalleryFinalApi;
import cn.finalteam.rxgalleryfinal.SimpleRxGalleryFinal;
import cn.finalteam.rxgalleryfinal.utils.Logger;
import cn.finalteam.rxgalleryfinal.utils.MediaScanner;

public class PhotographActivity extends AppCompatActivity {

    private Context context;
    private boolean enableCrop;
    private static onPhotographCallback onPhotographCallback;
    private String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;


        enableCrop = getIntent().getBooleanExtra("enableCrop", false);
        name = getIntent().getStringExtra("name");
        if (enableCrop) {
            SimpleRxGalleryFinal.get().init(
                    new SimpleRxGalleryFinal.RxGalleryFinalCropListener() {
                        @NonNull
                        @Override
                        public Activity getSimpleActivity() {
                            return PhotographActivity.this;
                        }

                        @Override
                        public void onCropCancel() {
                            Logger.i("裁剪被取消");
                            if (onPhotographCallback != null) {
                                onPhotographCallback.onReturnFailure("裁剪被取消");
                            }
                            finish();
                        }

                        @Override
                        public void onCropSuccess(@Nullable Uri uri) {
                            Logger.i("裁剪成功");
                            File file = null;
                            try {
                                file = new File(new URI(uri.toString()));
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }

                            if (file == null) {
                                if (onPhotographCallback != null) {
                                    onPhotographCallback.onReturnFailure("图片路径错误");
                                }
                                finish();
                                return;
                            }
                            if (onPhotographCallback != null) {
                                onPhotographCallback.onReturnSuccess(uri.getPath());
                            }
                            finish();
                        }

                        @Override
                        public void onCropError(@NonNull String errorMessage) {
                            Logger.i(errorMessage);
                            if (onPhotographCallback != null) {
                                onPhotographCallback.onReturnFailure(errorMessage);
                            }
                            finish();
                        }
                    }
            ).openCamera();
        } else {
            RxGalleryFinalApi.openZKCamera(context,name);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (enableCrop) {
            SimpleRxGalleryFinal.get().onActivityResult(requestCode, resultCode, data);
        } else {
            if (requestCode == RxGalleryFinalApi.TAKE_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
                Logger.i("拍照OK，图片路径:" + RxGalleryFinalApi.fileImagePath.getPath());
                //刷新相册数据库
            RxGalleryFinalApi.openZKCameraForResult(this, new MediaScanner.ScanCallback() {
                @Override
                public void onScanCompleted(String[] strings) {
                    Logger.i(String.format("拍照成功,图片存储路径:%s", strings[0]));
                    Logger.d("演示拍照后进行图片裁剪，根据实际开发需求可去掉上面的判断");
//                    RxGalleryFinalApi.cropScannerForResult(context, RxGalleryFinalApi.getModelPath(), strings[0]);//调用裁剪.RxGalleryFinalApi.getModelPath()为默认的输出路径
                }
            });
                if (onPhotographCallback != null) {
                    onPhotographCallback.onReturnSuccess(RxGalleryFinalApi.fileImagePath.getPath());
                }
                finish();
            } else {
                Logger.i("失敗");
                if (onPhotographCallback != null) {
                    onPhotographCallback.onReturnFailure("拍照失败");
                }
                finish();
            }
        }

    }


    public static void startActivity(Context context, boolean enableCrop,String name) {
        Intent intent = new Intent(context, PhotographActivity.class);
        intent.putExtra("enableCrop", enableCrop);
        intent.putExtra("name", name);
        context.startActivity(intent);
    }


    public interface onPhotographCallback {
        void onReturnSuccess(String path);

        void onReturnFailure(String meg);
    }


    public static void setOnPhotographCallback(PhotographActivity.onPhotographCallback onPhotographCallback) {
        PhotographActivity.onPhotographCallback = onPhotographCallback;
    }
}
