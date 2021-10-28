package com.bjsn909429077.stz.ui.course;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.core.text.HtmlCompat;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.CourseWareBean;
import com.bjsn909429077.stz.bean.DetailBean;
import com.blankj.utilcode.util.AppUtils;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseLazyLoadFragment;
import com.jiangjun.library.user.UserConfig;
import com.jiangjun.library.utils.SignUtil;
import com.jiangjun.library.utils.StringUtil;
import com.jiangjun.library.utils.SystemUtil;
import com.tamic.novate.Throwable;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class CourseInfoFragment extends BaseLazyLoadFragment {
    @BindView(R.id.tv_html)
    WebView tv_html;
    private String url = "";
    private int coursePackageId;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_course_info;
    }

    @SuppressLint("JavascriptInterface")
    @Override
    protected void init() {

    }

    @Override
    protected void loadData() {
        if (getArguments() != null) {
            coursePackageId = getArguments().getInt("coursePackageId", 0);
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("coursePackageId", coursePackageId);
        Log.d("sadfdsf", "loadData: " + coursePackageId);
        NovateUtils.getInstance().get2(mContext, BaseUrl.detail, map, true,
                new NovateUtils.setResponseBody<String>() {
                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onSuccee(String response) throws IOException {
                        Log.d("sfasfasfasf", "onSuccee: " + response);
                        setUrl(response);
                    }
                });

//        setUrl("http://139.9.141.68:8082/api/app/v1/h5/course/package/detail/" + coursePackageId);
    }

    @SuppressLint("JavascriptInterface")
    public void setUrl(String detail) {
        this.url = detail;
//        tv_html.loadUrl(url);
        tv_html.loadData(detail, "text/html", "UTF-8");
        tv_html.addJavascriptInterface(this, null);
        WebSettings settings = tv_html.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setAllowFileAccess(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setDefaultTextEncodingName("utf-8");

//        tv_html.setLayoutParams();

//        tv_html.add
//        tv_html.setText(Html.fromHtml(detail));
    }


    public static CourseInfoFragment getInstance(Bundle bundle) {
        CourseInfoFragment courseInfoFragment = new CourseInfoFragment();
        courseInfoFragment.setArguments(bundle);
        return courseInfoFragment;
    }
}
