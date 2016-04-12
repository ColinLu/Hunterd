package com.colin.hunter.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.colin.hunter.LoginActivity;
import com.colin.hunter.R;
import com.colin.hunter.RegisterActivity;
import com.colin.hunter.UserActivity;
import com.colin.hunter.WebViewActivity;
import com.colin.hunter.base.BaseFragment;
import com.colin.hunter.view.pulltozoomscrollviewex.PullToZoomScrollViewEx;

public class MineFragment extends BaseFragment implements View.OnClickListener {
    private static final String FRAGMENT_ID = "fragment_id";
    private static final String TITLE = "title";
    private int fragment_id;
    private String title;

    private PullToZoomScrollViewEx pulltozoomscrollviewex_mine;

    View headView;
    View zoomView;
    View contentView;

    public MineFragment() {
    }

    public static MineFragment newInstance(int fragment_id, String title) {
        MineFragment fragment = new MineFragment();
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
        view = inflater.inflate(R.layout.fragment_mine, container, false);
        pulltozoomscrollviewex_mine = (PullToZoomScrollViewEx) view.findViewById(R.id.pulltozoomscrollviewex_mine);
        headView = LayoutInflater.from(activity).inflate(R.layout.layout_mine_head, container, false);
        zoomView = LayoutInflater.from(activity).inflate(R.layout.layout_mine_zoom, container, false);
        contentView = LayoutInflater.from(activity).inflate(R.layout.layout_mine_content, container, false);
        pulltozoomscrollviewex_mine.setHeaderView(headView);
        pulltozoomscrollviewex_mine.setZoomView(zoomView);
        pulltozoomscrollviewex_mine.setScrollContentView(contentView);
        return view;
    }

    @Override
    protected void initView() {
        headView.findViewById(R.id.button_mine_register).setOnClickListener(this);
        headView.findViewById(R.id.button_mine_login).setOnClickListener(this);

        contentView.findViewById(R.id.rippleview_mine_company).setOnClickListener(this);
        contentView.findViewById(R.id.rippleview_mine_collect).setOnClickListener(this);
        contentView.findViewById(R.id.rippleview_mine_phone).setOnClickListener(this);
        contentView.findViewById(R.id.rippleview_mine_about).setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_mine_login:
                startActivity(LoginActivity.class, null, false);
                break;
            case R.id.button_mine_register:
                startActivity(RegisterActivity.class, null, false);
                break;
            case R.id.rippleview_mine_company:
                startActivity(UserActivity.class, null, false);
                break;
            case R.id.rippleview_mine_collect:
                startActivity(UserActivity.class, null, false);
                break;
            case R.id.rippleview_mine_phone:
                startActivity(UserActivity.class, null, false);
                break;
            case R.id.rippleview_mine_about:
                Bundle bundle = new Bundle();
                bundle.putString("title", "about");
                bundle.putString("url", "file:///android_asset/about/about.html");
                startActivity(WebViewActivity.class, bundle, false);
                break;
        }
    }
}
