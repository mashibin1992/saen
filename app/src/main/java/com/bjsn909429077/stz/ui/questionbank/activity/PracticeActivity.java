package com.bjsn909429077.stz.ui.questionbank.activity;

import android.content.Intent;
import android.os.Bundle;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.PracticeAdapter;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.PracticeBean;
import com.bjsn909429077.stz.bean.PracticeFirstBean;
import com.bjsn909429077.stz.bean.PracticeSecondBean;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseActivity;
import com.jiangjun.library.utils.CommonTitleView;
import com.jiangjun.library.utils.ToastUtils;
import com.tamic.novate.Throwable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 章节练习，专项练习，练习类的共同页面
 */
public class PracticeActivity extends BaseActivity {

    private CommonTitleView commonTitleView;
    private RecyclerView practice_rv;
    private PracticeAdapter practiceAdapter;
    private int type;
    private int secondId;

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_practice;
    }

    @Override
    protected boolean isShowTitleView() {
        return true;
    }

    @Override
    protected void findViews(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        commonTitleView = (CommonTitleView) findViewById(com.jiangjun.library.R.id.common_title);
        practice_rv = findViewById(R.id.practice_rv);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        type = intent.getIntExtra("type", 0);
        secondId = intent.getIntExtra("secondId", -1);
        commonTitleView.setTitle(title);
        initNetWorkData();
    }

    private void initNetWorkData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("secondId", secondId);
        map.put("type", type);//类型 1.章节 2.专项
        NovateUtils.getInstance().Post(mContext, BaseUrl.exerciseList, map, true,
                new NovateUtils.setRequestReturn<PracticeBean>() {

                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(PracticeActivity.this, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(PracticeBean response) {
                        if (response != null && response.getData() != null) {
                            practice_rv.setLayoutManager(new LinearLayoutManager(PracticeActivity.this));
                            practiceAdapter = new PracticeAdapter(type,PracticeActivity.this);
                            practice_rv.setAdapter(practiceAdapter);
                            practiceAdapter.setList(getEntity(response.getData()));
                        }
                    }
                });
    }

    /**
     * 初始化数据
     *
     * @return
     */
    private List<BaseNode> getEntity(List<PracticeBean.DataBean> dataBean) {
        List<BaseNode> list = new ArrayList<>();
        for (int i = 0; i < dataBean.size(); i++) {
            PracticeBean.DataBean dataBean1 = dataBean.get(i);
            List<BaseNode> secondNodeList = new ArrayList<>();
            for (int n = 0; n < dataBean1.getNodeList().size(); n++) {
                PracticeBean.DataBean.NodeListBean nodeListBean = dataBean1.getNodeList().get(n);
                PracticeSecondBean secondBean = new PracticeSecondBean();
                secondBean.setCompleteNumber(nodeListBean.getCompleteNumber());
                secondBean.setParentId(nodeListBean.getParentId());
                secondBean.setNodeId(nodeListBean.getNodeId());
                secondBean.setNodeName(nodeListBean.getNodeName());
                secondBean.setCountNumber(nodeListBean.getCountNumber());
                secondNodeList.add(secondBean);
            }
            PracticeFirstBean firstBean = new PracticeFirstBean();
            firstBean.setChapterId(dataBean1.getChapterId());
            firstBean.setChapterName(dataBean1.getChapterName());
            firstBean.setCompleteNumber(dataBean1.getCompleteNumber());
            firstBean.setCountNumber(dataBean1.getCountNumber());
            firstBean.setChildNode(secondNodeList);
            // 模拟 默认第0个是展开的
            firstBean.setExpanded(i == 0);
            list.add(firstBean);
        }
        return list;
    }
}