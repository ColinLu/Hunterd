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
import com.colin.hunter.view.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderFragment extends BaseFragment {
    private static final String FRAGMENT_ID = "fragment_id";
    private static final String TITLE = "title";
    private int fragment_id;
    private String title;
    //    private TabLayout tablayout_position;
    private PagerSlidingTabStrip tabs;
    private ViewPager pager_order_content;
    private List<Fragment> fragmentList = null;
    private MyFragmentPagerAdapter myFragmentPagerAdapter = null;

    public OrderFragment() {
    }

    public static OrderFragment newInstance(int fragment_id, String title) {
        OrderFragment fragment = new OrderFragment();
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
        view = inflater.inflate(R.layout.fragment_order, container, false);
        return view;
    }

    @Override
    protected void initView() {
//        tablayout_position = (TabLayout) getView().findViewById(R.id.tablayout_position);
        this.tabs = (PagerSlidingTabStrip) this.getView().findViewById(R.id.tabs);
        pager_order_content = (ViewPager) getView().findViewById(R.id.pager_order_content);
        initViewPager();
    }

    private void initViewPager() {
        if (fragmentList == null) {
            fragmentList = new ArrayList<>();
        }
        fragmentList.clear();
        int length = Constants.ORDER_TAB_TITLE_ID.length;
        for (int i = 0; i < length; i++) {
            Fragment fragment = OrderListFragment.newInstance(Constants.ORDER_TAB_TITLE_ID[i], Constants.ORDER_TAB_TITLE[i]);
            fragmentList.add(fragment);
//            tablayout_position.addTab(tablayout_position.newTab().setText(Constants.ORDER_TAB_TITLE[i]));
        }

        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), fragmentList, Arrays.asList(Constants.ORDER_TAB_TITLE));
        pager_order_content.setAdapter(myFragmentPagerAdapter);

//        tablayout_position.setupWithViewPager(pager_order_content);//将TabLayout和ViewPager关联起来
//        tablayout_position.setTabsFromPagerAdapter(myFragmentPagerAdapter);//给Tabs设置适配器
        pager_order_content.setOffscreenPageLimit(fragmentList.size());
        //动画效果
        pager_order_content.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
//                transformPage(View page, float position)方法有两个参数，
//                page参数代表当前view 或 fragment，position参数就是它的位置的值。
//                滑动的时候，起始page和目标page的各自的transformPage()就会被同时触发调用。
//                一个page的position为0代表它处于中间，为1代表它完全处于右边，为－1代表它完全处于左边。
//                官方文档是这样说的，position是一个page相对于屏幕中心的位置。position的值跟随用户滑动page而变化。
//                当page填充屏幕完全可见的时候，它的position是0；page位于屏幕右边，它的position是1。
//                两个page同时滑动到一半的时候，左边page的position是－0.5，右边page的position是0.5。
//                （因为左右是对称的）所以，为了不考虑正负值，我们取position的绝对值：
                final float normalizedposition = Math.abs(Math.abs(position) - 1);
//                淡入淡出效果
                page.setAlpha(normalizedposition);
            }
        });
        pager_order_content.setCurrentItem(0);
        tabs.setViewPager(pager_order_content);
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
