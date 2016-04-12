package com.colin.hunter.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.colin.hunter.R;
import com.colin.hunter.adapter.RecyclerOrderAdapter;
import com.colin.hunter.base.BaseFragment;
import com.colin.hunter.bean.OrderBean;
import com.colin.hunter.bean.PositionBean;
import com.colin.hunter.data.Constants;
import com.colin.hunter.view.refreshrecycler.ProgressStyle;
import com.colin.hunter.view.refreshrecycler.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class OrderListFragment extends BaseFragment {
    private static final String FRAGMENT_ID = "fragment_id";
    private static final String TITLE = "title";
    private int fragment_id;
    private String title;
    private List<OrderBean> orderBeanList = null;
    private RecyclerOrderAdapter recyclerOrderAdapter = null;


    private XRecyclerView recycler_fragment_item_position;
    private Handler mHandler = new Handler();

    public OrderListFragment() {
    }

    public static OrderListFragment newInstance(int fragment_id, String title) {
        OrderListFragment fragment = new OrderListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(FRAGMENT_ID, fragment_id);
        bundle.putString(TITLE, title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fragment_id = getArguments().getInt(FRAGMENT_ID);
            title = getArguments().getString(TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_order, container, false);
        return view;
    }

    @Override
    protected void initView() {
        recycler_fragment_item_position = (XRecyclerView) this.getView().findViewById(R.id.recycler_fragment_item_position);
        initRecylerView();
    }

    private void initRecylerView() {
        if (orderBeanList == null) {
            orderBeanList = new ArrayList<>();
        }
        if (recyclerOrderAdapter == null) {
            recyclerOrderAdapter = new RecyclerOrderAdapter(orderBeanList, fragment_id, mListener);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_fragment_item_position.setLayoutManager(layoutManager);
        recycler_fragment_item_position.setRefreshProgressStyle(ProgressStyle.Pacman);
        recycler_fragment_item_position.setLoadingMoreProgressStyle(ProgressStyle.BallZigZag);
        recycler_fragment_item_position.setAdapter(recyclerOrderAdapter);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        recycler_fragment_item_position.setLoadingListener(new XRecyclerView.LoadingListener() {
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
        OrderBean orderBean = null;
        List<OrderBean> orderBeans = new ArrayList<>();
        orderBeans.clear();
        for (int i = 0; i < Constants.LOAD_DATA_COUNT; i++) {
            int id = page * Constants.LOAD_DATA_COUNT + i;
            boolean is_now = fragment_id == 0 ? true : false;
            orderBean = new OrderBean(id, new PositionBean(i, "工程总监", "工程总监", "上海万科", true, "总经理", 100.0f, 55.3f, "上海", i, true, 1, is_now));
            orderBeans.add(orderBean);
        }
        // 刷新完成后调用，必须在UI线程中
        if (isRefresh) {
            recycler_fragment_item_position.refreshComplete();
            orderBeanList.clear();
        } else {
            recycler_fragment_item_position.loadMoreComplete();
        }
        orderBeanList.addAll(orderBeans);
        recyclerOrderAdapter.notifyDataSetChanged();
        setLoading(false);
    }


}
