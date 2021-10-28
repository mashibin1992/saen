package com.bjsn909429077.stz.ui.video;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.CourseViewPagerAdapter;
import com.bjsn909429077.stz.ui.video.fragment.AnswerFragment;
import com.bjsn909429077.stz.ui.video.fragment.HandoutFragment;
import com.bjsn909429077.stz.ui.video.fragment.NoteFragment;
import com.bjsn909429077.stz.ui.video.fragment.VideoPlayerListFragment;
import com.google.android.material.tabs.TabLayout;
import com.jiangjun.library.ui.base.BaseActivity;
import com.jiangjun.library.utils.ToastUtils;
import com.plv.foundationsdk.ijk.player.media.IjkVideoView;
//import com.shuyu.gsyvideoplayer.GSYVideoManager;
//import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
//import com.shuyu.gsyvideoplayer.video.NormalGSYVideoPlayer;


import java.util.ArrayList;

import butterknife.BindView;

public class VideoPlaybackActivity extends BaseActivity {
    private static final String TAG = VideoPlaybackActivity.class.getSimpleName();
    public static final String PATH = "path";


//    private NormalGSYVideoPlayer videoPlayer;
//    private OrientationUtils orientationUtils;

    private String mFilePath;

    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.m_IjkVideoView)
    IjkVideoView IjkVideoView;
    @BindView(R.id.tool_bar)
    Toolbar tool_bar;
    @BindView(R.id.tv_cache)
    TextView tv_cache;
    @BindView(R.id.view_pager)
    ViewPager view_pager;
    private int recentlyStudyClassHourId;
    private int courseId;


    @Override
    protected int setLayoutRes() {
        return R.layout.activity_video_playback;
    }

    @Override
    protected boolean isShowTitleView() {
        return false;
    }

    @Override
    protected void findViews(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {
        if (getIntent() != null) {
            courseId = getIntent().getIntExtra("id", 0);
            recentlyStudyClassHourId = getIntent().getIntExtra("recentlyStudyClassHourId", 0);
        }
        initViewPager();
        mFilePath = "https://vd3.bdstatic.com/mda-mi8018h05dxd806u/cae_h264/1631145877370279323/" +
                "mda-mi8018h05dxd806u.mp4?v_from_s=hkapp-haokan-shunyi&amp;auth_key=1631260627" +
                "-0-0-e5adbf99d66817a77a9af56bab51d4df&amp;bcevod_channel=searchbox_feed&amp;pd=1&amp;pt=3&amp;abtest=";
        initVideo();
        initListener();

        IjkVideoView.setVideoPath(mFilePath);
        IjkVideoView.start();
    }

    private void initListener() {
        tool_bar.setNavigationOnClickListener(v -> finish());

        tv_cache.setOnClickListener(v -> {
            ToastUtils.Toast(mContext,"全部缓存");
        });

    }

    private void initViewPager() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("章节");
        strings.add("讲义");
        strings.add("笔记");
        strings.add("答疑");
        strings.add("下载");
        ArrayList<Fragment> fragments = new ArrayList<>();

        Bundle bundle = new Bundle();
        bundle.putString("isShowType", "0");
        bundle.putInt("courseId", courseId);
        fragments.add(VideoPlayerListFragment.getInstance(bundle));

        Bundle bundle3 = new Bundle();
        bundle3.putInt("courseId", courseId);
        fragments.add(HandoutFragment.getInstance(bundle3));

        Bundle bundle1 = new Bundle();
        bundle1.putInt("recentlyStudyClassHourId", recentlyStudyClassHourId);
        bundle1.putInt("id", courseId);
        fragments.add(NoteFragment.getInstance(bundle1));

        Bundle bundle4 = new Bundle();
        bundle4.putInt("courseId", courseId);
        bundle4.putInt("recentlyStudyClassHourId", recentlyStudyClassHourId);
        fragments.add(AnswerFragment.getInstance(bundle4));

        Bundle bundle2 = new Bundle();
        bundle2.putString("isShowType", "1");
        bundle2.putInt("courseId", courseId);
        fragments.add(VideoPlayerListFragment.getInstance(bundle2));

        CourseViewPagerAdapter courseViewPagerAdapter = new CourseViewPagerAdapter(strings, fragments, getSupportFragmentManager());
        view_pager.setAdapter(courseViewPagerAdapter);
        view_pager.setOffscreenPageLimit(strings.size());
        tablayout.setupWithViewPager(view_pager);
    }


    private void initVideo() {
//        videoPlayer = findViewById(R.id.video_player);
//        // 初始化Url
//        videoPlayer.setUp(mFilePath, true, "");
//        // 隐藏标题栏
//        videoPlayer.getTitleTextView().setVisibility(View.GONE);
//        videoPlayer.getBackButton().setVisibility(View.GONE);
//        // 屏幕旋转
//        orientationUtils = new OrientationUtils(this, videoPlayer);
//        // 设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
//        videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                orientationUtils.resolveByClick();
//            }
//        });
//        // 是否可以滑动调整
//        videoPlayer.setIsTouchWiget(true);
//        // 设置返回按键功能
//        videoPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
////        videoPlayer.startPlayLogic();
    }

    @Override
    protected void onPause() {
        super.onPause();
        IjkVideoView.pause();
//        videoPlayer.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        IjkVideoView.start();
//        videoPlayer.onVideoResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        IjkVideoView.release(true);
//        GSYVideoManager.releaseAllVideos();
//        if (orientationUtils != null)
//            orientationUtils.releaseListener();
    }

    public void onBackPressed() {
//        // 先返回正常状态
//        if (orientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
//            videoPlayer.getFullscreenButton().performClick();
//            return;
//        }
//        // 释放所有
//        videoPlayer.setVideoAllCallBack(null);
        super.onBackPressed();
    }
}