package com.jiangjun.library.widget.dialog;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import com.youth.banner.util.LogUtils;

/**
 * Created by jiangjun on 2020/10/23 17:13
 */
public class BaseDialogLifecycleObserverAdapter implements LifecycleObserver {
    private final BaseDialogLifecycleObserver mObserver;
    private final LifecycleOwner mLifecycleOwner;

    public BaseDialogLifecycleObserverAdapter(LifecycleOwner lifecycleOwner, BaseDialogLifecycleObserver observer) {
        mLifecycleOwner = lifecycleOwner;
        mObserver = observer;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        LogUtils.i("onStart");
        mObserver.onStart(mLifecycleOwner);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        LogUtils.i("onStop");
        mObserver.onStop(mLifecycleOwner);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        LogUtils.i("onDestroy");
        mObserver.onDestroy(mLifecycleOwner);
    }

}
