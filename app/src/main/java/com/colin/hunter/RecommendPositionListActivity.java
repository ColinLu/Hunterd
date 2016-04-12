package com.colin.hunter;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.colin.hunter.adapter.RecyclerPositionAdapter;
import com.colin.hunter.base.BaseAppCompatActivity;
import com.colin.hunter.bean.OrderBean;
import com.colin.hunter.bean.PositionBean;
import com.colin.hunter.data.Constants;
import com.colin.hunter.help.StringHelp;
import com.colin.hunter.myinterface.OnRecyclerClickListener;
import com.colin.hunter.view.refreshrecycler.ProgressStyle;
import com.colin.hunter.view.refreshrecycler.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 推荐职位
 */
public class RecommendPositionListActivity extends BaseAppCompatActivity {
    private String title;
    private OrderBean orderBean;
    private List<PositionBean> positionBeanList = null;
    private RecyclerPositionAdapter recyclerPositionAdapter = null;
    private XRecyclerView recycler_positionlist_content;
    private Handler mHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_position_list);
    }

    @Override
    protected void initView() {
        getData();
        recycler_positionlist_content = (XRecyclerView) this.findViewById(R.id.recycler_positionlist_content);
        initRecylerView();
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            title = bundle.getString("title");
//            orderBean = bundle.getParcelable("class");
        }
        if (!StringHelp.isEmpty(title)) {
            this.text_title.setText(title);
            this.text_title.setVisibility(View.VISIBLE);
        }
        this.image_title_back.setVisibility(View.VISIBLE);
    }

    private void initRecylerView() {
        if (positionBeanList == null) {
            positionBeanList = new ArrayList<>();
        }
        if (recyclerPositionAdapter == null) {
            recyclerPositionAdapter = new RecyclerPositionAdapter(positionBeanList);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_positionlist_content.setLayoutManager(layoutManager);
        recycler_positionlist_content.setRefreshProgressStyle(ProgressStyle.Pacman);
        recycler_positionlist_content.setLoadingMoreProgressStyle(ProgressStyle.BallZigZagDeflect);
        recycler_positionlist_content.setAdapter(recyclerPositionAdapter);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        recycler_positionlist_content.setLoadingListener(new XRecyclerView.LoadingListener() {
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

        recyclerPositionAdapter.setOnRecyclerClickListener(new OnRecyclerClickListener() {
            @Override
            public void onClick(int view_id, int position, boolean b, Object object) {
                if (b) {
                    PositionBean positionBean = (PositionBean) object;
                    Bundle bundle = new Bundle();
                    bundle.putString("title", "职位详情");
                    bundle.putParcelable("class", positionBean);
                    switch (view_id) {
                        case Constants.RECOMMEND_LIST_ITEM_CLICK_VIEW:
                            bundle.putBoolean("is_finish", false);
                            break;
                        case Constants.RECOMMEND_LIST_ITEM_CLICK_DETAIL:
                            bundle.putBoolean("is_finish", true);
                            break;
                        case Constants.RECOMMEND_LIST_ITEM_CLICK_RECOMMEND:
                            bundle.putBoolean("is_finish", false);
                            break;
                    }
                    startActivity(ReleasePositionActivity.class, bundle, false);
                }

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
        PositionBean positionBean = null;
        List<PositionBean> positionBeans = new ArrayList<>();
        positionBeans.clear();
        for (int i = 0; i < Constants.LOAD_DATA_COUNT; i++) {
            int id = page * Constants.LOAD_DATA_COUNT + i;
            positionBean = new PositionBean(id, "工程总监", "工程总监", "上海万科", true, "总经理", 100.0f, 55.3f, "上海", id, true, 1, true);
            positionBeans.add(positionBean);
        }

        // 刷新完成后调用，必须在UI线程中
        if (isRefresh) {
            recycler_positionlist_content.refreshComplete();
            positionBeanList.clear();
        } else {
            recycler_positionlist_content.loadMoreComplete();
        }
        positionBeanList.addAll(positionBeans);
        recyclerPositionAdapter.notifyDataSetChanged();
        setLoading(false);
    }
}
