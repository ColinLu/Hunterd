package com.colin.hunter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.colin.hunter.adapter.MyFragmentPagerAdapter;
import com.colin.hunter.base.BaseAppCompatActivity;
import com.colin.hunter.base.BaseFragment;
import com.colin.hunter.bean.CandidateBean;
import com.colin.hunter.bean.OrderBean;
import com.colin.hunter.data.Constants;
import com.colin.hunter.fragment.CandidateFragment;
import com.colin.hunter.fragment.MessageListFragment;
import com.colin.hunter.fragment.MineFragment;
import com.colin.hunter.fragment.OrderFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseAppCompatActivity implements BaseFragment.OnFragmentListener {
    private ViewPager pager_main_content;
    private RadioGroup radio_group_items;
    private int current_position = -1;
    private List<Fragment> fragmentList = null;
    private MyFragmentPagerAdapter adapter = null;
    private SearchView action_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onDestroy() {
        pager_main_content = null;
        fragmentList = null;
        adapter = null;
        super.onDestroy();
    }

    @Override
    protected void initView() {
        initToolbar();
        this.pager_main_content = (ViewPager) this.findViewById(R.id.pager_main_content);
        this.radio_group_items = (RadioGroup) this.findViewById(R.id.radio_group_items);
        initViewPager();
    }

    private void initViewPager() {
        if (fragmentList == null) {
            fragmentList = new ArrayList<>();
        }
        fragmentList.clear();
        Fragment orderFragment = OrderFragment.newInstance(Constants.ORDER_TAB_TITLE_ID[0], Constants.ORDER_TAB_TITLE[0]);
        Fragment candidateFragment = CandidateFragment.newInstance(Constants.FRAGMENT_MAIN_ID_CANDIDATE, Constants.TAB_MAIN_FRAGMENT_TITLE[1]);
        Fragment messageFragment = MessageListFragment.newInstance(Constants.FRAGMENT_MAIN_ID_MESSAGE, Constants.TAB_MAIN_FRAGMENT_TITLE[2]);
        Fragment mineFragment = MineFragment.newInstance(Constants.FRAGMENT_MAIN_ID_MINE, Constants.TAB_MAIN_FRAGMENT_TITLE[3]);

        fragmentList.add(orderFragment);
        fragmentList.add(candidateFragment);
        fragmentList.add(messageFragment);
        fragmentList.add(mineFragment);

        if (adapter == null) {
            adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList, Arrays.asList(Constants.TAB_MAIN_FRAGMENT_TITLE));
        }
        pager_main_content.setAdapter(adapter);

        select(0);
    }

    private void select(int i) {
        if (current_position != i) {
            current_position = i;
            pager_main_content.setCurrentItem(current_position, false);
            //中间加了下划线
            ((RadioButton) radio_group_items.getChildAt(current_position * 2)).setChecked(true);
            String title = "";
            if (current_position == 0) {
                title = "我的" + Constants.TAB_MAIN_FRAGMENT_TITLE[i];
            } else if (current_position == 1) {
                title = "我的" + Constants.TAB_MAIN_FRAGMENT_TITLE[i] + "库";
            } else {
                title = Constants.TAB_MAIN_FRAGMENT_TITLE[i];
            }
            text_title.setText(title);

            if (current_position == 1) {
                this.text_title_right_extra.setText("搜索");
                this.text_title_right_extra.setVisibility(View.VISIBLE);
                this.text_title_right.setText("+");
                this.text_title_right.setVisibility(View.VISIBLE);
            } else {
                this.text_title_right_extra.setVisibility(View.INVISIBLE);
                this.text_title_right.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        图标
        toolbar.setLogo(R.drawable.ic_action_name);
//        标题  标题的文字需在setSupportActionBar之前，不然会无效
//        toolbar.setTitle(getResources().getString(R.string.app_name));
//        副标题
        // getSupportActionBar().setSubtitle("副标题");
        setSupportActionBar(toolbar);
        /* 这些通过ActionBar来设置也是一样的，注意要在setSupportActionBar(toolbar);之后，不然就报错了 */
        // getSupportActionBar().setTitle("标题");
        // getSupportActionBar().setSubtitle("副标题");
        // getSupportActionBar().setLogo(R.drawable.ic_launcher);

		/* 菜单的监听可以在toolbar里设置，也可以像ActionBar那样，通过Activity的onOptionsItemSelected回调方法来处理 */
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_settings:
                        Toast.makeText(MainActivity.this, MainActivity.this.getResources().getString(R.string.action_settings), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_search:
                        Toast.makeText(MainActivity.this, "ddd", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setVisibility(View.GONE);
        this.text_title.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        pager_main_content.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                select(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        this.radio_group_items.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int page = 0;
                if (checkedId == R.id.radio_title_order) {
                    page = 0;
                } else if (checkedId == R.id.radio_title_talent) {
                    page = 1;
                } else if (checkedId == R.id.radio_title_message) {
                    page = 2;
                } else if (checkedId == R.id.radio_title_mine) {
                    page = 3;
                }
                select(page);
            }
        });
        this.text_title_right_extra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "搜索", Toast.LENGTH_SHORT).show();
            }
        });
        this.text_title_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "暂未实现", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void getAsynData() {
        //需要一个异步任务  获取可接单数量，已接单数量，已结束数量，推荐人才数量等等
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//
//        MenuItem menuItem = menu.findItem(R.id.action_search);//在菜单中找到对应控件的item
//        action_search = (SearchView) MenuItemCompat.getActionView(menuItem);
//        MenuItemCompat.setOnActionExpandListener(menuItem, new MenuItemCompat.OnActionExpandListener() {
//            @Override
//            public boolean onMenuItemActionExpand(MenuItem item) {
//                //SearchView 打开
//                LogHelp.d("onCreateOptionsMenu:SearchView---onExpand");
//                return true;
//            }
//
//            @Override
//            public boolean onMenuItemActionCollapse(MenuItem item) {
//                //SearchView 关闭
//                LogHelp.d("onCreateOptionsMenu:SearchView---Collapse");
//                return true;
//            }
//        });
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        } else if (id == R.id.action_search) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > Constants.TIME_EXIT) {
                Toast.makeText(this, "在按一次退出", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                closeActivity();
            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_SEARCH) {
            Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onFragmentClick(int fragment_id, int position, boolean b, Object object) {
        if (b) {
            if (fragment_id == Constants.FRAGMENT_ORDER_LIST_ID_0) {//派单——>可接单——>Item点击
                OrderBean orderBean = (OrderBean) object;
                Bundle bundle = new Bundle();
                bundle.putString("title", "职位详情");
                bundle.putBoolean("is_finish", false);
                bundle.putParcelable("class", orderBean.getPositionBean());
                startActivity(ReleasePositionActivity.class, bundle, false);
            } else if (fragment_id == Constants.FRAGMENT_ORDER_LIST_ID_1) {//派单——>已接单——>Item点击
                OrderBean orderBean = (OrderBean) object;
                Bundle bundle = new Bundle();
                bundle.putString("title", "职位详情");
                bundle.putBoolean("is_finish", true);
                bundle.putParcelable("class", orderBean.getPositionBean());
                startActivity(ReleasePositionActivity.class, bundle, false);
            } else if (fragment_id == Constants.FRAGMENT_ORDER_LIST_ID_2) {//派单——>已结束——>Item点击
                OrderBean orderBean = (OrderBean) object;
                Bundle bundle = new Bundle();
                bundle.putString("title", "职位详情");
                bundle.putBoolean("is_finish", true);
                bundle.putParcelable("class", orderBean.getPositionBean());
                startActivity(ReleasePositionActivity.class, bundle, false);
            } else if (fragment_id == Constants.FRAGMENT_ORDER_ITEM_FEEDBACK) {//派单——>可接单/已接单/已结束——>Item——>反馈记录按钮点击
//                OrderBean orderBean = (OrderBean) object;
                Bundle bundle = new Bundle();
                bundle.putString("title", "反馈记录");
                bundle.putInt("id", Constants.FEEDBACK_ID_HR);
                startActivity(FeedbackRecordListActivity.class, bundle, false);
            } else if (fragment_id == Constants.FRAGMENT_ORDER_ITEM_RECOMMEND_STATE) {//派单——>可接单/已接单/已结束——>Item——>已推荐按钮点击
                OrderBean orderBean = (OrderBean) object;
                Bundle bundle = new Bundle();
                bundle.putInt("id", Constants.ACTIVITY_CANDIDATE_RECOMMMEND_STATE_ID_RECOMMEND);//编辑状态
                bundle.putParcelable("class", orderBean);
                startActivity(CandidateRecommendStateActivity.class, bundle, false);
            } else if (fragment_id == Constants.FRAGMENT_ORDER_ITEM_RECOMMEND) {//派单——>可接单/已接单/已结束——>Item——>推荐人才按钮点击
                OrderBean orderBean = (OrderBean) object;
                Bundle bundle = new Bundle();
                bundle.putInt("id", Constants.ACTIVITY_CANDIDATE_RECOMMMEND_STATE_ID_RECOMMEND_CANDIDATE);//可以搜索,进行推荐
                bundle.putParcelable("class", orderBean);
                startActivity(CandidateRecommendStateActivity.class, bundle, false);
            } else if (fragment_id == Constants.FRAGMENT_CANDIDATE_LIST_VIEW) {//人才——>推荐人才/过往人才——>Item点击：进入简历阅览
                CandidateBean candidateBean = (CandidateBean) object;
                Bundle bundle = new Bundle();
                bundle.putString("title", candidateBean.getPerson().getName());
                bundle.putParcelable("class", candidateBean);
                startActivity(CandidateDetailActivity.class, bundle, false);
            } else if (fragment_id == Constants.FRAGMENT_CANDIDATE_LIST_RECOMMEND_RECORD) {//人才——>推荐人才/过往人才——>Item点击——>推荐记录
                CandidateBean candidateBean = (CandidateBean) object;
                Bundle bundle = new Bundle();
                bundle.putString("title", candidateBean.getPerson().getName() + "推荐记录");
                bundle.putParcelable("class", candidateBean);
                startActivity(RecommendRecordListActivity.class, bundle, false);
            } else if (fragment_id == Constants.FRAGMENT_CANDIDATE_LIST_INTERVIEW_RECORD) {//人才——>推荐人才/过往人才——>Item点击——>访谈记录
                Toast.makeText(MainActivity.this, "暂未开通,敬请期待！", Toast.LENGTH_SHORT).show();
            } else if (fragment_id == Constants.FRAGMENT_CANDIDATE_LIST_RECOMMEND_POSITION) {//人才——>推荐人才/过往人才——>Item点击——>推荐职位
                CandidateBean candidateBean = (CandidateBean) object;
                Bundle bundle = new Bundle();
                bundle.putString("title", candidateBean.getPerson().getName() + "推荐职位");
                bundle.putParcelable("class", candidateBean);
                startActivity(RecommendPositionListActivity.class, bundle, false);
            } else if (fragment_id == Constants.FRAGMENT_CANDIDATE_LIST_RECOMMEND_POSITION_FINISH) {//人才——>推荐人才/过往人才——>Item点击——>已推荐职位
                CandidateBean candidateBean = (CandidateBean) object;
                Bundle bundle = new Bundle();
                bundle.putString("title", candidateBean.getPerson().getName() + "已推荐职位");
                bundle.putParcelable("class", candidateBean);
                startActivity(RecommendPositionListActivity.class, bundle, false);
            }
        }

    }

}
