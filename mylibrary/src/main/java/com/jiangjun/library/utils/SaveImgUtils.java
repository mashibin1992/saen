package com.jiangjun.library.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author XuZhuChao
 * @create 2021/9/22
 * @Describe  保存图片工具类
 */
public class SaveImgUtils {

    //保存的文件路径及文件名
    private static final String SavePathName = "ImgDetails";


    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public static class MyTask extends AsyncTask<String, Integer, String> {

        private Context mContext;

        public MyTask(Context context) {
            mContext = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(final String... realFilePath) {

            return saveImageToGallery(mContext, realFilePath[0], SavePathName);
        }

        @Override
        protected void onPostExecute(String url) {
            super.onPostExecute(url);

            if (!StringUtil.isBlank(url)) {
                ToastUtils.Toast(mContext, "保存成功");
            }

        }
    }


    public static String saveImageToGallery(Context context, String image, String savePathName) {
        // 首先保存图片
//        File appDir = new File(Environment.getExternalStorageDirectory(), savePathName);
        File appDir = new File(context.getExternalFilesDir(""), savePathName);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".png";
        File file = new File(appDir, fileName);
        try {
            Bitmap bitmap = null;
            URL url = new URL(image);
            HttpURLConnection conn = null;
            conn = (HttpURLConnection) url.openConnection();
            InputStream is = null;
            is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);

            FileOutputStream outStream;

            outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 95, outStream);
            outStream.flush();
            outStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 其次把文件插入到系统图库
        savePhotoToMedia(context, file, fileName);

        return file.getAbsolutePath();
    }


    public static void savePhotoToMedia(Context context, File file, String fileName) {

        try {
            String uriString = MediaStore.Images.Media
                    .insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
//        String uriString = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, null, null);
            File file1 = new File(getRealPathFromURI(Uri.parse(uriString), context));
            updatePhotoMedia(file1, context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //得到绝对地址
    private static String getRealPathFromURI(Uri contentUri, Context context) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor == null) {
            return "";
        }
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String fileStr = cursor.getString(column_index);
        cursor.close();
        return fileStr;
    }

    //更新图库
    private static void updatePhotoMedia(File file, Context context) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(file));
        context.sendBroadcast(intent);
    }
}
