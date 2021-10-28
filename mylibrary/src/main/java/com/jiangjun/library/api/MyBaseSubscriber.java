package com.jiangjun.library.api;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jiangjun.library.utils.ButtonUtils;
import com.jiangjun.library.utils.RangerEvent;
import com.jiangjun.library.widget.LoadingDialog;
import com.tamic.novate.BaseSubscriber;
import com.tamic.novate.Throwable;
import com.tamic.novate.exception.NovateException;
import com.tamic.novate.util.LogWraper;
import com.tamic.novate.util.NetworkUtil;
import com.tamic.novate.util.ReflectionUtil;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;

import static com.tamic.novate.Novate.TAG;

/**
 * Created by LeBron James on 2018/8/27.
 */

public class MyBaseSubscriber<T> extends BaseSubscriber<ResponseBody> {

    private NovateUtils.setRequestReturn<T> callBack;
    private String url;
    private boolean isShow;
    private Type finalNeedType;
    private Handler handler;
    private Runnable runnable;
    public static final int DIALOG_SHOW = 0;
    public static final int DIALOG_DIMISS = 1;
    public static final int DIALOG_LENGTH = 1000;


    public MyBaseSubscriber(Context context, String url, boolean isShow,NovateUtils.setRequestReturn callBack) {
        super(context);
        this.callBack = callBack;
        this.url = url;
        this.isShow = isShow;
        if (isShow){
            try {
                if (handler == null) {
                    handler = new MyHandler(this, context);
                }
            } catch (Exception e) {

            }
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        if (isShow){
            try {
                if (handler != null) {
                    if (runnable == null) {
                        runnable = new Runnable() {
                            @Override
                            public void run() {
                                handler.sendEmptyMessage(DIALOG_SHOW);
                            }
                        };
                    }

                    handler.postDelayed(runnable, DIALOG_LENGTH);
                }

            } catch (Exception e) {
            }

        }

        Type[] types = ReflectionUtil.getParameterizedTypeswithInterfaces(callBack);
        if (ReflectionUtil.methodHandler(types) == null || ReflectionUtil.methodHandler(types).size() == 0) {
            LogWraper.e(TAG, "callBack<T> 中T不合法: -->" + finalNeedType);
            throw new NullPointerException("callBack<T> 中T不合法");
        }
        finalNeedType = ReflectionUtil.methodHandler(types).get(0);


    }

    @Override
    public void onCompleted() {
        if (isShow){
            try {
                if (handler != null) {
                    handler.sendEmptyMessage(DIALOG_DIMISS);
                    handler.removeCallbacks(runnable);
                    runnable = null;
                }

            } catch (Exception e) {
            }
        }

    }

    @Override
    public void onError(Throwable e) {
        onFail(e);
    }

    @Override
    public void onNext(ResponseBody responseBody) {
        try {
            byte[] bytes = responseBody.bytes();
            String jsStr = new String(bytes);
            LogWraper.d("Novate", "url:"+url+"\nResponseBody:" + jsStr.trim());

            if (callBack != null) {
                try {

                    /**
                     * if need parse baseRespone<T> use ParentType, if parse T use childType . defult parse baseRespone<T>
                     *
                     *  callBack.onSuccee((T) JSON.parseArray(jsStr, (Class<Object>) finalNeedType));
                     *  Type finalNeedType = needChildType;
                     */
                    T dataResponse = null;
                    MyResponse response = new Gson().fromJson(jsStr, MyResponse.class);
                    if (response.getCode() == 200||response.getCode() == 0 ) {
                        dataResponse = (T) new Gson().fromJson(jsStr, ReflectionUtil.newInstance(finalNeedType).getClass());
                        callBack.onSuccee(dataResponse);
                    } else {

                        if (response.getCode() == -1) {
                            if (!ButtonUtils.isFastDoubleClick(1, 4000)) {
                                RangerEvent.getInstance().getEventBus().post(RangerEvent.RefreshData.obtain("1"));
                            }
                        }
                        onFail(new Throwable(new java.lang.Throwable(), response.getCode(), response.getMsg()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    onFail(NovateException.handleException(e));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            onFail(NovateException.handleException(e));
        }
    }

    private void onFail(Throwable e) {
        LogWraper.e(TAG, "URL=" + url + "<---------->" + e.getLocalizedMessage());
        if (callBack != null) {
            callBack.onError(e);
        }
    }

    private static class MyHandler extends Handler {
        WeakReference<MyBaseSubscriber> weakReference;
        public LoadingDialog progress;
        private Context context;

        public MyHandler(MyBaseSubscriber activity, Context context) {
            try {
                this.context = context;
                weakReference = new WeakReference(activity);
                if (progress == null) {
                    LoadingDialog.Builder loadBuilder=new LoadingDialog.Builder(context)
                            .setMessage("加载中...")
                            .setCancelable(true)
                            .setCancelOutside(true);
                    progress= loadBuilder.create();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (weakReference.get() != null) {

                switch (msg.what) {
                    case DIALOG_SHOW:
                        DialogShow();
                        break;
                    case DIALOG_DIMISS:
                        DialogDimiss();
                        break;
                }
                // update android ui


            }
        }

        private void DialogShow() {
            try {
                if (!NetworkUtil.isNetworkAvailable(context)) {
                    Toast.makeText(context, "似乎没有网络", Toast.LENGTH_SHORT).show();
                    if (weakReference.get() != null) {
                        weakReference.get().onCompleted();
                    }
                    return;
                }
                if (progress != null) {
                    if (progress.isShowing()) {
                        progress.dismiss();
                    }
                    if (context != null) {
                        progress.show();
                    }

                }
            } catch (Exception e) {

            }

        }

        private void DialogDimiss() {
            try {
                if (progress != null && progress.isShowing()) {
                    progress.dismiss();
                }
            } catch (Exception e) {

            }
        }
    }

}
