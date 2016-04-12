package com.colin.hunter.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.colin.hunter.R;
import com.colin.hunter.adapter.RecyclerCandidateAdapter;
import com.colin.hunter.base.BaseFragment;
import com.colin.hunter.bean.CandidateBean;
import com.colin.hunter.bean.Education;
import com.colin.hunter.bean.Person;
import com.colin.hunter.bean.WorkExperience;
import com.colin.hunter.data.Constants;
import com.colin.hunter.help.LogHelp;
import com.colin.hunter.view.refreshrecycler.ProgressStyle;
import com.colin.hunter.view.refreshrecycler.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CandidateListFragment extends BaseFragment {
    private static final String FRAGMENT_ID = "fragment_id";
    private static final String TITLE = "title";
    private int fragment_id;
    private String title;
    private List<CandidateBean> candidateBeanList = null;
    private RecyclerCandidateAdapter recyclerCandidateAdapter = null;


    private XRecyclerView recycler_fragment_item_candidate;
    private Handler mHandler = new Handler();

    public CandidateListFragment() {
    }

    public static CandidateListFragment newInstance(int fragment_id, String title) {
        CandidateListFragment fragment = new CandidateListFragment();
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
        view = inflater.inflate(R.layout.fragment_list_candidate, container, false);
        return view;
    }

    @Override
    protected void initView() {
        recycler_fragment_item_candidate = (XRecyclerView) this.getView().findViewById(R.id.recycler_fragment_item_candidate);
        initRecylerView();
    }

    private void initRecylerView() {
        if (candidateBeanList == null) {
            candidateBeanList = new ArrayList<>();
        }
        if (recyclerCandidateAdapter == null) {
            if (fragment_id == Constants.FRAGMENT_CANDIDATE_RECOMMEND_STATE_LIST_ID_0) {
                recyclerCandidateAdapter = new RecyclerCandidateAdapter(activity, fragment_id, candidateBeanList, mListener);
            } else {
                recyclerCandidateAdapter = new RecyclerCandidateAdapter(fragment_id, candidateBeanList, mListener);
            }
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_fragment_item_candidate.setLayoutManager(layoutManager);
        recycler_fragment_item_candidate.setRefreshProgressStyle(ProgressStyle.Pacman);
        recycler_fragment_item_candidate.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        recycler_fragment_item_candidate.setAdapter(recyclerCandidateAdapter);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        recycler_fragment_item_candidate.setLoadingListener(new XRecyclerView.LoadingListener() {
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
        CandidateBean candidateBean = null;
        Person person = null;
        List<CandidateBean> candidateBeans = new ArrayList<>();
        candidateBeans.clear();
        for (int i = 0; i < Constants.LOAD_DATA_COUNT; i++) {
            candidateBean = new CandidateBean();
            int id = Constants.LOAD_DATA_COUNT * page + i;
            Education education1 = new Education(0, "第一学历", 0, "复旦大学");
            Education education2 = new Education(1, "最高学历", 1, "北京大学");
            List<Education> educationList = new ArrayList<Education>();
            educationList.add(education1);
            educationList.add(education2);
            WorkExperience workExperience = new WorkExperience(0, "万科", "上海", 1.5f);
            WorkExperience workExperience1 = new WorkExperience(1, "万达", "上海", 1.5f);
            List<WorkExperience> workExperienceList = new ArrayList<WorkExperience>();
            workExperienceList.add(workExperience);
            workExperienceList.add(workExperience1);
            List<String> labelList = new ArrayList<>();
            labelList.add("好人");
            labelList.add("非常好人");
            labelList.add("非常非常好人");
            labelList.add("非常好人");
            labelList.add("非常非常好人");
            labelList.add("非常非常好人");
            labelList.add("好人");
            labelList.add("非常非常好人");
            person = new Person(id, "Colin", (id % 2), id, true, 555555.5f, 666666.6f, "现在职位", "期望职位", educationList, workExperienceList, "上海",
                    "上海", "好，非常好", labelList, Constants.image_url[i], true);
            candidateBean.setId(id);
            candidateBean.setState(i % 4);
            LogHelp.d("state=" + String.valueOf(i % 4));
            candidateBean.setPerson(person);
            candidateBean.setIntroduce(getResources().getString(R.string.text_item_candidate_introduce));
            candidateBeans.add(candidateBean);
        }
        // 刷新完成后调用，必须在UI线程中
        if (isRefresh) {
            recycler_fragment_item_candidate.refreshComplete();
            candidateBeanList.clear();
        } else {
            recycler_fragment_item_candidate.loadMoreComplete();
        }
        candidateBeanList.addAll(candidateBeans);
        recyclerCandidateAdapter.notifyDataSetChanged();
        setLoading(false);
    }


}
