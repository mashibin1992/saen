package com.bjsn909429077.stz.ui.course.contract;


import android.widget.ProgressBar;
import android.widget.TextView;

import com.bjsn909429077.stz.bean.CoursePackageInfoBean;

import java.util.ArrayList;
import java.util.List;

public interface CourseArrangeContract<T> {
    /**
     * 三级列表回掉
     *
     * @param position
     */
    void courseArrangeItemCallback(String data, int position);

    /**
     * 播放按钮
     */
    void itemPlay(T data, int position);

    /**
     * 暂停
     */
    void itemSpend(T data, int position);

    /**
     * 下载
     */

    void itemDownload(T data, TextView view, ProgressBar progressBar, int position);


    void itemLock(T s, int layoutPosition);
}
