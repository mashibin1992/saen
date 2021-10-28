package com.bjsn909429077.stz.utils;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSAuthCredentialsProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.bjsn909429077.stz.bean.VPBean;
import com.bjsn909429077.stz.ui.MyApplication;
import com.jiangjun.library.api.NovateUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author XuZhuChao
 * @create 2021/8/12
 * @Describe
 */
public class OSSUpDataUtils {

    private String endpoint = "oss-cn-beijing.aliyuncs.com";

    private String stsServer = NovateUtils.baseUrl + "api/frame/v1/aliOss/getSts";
    private String bucketName = "saenjiaoyu";
    private OSS oss;
    private List<String> mPicUrls;
    private String baseHttpUrl = "http://saenjiaoyu.oss-cn-beijing.aliyuncs.com/";

    public OSSUpDataUtils() {
        // 推荐使用OSSAuthCredentialsProvider。token过期可以及时更新。
        OSSCredentialProvider credentialProvider = new OSSAuthCredentialsProvider(stsServer);

        // 配置类如果不设置，会有默认配置。
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒。
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒。
        conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个。
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次。

        oss = new OSSClient(MyApplication.getApplication(), endpoint, credentialProvider);
        mPicUrls = new ArrayList<>();
    }



    public void upData(List<VPBean> list, int i, OssUpCallback mOssUpCallback) {

        if (list.size() <= i || TextUtils.isEmpty(list.get(i).getUrl())) {
            if (mOssUpCallback!=null){
                mOssUpCallback.onCallback(mPicUrls);
            }
            return ;
        }

        File file = new File(list.get(i).getUrl());
        if (file == null) {
            if (mOssUpCallback!=null){
                mOssUpCallback.onCallback(mPicUrls);
            }
            return ;
        }
        // 构造上传请求。
        PutObjectRequest put = new PutObjectRequest(bucketName, file.getName(), list.get(i).getUrl());

        // 异步上传时可以设置进度回调。
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
            }
        });

        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                Log.d("PutObject", "UploadSuccess");
                Log.d("ObjectKey", request.getObjectKey());
                Log.d("ETag", result.getETag());
                Log.d("RequestId", result.getRequestId());
                mPicUrls.add(baseHttpUrl + request.getObjectKey());
                upData(list, i + 1 ,mOssUpCallback);
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常。
                if (clientExcepion != null) {
                    // 本地异常，如网络异常等。
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常。
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
            }
        });
        // task.cancel(); // 可以取消任务。
        // task.waitUntilFinished(); // 等待任务完成。


    }


    public interface OssUpCallback {
        void onCallback(List<String> picList);

    }

}
