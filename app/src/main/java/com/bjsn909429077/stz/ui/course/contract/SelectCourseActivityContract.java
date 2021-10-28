package com.bjsn909429077.stz.ui.course.contract;

import com.bjsn909429077.stz.bean.CoursePackageInfoBean;

public interface SelectCourseActivityContract {
    void coursePackageInfo(CoursePackageInfoBean.DataBean data);

    void receiveCoupon(CoursePackageInfoBean response);
}
