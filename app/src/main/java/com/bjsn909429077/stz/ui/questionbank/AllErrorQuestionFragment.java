package com.bjsn909429077.stz.ui.questionbank;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.AllErrorQuestionAdapter;
import com.bjsn909429077.stz.adapter.AnswerAdapter;
import com.bjsn909429077.stz.adapter.PracticeAdapter;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.AllErrorQuestionBean;
import com.bjsn909429077.stz.bean.AllErrorQuestionFirstBean;
import com.bjsn909429077.stz.bean.AllErrorQuestionSecondBean;
import com.bjsn909429077.stz.bean.AnswerListBean;
import com.bjsn909429077.stz.bean.PracticeFirstBean;
import com.bjsn909429077.stz.bean.PracticeSecondBean;
import com.bjsn909429077.stz.ui.questionbank.activity.AnswerActivity;
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
 * @Description:
 * @Date :2021/9/14 22:30
 **/
public class AllErrorQuestionFragment extends BaseLazyLoadFragment {

    private RecyclerView all_error_rv;
    private AllErrorQuestionAdapter allErrorQuestionAdapter;

    @Override
    protected int setLayoutId() {
        return R.layout.all_error_question_fragment;
    }

    @Override
    protected void init() {
        all_error_rv = (RecyclerView) findViewById(R.id.all_error_rv);
    }

    @Override
    protected void loadData() {
        initNetWork();
    }

    private void initNetWork(){
        HashMap<String, Integer> map = new HashMap<>();
        map.put("secondId", (int) SharedPreferencesUtil.getData(getActivity(), "secondId", -1));
        NovateUtils.getInstance().Post(mContext, BaseUrl.errorBookChapter, map, true,
                new NovateUtils.setRequestReturn<AllErrorQuestionBean>() {

                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(getActivity(), throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(AllErrorQuestionBean response) {
                        if (response != null && response.getData() != null) {
                            all_error_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                            allErrorQuestionAdapter = new AllErrorQuestionAdapter();
                            all_error_rv.setAdapter(allErrorQuestionAdapter);
                            allErrorQuestionAdapter.setList(getEntity(response));
                        }
                    }
                });
    }

    /**
     * 初始化数据
     *
     * @return
     */
    private ArrayList<BaseNode> getEntity(AllErrorQuestionBean allErrorQuestionBean) {
        ArrayList<BaseNode> list = new ArrayList<>();
        List<AllErrorQuestionBean.DataBean> data = allErrorQuestionBean.getData();
        for (int i = 0; i < data.size(); i++) {
            List<BaseNode> secondNodeList = new ArrayList<>();
            List<AllErrorQuestionBean.DataBean.NodeListBean> nodeList = allErrorQuestionBean.getData().get(i).getNodeList();
            for (int n = 0; n < nodeList.size(); n++) {
                AllErrorQuestionSecondBean secondBean = new AllErrorQuestionSecondBean();
                secondBean.setCompleteNumber(nodeList.get(n).getCompleteNumber());
                secondBean.setCountNumber(nodeList.get(n).getCountNumber());
                secondBean.setErrorNumber(nodeList.get(n).getErrorNumber());
                secondBean.setNodeId(nodeList.get(n).getNodeId());
                secondBean.setNodeName(nodeList.get(n).getNodeName());
                secondBean.setParentId(nodeList.get(n).getParentId());
                secondNodeList.add(secondBean);
            }
            AllErrorQuestionFirstBean firstBean = new AllErrorQuestionFirstBean();
            firstBean.setChapterId(data.get(i).getChapterId());
            firstBean.setChapterName(data.get(i).getChapterName());
            firstBean.setCompleteNumber(data.get(i).getCompleteNumber());
            firstBean.setCountNumber(data.get(i).getCountNumber());
            firstBean.setErrorNumber(data.get(i).getErrorNumber());
            firstBean.setChildNode(secondNodeList);
            // 模拟 默认第0个是展开的
            firstBean.setExpanded(i == 0);
            list.add(firstBean);
        }
        return list;
    }
}
