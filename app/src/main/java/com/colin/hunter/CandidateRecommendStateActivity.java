package com.colin.hunter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.colin.hunter.adapter.MyFragmentPagerAdapter;
import com.colin.hunter.base.BaseAppCompatActivity;
import com.colin.hunter.base.BaseFragment;
import com.colin.hunter.bean.CandidateBean;
import com.colin.hunter.bean.OrderBean;
import com.colin.hunter.data.Constants;
import com.colin.hunter.effect.viewpager.ViewPagerEffectPageTransformer;
import com.colin.hunter.fragment.CandidateListFragment;
import com.colin.hunter.help.ToastHelp;
import com.colin.hunter.view.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 派单 候选人状态界面
 * <p>
 * //已推荐，推荐人才  按钮点击进来
 */
public class CandidateRecommendStateActivity extends BaseAppCompatActivity implements BaseFragment.OnFragmentListener {
    private int id;
    private PagerSlidingTabStrip tabs;
    private ViewPager pager_candidate_recommend_state;
    private List<Fragment> fragmentList = null;
    private MyFragmentPagerAdapter myFragmentPagerAdapter = null;
    private OrderBean orderBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_recommend_state);
    }

    @Override
    protected void initView() {
        getData();
        this.tabs = (PagerSlidingTabStrip) this.findViewById(R.id.tabs);
        this.pager_candidate_recommend_state = (ViewPager) this.findViewById(R.id.pager_candidate_recommend_state);
        initViewPager();
    }

    private void getData() {
        this.image_title_back.setVisibility(View.VISIBLE);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            orderBean = bundle.getParcelable("class");
            id = bundle.getInt("id", 0);
        }
        if (null != orderBean) {
            this.text_title.setText(orderBean.getPositionBean().getName() + "\t" + orderBean.getPositionBean().getWork_place());
            this.text_title.setVisibility(View.VISIBLE);
        }
        if (id == Constants.ACTIVITY_CANDIDATE_RECOMMMEND_STATE_ID_RECOMMEND_CANDIDATE) {//可以搜索，对某个职位进行推荐
            this.text_title_right_extra.setText("搜索");
            this.text_title_right_extra.setVisibility(View.VISIBLE);
        } else if (id == Constants.ACTIVITY_CANDIDATE_RECOMMMEND_STATE_ID_RECOMMEND) {//编辑推荐状态
            this.text_title_right.setVisibility(View.GONE);
        }
        this.text_title_right.setText("+");
        this.text_title_right.setVisibility(View.VISIBLE);
    }

    private void initViewPager() {
        if (fragmentList == null) {
            fragmentList = new ArrayList<>();
        }
        fragmentList.clear();
        int length = Constants.CANDIDATE_RECOMMEND_STATE_TAB_TITLE.length;
        String[] array_titles = id == Constants.ACTIVITY_CANDIDATE_RECOMMMEND_STATE_ID_RECOMMEND ? Constants.CANDIDATE_RECOMMEND_STATE_TAB_TITLE : Constants.CANDIDATE_TAB_TITLE;
        int[] array_ids = id == Constants.ACTIVITY_CANDIDATE_RECOMMMEND_STATE_ID_RECOMMEND ? Constants.CANDIDATE_RECOMMEND_STATE_TAB_TITLE_ID :
                Constants.CANDIDATE_RECOMMEND_STATE_ID;
        for (int i = 0; i < length; i++) {
            Fragment fragment = CandidateListFragment.newInstance(array_ids[i], array_titles[i]);
            fragmentList.add(fragment);
        }

        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList, Arrays.asList(array_titles));
        pager_candidate_recommend_state.setAdapter(myFragmentPagerAdapter);

        pager_candidate_recommend_state.setOffscreenPageLimit(fragmentList.size());
        pager_candidate_recommend_state.setPageTransformer(false, new ViewPagerEffectPageTransformer(0));
        pager_candidate_recommend_state.setCurrentItem(0);
        tabs.setViewPager(pager_candidate_recommend_state);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        this.text_title_right_extra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CandidateRecommendStateActivity.this, "搜索", Toast.LENGTH_SHORT).show();
            }
        });
        this.text_title_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CandidateRecommendStateActivity.this, "暂未实现", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void getAsynData() {

    }

    @Override
    public void onFragmentClick(int fragment_id, int position, boolean b, Object object) {
        if (b) {
            if (fragment_id == Constants.FRAGMENT_CANDIDATE_LIST_RECOMMEND_RECORD) {//推荐记录
                CandidateBean candidateBean = (CandidateBean) object;
                Bundle bundle = new Bundle();
                bundle.putString("title", candidateBean.getPerson().getName() + "推荐记录");
                bundle.putParcelable("class", candidateBean);
                startActivity(RecommendRecordListActivity.class, bundle, false);
            } else if (fragment_id == Constants.FRAGMENT_CANDIDATE_LIST_INTERVIEW_RECORD) {
                ToastHelp.showToast(this, "暂未开通");
            } else if (fragment_id == Constants.FRAGMENT_CANDIDATE_LIST_VIEW) {
                CandidateBean candidateBean = (CandidateBean) object;
                Bundle bundle = new Bundle();
                bundle.putString("title", candidateBean.getPerson().getName());
                bundle.putParcelable("class", candidateBean);
                startActivity(CandidateDetailActivity.class, bundle, false);
            } else if (fragment_id == Constants.FRAGMENT_CANDIDATE_LIST_FEEDBACK_RECORD) {
                Bundle bundle = new Bundle();
                bundle.putString("title", "反馈记录");
                bundle.putInt("id", Constants.FEEDBACK_ID_HUNTER);
                startActivity(FeedbackRecordListActivity.class, bundle, false);
            } else if (fragment_id == Constants.FRAGMENT_CANDIDATE_LIST_RECOMMEND_OK) {
                Toast.makeText(CandidateRecommendStateActivity.this, "确认推荐", Toast.LENGTH_SHORT).show();
            } else if (fragment_id == Constants.FRAGMENT_CANDIDATE_LIST_RECOMMEND_STATE) {
                Toast.makeText(CandidateRecommendStateActivity.this, "推荐状态选择", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
