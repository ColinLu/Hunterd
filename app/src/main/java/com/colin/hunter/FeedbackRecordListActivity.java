package com.colin.hunter;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.colin.hunter.adapter.RecyclerFeedbackAdapter;
import com.colin.hunter.base.BaseAppCompatActivity;
import com.colin.hunter.bean.FeedBackRecordBean;
import com.colin.hunter.data.Constants;
import com.colin.hunter.help.StringHelp;
import com.colin.hunter.view.refreshrecycler.ProgressStyle;
import com.colin.hunter.view.refreshrecycler.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FeedbackRecordListActivity extends BaseAppCompatActivity {

    private String title;
    private int id = 0;
    private List<FeedBackRecordBean> feedBackRecordBeanList = null;
    private RecyclerFeedbackAdapter recyclerOrderAdapter = null;
    private XRecyclerView recycler_feedbacklist_content;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_record_list);
    }

    @Override
    protected void initView() {
        getData();
        recycler_feedbacklist_content = (XRecyclerView) this.findViewById(R.id.recycler_feedbacklist_content);
        initRecylerView();
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            title = bundle.getString("title");
            id = bundle.getInt("id", 0);
        }
        if (!StringHelp.isEmpty(title)) {
            this.text_title.setText(title);
            this.text_title.setVisibility(View.VISIBLE);
        }
        this.image_title_back.setVisibility(View.VISIBLE);
    }

    private void initRecylerView() {
        if (feedBackRecordBeanList == null) {
            feedBackRecordBeanList = new ArrayList<>();
        }
        if (recyclerOrderAdapter == null) {
            recyclerOrderAdapter = new RecyclerFeedbackAdapter(feedBackRecordBeanList);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_feedbacklist_content.setLayoutManager(layoutManager);
        recycler_feedbacklist_content.setRefreshProgressStyle(ProgressStyle.Pacman);
        recycler_feedbacklist_content.setLoadingMoreProgressStyle(ProgressStyle.BallZigZagDeflect);
        recycler_feedbacklist_content.setAdapter(recyclerOrderAdapter);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        recycler_feedbacklist_content.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 0;
                new Thread(new MyRunnable(true)).start();
            }

            @Override
            public void onLoadMore() {
                ++page;
                new Thread(new MyRunnable(false)).start();
            }
        });
    }

    @Override
    protected void getAsynData() {
        LoadData(true);
    }

    class MyRunnable implements Runnable {

        boolean isRefresh;

        public MyRunnable(boolean isRefresh) {
            this.isRefresh = isRefresh;
        }

        @Override
        public void run() {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    LoadData(isRefresh);
                }
            }, 2000);
        }
    }


    private void LoadData(boolean isRefresh) {
        setLoading(true);
        FeedBackRecordBean feedBackRecordBean = null;
        List<FeedBackRecordBean> feedBackRecordBeans = new ArrayList<>();
        feedBackRecordBeans.clear();
        for (int i = 0; i < Constants.LOAD_DATA_COUNT; i++) {
            int feedback_id = page * Constants.LOAD_DATA_COUNT + i;
            String name = id == 0 ? "HR" : "Hunter";
            feedBackRecordBean = new FeedBackRecordBean(feedback_id, feedback_id, feedback_id, feedback_id, name, "candidate_name", "简历不合适", "候选人经验不足", System.currentTimeMillis());
            feedBackRecordBeans.add(feedBackRecordBean);
        }

        // 刷新完成后调用，必须在UI线程中
        if (isRefresh) {
            recycler_feedbacklist_content.refreshComplete();
            feedBackRecordBeanList.clear();
        } else {
            recycler_feedbacklist_content.loadMoreComplete();
        }
        feedBackRecordBeanList.addAll(feedBackRecordBeans);
        recyclerOrderAdapter.notifyDataSetChanged();
        setLoading(false);
    }
}