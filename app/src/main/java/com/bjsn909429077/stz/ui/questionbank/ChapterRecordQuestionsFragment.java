package com.bjsn909429077.stz.ui.questionbank;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.PracticeAdapter;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.PracticeBean;
import com.bjsn909429077.stz.bean.PracticeFirstBean;
import com.bjsn909429077.stz.bean.PracticeSecondBean;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseLazyLoadFragment;
import com.jiangjun.library.utils.SharedPreferencesUtil;
import com.jiangjun.library.utils.ToastUtils;
import com.tamic.novate.Throwable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @Author: Ke.Chen
 * @Description:做题记录
 * @Date :2021/9/19 11:42
 **/
public class ChapterRecordQuestionsFragment extends BaseLazyLoadFragment {

    private RecyclerView chapter_record_rv;

    @Override
    protected int setLayoutId() {
        return R.layout.chapter_record_questions_fragment;
    }

    @Override
    protected void init() {
        chapter_record_rv = (RecyclerView) findViewById(R.id.chapter_record_rv);
    }

    @Override
    protected void loadData() {
        initNetWork();
    }

    private void initNetWork(){
        HashMap<String, Integer> map = new HashMap<>();
        map.put("secondId", (int) SharedPreferencesUtil.getData(getActivity(), "secondId", -1));
        NovateUtils.getInstance().Post(mContext, BaseUrl.recordChapterList, map, true,
                new NovateUtils.setRequestReturn<PracticeBean>() {

                    private PracticeAdapter practiceAdapter;

                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(getActivity(), throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(PracticeBean response) {
                        if (response != null && response.getData() != null) {
                            chapter_record_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                            practiceAdapter = new PracticeAdapter(R.layout.answer_item, getActivity());
                            chapter_record_rv.setAdapter(practiceAdapter);
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
