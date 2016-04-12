package com.colin.hunter;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.colin.hunter.adapter.RecyclerRecommendAdapter;
import com.colin.hunter.base.BaseAppCompatActivity;
import com.colin.hunter.bean.CandidateBean;
import com.colin.hunter.bean.PositionBean;
import com.colin.hunter.bean.RecommendBean;
import com.colin.hunter.data.Constants;
import com.colin.hunter.help.StringHelp;

import java.util.ArrayList;
import java.util.List;

public class RecommendRecordListActivity extends BaseAppCompatActivity {
    private String title;
    private CandidateBean candidateBean;
    private List<RecommendBean> recommendBeanList = null;
    private RecyclerRecommendAdapter recyclerRecommendAdapter = null;
    private RecyclerView recycler_recommendlist_content;
    private TextView text_recommendlist_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_record_list);
    }

    @Override
    protected void initView() {
        getData();
        recycler_recommendlist_content = (RecyclerView) this.findViewById(R.id.recycler_recommendlist_content);
        initRecylerView();
        text_recommendlist_number = (TextView) this.findViewById(R.id.text_recommendlist_number);
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            title = bundle.getString("title");
            candidateBean = bundle.getParcelable("class");
        }
        if (!StringHelp.isEmpty(title)) {
            this.text_title.setText(title);
            this.text_title.setVisibility(View.VISIBLE);
        }
        this.image_title_back.setVisibility(View.VISIBLE);
    }

    private void initRecylerView() {
        if (recommendBeanList == null) {
            recommendBeanList = new ArrayList<>();
        }
        if (recyclerRecommendAdapter == null) {
            recyclerRecommendAdapter = new RecyclerRecommendAdapter(recommendBeanList);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_recommendlist_content.setLayoutManager(layoutManager);
        recycler_recommendlist_content.setAdapter(recyclerRecommendAdapter);

    }

    @Override
    protected void initData() {
        RecommendBean recommendBean = null;
        for (int i = 0; i < 30; i++) {
            int state = i % Constants.RECOMMEND_STATE.length;
            recommendBean = new RecommendBean(i, new PositionBean(i, "工程总监", "工程总监", "上海万科", true, "总经理", 100.0f, 55.3f, "上海", i, true, 1, true), state, System.currentTimeMillis());
            recommendBeanList.add(recommendBean);
        }
        recyclerRecommendAdapter.notifyDataSetChanged();
        text_recommendlist_number.setText("共推荐 30 个职位");
    }

    @Override
    protected void initListener() {
        ((Spinner) this.findViewById(R.id.spinner_recommendlist_state)).setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(RecommendRecordListActivity.this, getResources().getStringArray(R.array.recommend_record_state)[position], Toast.LENGTH_SHORT).show();
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
    }

    @Override
    protected void getAsynData() {

    }
}
