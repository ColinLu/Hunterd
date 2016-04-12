package com.colin.hunter.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.colin.hunter.R;
import com.colin.hunter.adapter.MyFragmentPagerAdapter;
import com.colin.hunter.base.BaseFragment;
import com.colin.hunter.data.Constants;
import com.colin.hunter.effect.viewpager.ViewPagerEffectPageTransformer;
import com.colin.hunter.view.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CandidateFragment extends BaseFragment {
    private static final String FRAGMENT_ID = "fragment_id";
    private static final String TITLE = "title";
    private int fragment_id;
    private String title;
    private PagerSlidingTabStrip tabs;
    private ViewPager pager_candidate_content;
    private List<Fragment> fragmentList = null;
    private MyFragmentPagerAdapter myFragmentPagerAdapter = null;

    public CandidateFragment() {
    }

    public static CandidateFragment newInstance(int fragment_id, String title) {
        CandidateFragment fragment = new CandidateFragment();
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
        view = inflater.inflate(R.layout.fragment_candidate, container, false);
        return view;
    }

    @Override
    protected void initView() {
        this.tabs = (PagerSlidingTabStrip) this.getView().findViewById(R.id.tabs);
        pager_candidate_content = (ViewPager) getView().findViewById(R.id.pager_candidate_content);
        initViewPager();
    }

    private void initViewPager() {
        if (fragmentList == null) {
            fragmentList = new ArrayList<>();
        }
        fragmentList.clear();
        int length = Constants.CANDIDATE_TAB_TITLE.length;
        for (int i = 0; i < length; i++) {
            Fragment fragment = CandidateListFragment.newInstance(Constants.CANDIDATE_TAB_TITLE_ID[i], Constants.CANDIDATE_TAB_TITLE[i]);
            fragmentList.add(fragment);
        }

        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), fragmentList, Arrays.asList(Constants.CANDIDATE_TAB_TITLE));
        pager_candidate_content.setAdapter(myFragmentPagerAdapter);

        pager_candidate_content.setOffscreenPageLimit(fragmentList.size());
        //动画效果
        pager_candidate_content.setPageTransformer(false, new ViewPagerEffectPageTransformer(0));
        pager_candidate_content.setCurrentItem(0);
        tabs.setViewPager(pager_candidate_content);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void getAsynData() {

    }


}
