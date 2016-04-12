package com.colin.hunter.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.colin.hunter.R;
import com.colin.hunter.adapter.RecyclerMessageAdapter;
import com.colin.hunter.base.BaseFragment;
import com.colin.hunter.bean.MessageBean;
import com.colin.hunter.data.Constants;
import com.colin.hunter.view.refreshrecycler.ProgressStyle;
import com.colin.hunter.view.refreshrecycler.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MessageListFragment extends BaseFragment {
    private static final String FRAGMENT_ID = "fragment_id";
    private static final String TITLE = "title";
    private int fragment_id;
    private String title;
    private List<MessageBean> messageBeanList = null;
    private RecyclerMessageAdapter recyclerPersonAdapter = null;


    private XRecyclerView recycler_fragment_item_message;
    private Handler mHandler = new Handler();

    public MessageListFragment() {
    }

    public static MessageListFragment newInstance(int fragment_id, String title) {
        MessageListFragment fragment = new MessageListFragment();
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
        view = inflater.inflate(R.layout.fragment_list_message, container, false);
        return view;
    }

    @Override
    protected void initView() {
        recycler_fragment_item_message = (XRecyclerView) this.getView().findViewById(R.id.recycler_fragment_item_message);
        initRecylerView();
    }

    private void initRecylerView() {
        if (messageBeanList == null) {
            messageBeanList = new ArrayList<>();
        }
        if (recyclerPersonAdapter == null) {
            recyclerPersonAdapter = new RecyclerMessageAdapter(Constants.FRAGMENT_MAIN_ID_MESSAGE, messageBeanList, mListener);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_fragment_item_message.setLayoutManager(layoutManager);
        recycler_fragment_item_message.setRefreshProgressStyle(ProgressStyle.Pacman);
        recycler_fragment_item_message.setLoadingMoreProgressStyle(ProgressStyle.CubeTransition);
        recycler_fragment_item_message.setAdapter(recyclerPersonAdapter);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        recycler_fragment_item_message.setLoadingListener(new XRecyclerView.LoadingListener() {
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
        MessageBean message = null;
        List<MessageBean> messageBeans = new ArrayList<>();
        messageBeans.clear();
        for (int i = 0; i < Constants.LOAD_DATA_COUNT; i++) {
            int id = page * Constants.LOAD_DATA_COUNT + i;
            id += i;
            message = new MessageBean();
            message.setType(MessageBean.Type.valueOf(0));
            message.setId(id);
            message.setFrom("系统消息");
            message.setUser_head(Constants.image_url[i]);
            message.setContent("又新增了5位合适的候选人");
            message.setTime(System.currentTimeMillis());
            messageBeans.add(message);
        }
        // 刷新完成后调用，必须在UI线程中
        if (isRefresh) {
            recycler_fragment_item_message.refreshComplete();
            messageBeanList.clear();
        } else {
            recycler_fragment_item_message.loadMoreComplete();
        }
        messageBeanList.addAll(messageBeans);
        recyclerPersonAdapter.notifyDataSetChanged();
        setLoading(false);
    }


}
