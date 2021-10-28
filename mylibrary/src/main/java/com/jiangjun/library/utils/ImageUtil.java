package com.jiangjun.library.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by jiangjun on 2019/8/30 15:23
 */
public class ImageUtil {
    private static String rootPath = "YJYPhoto";
    private static String playPath = "";
    private final static String PHOTO_JPG_BASEPATH = "/" + rootPath + "/img/";
    private final static String PHOTO_COMPRESS_JPG_BASEPATH = "/" + rootPath + "/CompressImgs/";

    public static void setRootPath(String rootPath) {
        ImageUtil.rootPath = rootPath;
    }

    /**
     * @param fileName :System.currentTimeMillis() + ".jpg"//用时间戳
     * @return 获取保存原始文件的位置
     */

    public static String getJpgFileAbsolutePath(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            throw new NullPointerException("fileName isEmpty");
        }
        String photoPath = "";
        if (!fileName.endsWith(".jpg")) {
            fileName = fileName + ".jpg";
        }
        String fileBasePath = Environment.getExternalStorageDirectory().getAbsolutePath() + PHOTO_JPG_BASEPATH;
        File file = new File(fileBasePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        photoPath = fileBasePath + fileName;
        return photoPath;
    }

    /**
     * 获取保存压缩图片文件的位置
     *
     * @return
     */
    public static String getCompressJpgFileAbsolutePath() {
        String fileBasePath = Environment.getExternalStorageDirectory().getAbsolutePath() + PHOTO_COMPRESS_JPG_BASEPATH;
        File file = new File(fileBasePath);
        if (!file.exists()) {
            file.mkdirs();
        }

        return fileBasePath;
    }


    /**
     * 使用LuBan 压缩单张图片
     *
     * @param context
     * @param imageUrl
     * @param compressCallBack 结果回调
     */
    public static void luBanCompress(Context context, final String imageUrl, final ImageCompressCallBack compressCallBack) {
        Luban.with(context)
                .load(new File(imageUrl))
                .ignoreBy(100)//低于100的图片不用压缩
                .putGear(3)//设置压缩级别 默认是3
                .setTargetDir(ImageUtil.getCompressJpgFileAbsolutePath())
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(File file) {
//                        File oldfile = new File(imageUrl);//删掉旧文件
//                        if (oldfile.exists()) {
//                            oldfile.delete();
//                        }
                        compressCallBack.onSucceed(file.getAbsolutePath());
                    }

                    @Override
                    public void onError(Throwable e) {
                        compressCallBack.onFailure(e.getMessage());
                    }
                }).launch();
    }

    public interface ImageCompressCallBack {
        void onSucceed(String data);
        void onFailure(String msg);
    }
}