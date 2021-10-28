package com.jiangjun.library.api;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.tamic.novate.BaseSubscriber;
import com.tamic.novate.util.NetworkUtil;

import java.lang.ref.WeakReference;

/**
 * Created by jiangjun on 2019/8/28 18:49
 */
public abstract class FileBaseSubscriber<T> extends BaseSubscriber<T> {


    private Context context;
    private Handler handler;

    public static final int DIALOG_SHOW = 0;
    public static final int DIALOG_DIMISS = 1;
    public static final int DIALOG_LENGTH = 1000;
    private Runnable runnable;

    public FileBaseSubscriber(Context context) {
        super(context);
        this.context = context;
        try {

            if (handler == null) {
                handler = new MyHandler(this, context);
            }
        } catch (Exception e) {

        }


    }


    @Override
    public void onStart() {
        super.onStart();
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


    @Override
    public void onCompleted() {
        super.onCompleted();

        try {
            if (handler != null) {
                handler.sendEmptyMessage(DIALOG_DIMISS);
                handler.removeCallbacks(runnable);
                runnable = null;
            }

        } catch (Exception e) {
        }
    }


    public static class MyHandler extends Handler {
        WeakReference<FileBaseSubscriber> weakReference;
        public ProgressDialog progress;
        private Context context;

        public MyHandler(FileBaseSubscriber activity, Context context) {
            try {
                this.context = context;
                weakReference = new WeakReference<FileBaseSubscriber>(activity);
                if (progress == null) {
                    progress = new ProgressDialog(context);
                    progress.setMessage("正在拼命加载中....");

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

