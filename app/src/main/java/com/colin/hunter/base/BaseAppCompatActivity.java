package com.colin.hunter.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colin.hunter.R;


public abstract class BaseAppCompatActivity extends AppCompatActivity {
    /**
     * 是否初始化控件
     */
    private boolean initviewed = false;
    /**
     * TitleBar控件
     */
    protected LinearLayout linear_top_title_bar;
    protected ImageView image_title_left;
    protected TextView text_title_left;
    protected ImageView image_title_back;
    protected ImageView image_title;
    protected TextView text_title;
    protected ImageView image_title_right;
    protected TextView text_title_right;
    protected ImageView image_title_right_extra;
    protected TextView text_title_right_extra;
    protected ImageView image_title_left_extra;
    protected TextView text_title_left_extra;
    private boolean isLoading = false;
    protected int page = 0;
    private int current_asyntask_page = -1;//当前异步任务页码

    /**
     * 对外公开;是否正在异步任务
     *
     * @return
     */
    public boolean isLoading() {
        return isLoading;
    }

    /**
     * 对外公开;是否正在异步任务
     */
    public void setLoading(boolean isLoading) {
        this.isLoading = isLoading;
        if (false) {
            current_asyntask_page = page;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication.getInstance().addActivity(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        if (!initviewed) {
            this.initTitleBar();
            this.initView();
            this.initData();
            this.initListener();
            initviewed = true;
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (initviewed && !isLoading() && page != current_asyntask_page) {
            getAsynData();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        setLoading(false);
        initviewed = false;
        page = 0;
        current_asyntask_page = -1;
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 拦截MENU按钮点击事件，让他无任何操作
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        closeActivity();
        super.onBackPressed();
    }

    /**
     * 关闭当前界面
     */
    protected final void closeActivity() {
        hideKeyword();
        this.finish();
        showCloseAnim();
    }

    private void initTitleBar() {
        this.linear_top_title_bar = (LinearLayout) this.findViewById(R.id.linear_top_title_bar);
        this.image_title_left = (ImageView) this.findViewById(R.id.image_title_left);
        this.text_title_left = (TextView) this.findViewById(R.id.text_title_left);
        this.image_title_back = (ImageView) this.findViewById(R.id.image_title_back);
        this.image_title = (ImageView) this.findViewById(R.id.image_title);
        this.text_title = (TextView) this.findViewById(R.id.text_title);
        this.image_title_right = (ImageView) this.findViewById(R.id.image_title_right);
        this.text_title_right = (TextView) this.findViewById(R.id.text_title_right);
        this.image_title_right_extra = (ImageView) this.findViewById(R.id.image_title_right_extra);
        this.text_title_right_extra = (TextView) this.findViewById(R.id.text_title_right_extra);
        this.image_title_left_extra = (ImageView) this.findViewById(R.id.image_title_left_extra);
        this.text_title_left_extra = (TextView) this.findViewById(R.id.text_title_right_extra);
        if (this.image_title_back != null) {
            this.image_title_back.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    /**
     * 初始化内容布局与行为，仅调用一次
     */
    protected abstract void initView();

    /**
     * 初始化控件上的数据，仅调用一次
     */
    protected abstract void initData();

    /**
     * 初始化控件的监听，仅调用一次
     */
    protected abstract void initListener();

    /**
     * 异步任务加载数据
     */
    protected abstract void getAsynData();

    /**
     * 界面跳转 是否保存当前界面 false 保存；true 不保存
     *
     * @param target
     * @param bundle
     * @param closeSelf
     */
    protected final void startActivity(Class<? extends Activity> target, Bundle bundle, boolean closeSelf) {
        hideKeyword();
        if (closeSelf) {
            this.finish();
        }
        Intent intent = new Intent(this, target);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        this.startActivity(intent);
        showOpenAnim();
    }

    /**
     * 界面跳转获取值
     *
     * @param target
     * @param requestCode
     * @param bundle
     */
    protected final void startActivityForResult(Class<? extends Activity> target, int requestCode, Bundle bundle) {
        hideKeyword();
        Intent intent = new Intent(this, target);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        this.startActivityForResult(intent, requestCode);
        showOpenAnim();
    }


    /**
     * 显示动画
     */
    protected void showOpenAnim() {
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    /**
     * 显示动画
     */
    protected void showCloseAnim() {
        this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    /**
     * 隐藏输入法面板
     */
    protected void hideKeyword() {
        InputMethodManager imm = ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE));
        if (imm != null && getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
