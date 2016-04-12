package com.colin.hunter;

import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import com.colin.hunter.base.BaseAppCompatActivity;

public class SplashActivity extends BaseAppCompatActivity {
    private LinearLayout linear_splash_content;
    private AlphaAnimation start_anima;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void initView() {
        this.linear_splash_content = (LinearLayout) this.findViewById(R.id.linear_splash_content);
    }

    @Override
    protected void initData() {
        start_anima = new AlphaAnimation(0.3f, 1.0f);
        start_anima.setDuration(2000);
        linear_splash_content.startAnimation(start_anima);
    }

    @Override
    protected void initListener() {
        start_anima.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(MainActivity.class, null, true);

            }
        });
    }


    @Override
    protected void getAsynData() {

    }
}
