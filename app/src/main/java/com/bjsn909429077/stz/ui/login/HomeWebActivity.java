package com.bjsn909429077.stz.ui.login;

import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;

import com.bjsn909429077.stz.R;
import com.jiangjun.library.ui.base.BaseActivity;
import com.jiangjun.library.utils.RLog;

import java.io.Serializable;

public class HomeWebActivity extends BaseActivity {
    private WebView webView;
    private WebBean bean;


    @Override
    protected boolean isShowTitleView() {
        return true;
    }

    @Override
    protected void initTitleView() {
        super.initTitleView();
    }

    @Override
    protected void findViews(@Nullable Bundle savedInstanceState) {
        bean = (WebBean) getIntent().getSerializableExtra("bean");

        commonTitleView.setTitle(bean.title);


        webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setBlockNetworkImage(false);
        webSettings.setUseWideViewPort(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //这个方法解决重定向后网页无法返回的问题
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // 网页加载完成
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                super.onReceivedSslError(view, handler, error);
                handler.proceed();    //解决加载https报错问题
            }
        });

        RLog.e(TAG,bean.url);
        webView.loadUrl(bean.url);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_home_web;
    }


    public static void startActivity(Context context, WebBean bean) {
        Intent intent = new Intent(context, HomeWebActivity.class);
        intent.putExtra("bean", bean);
        context.startActivity(intent);
    }

    public static class WebBean implements Serializable {
        private String type;
        private String title;
        private String url;

        /**
         *
         * @param type 1:banner点击
         * @param title
         * @param url
         */
        public WebBean(String type, String title, String url) {
            this.type = type;
            this.title = title;
            this.url = url;
        }

        public String getType() {
            return type == null ? "" : type;
        }

        public WebBean setType(String type) {
            this.type = type;
            return this;
        }

        public String getTitle() {
            return title == null ? "" : title;
        }

        public WebBean setTitle(String title) {
            this.title = title;
            return this;
        }

        public String getUrl() {
            return url == null ? "" : url;
        }

        public WebBean setUrl(String url) {
            this.url = url;
            return this;
        }
    }
}
