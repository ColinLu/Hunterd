package com.colin.hunter.effect.viewpager;

import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;

/**
 * Created by Administrator on 2016/3/16.
 */
public class ViewPagerEffectPageTransformer implements PageTransformer {
    //    effect =0 ;淡进淡出效果
    private int effect = 0;

    public ViewPagerEffectPageTransformer(int effect) {
        this.effect = effect;
    }

    /**
     * transformPage(View page, float position)方法有两个参数，
     * page参数代表当前view 或 fragment，position参数就是它的位置的值。
     * 滑动的时候，起始page和目标page的各自的transformPage()就会被同时触发调用。
     * 一个page的position为0代表它处于中间，为1代表它完全处于右边，为－1代表它完全处于左边。
     * 官方文档是这样说的，position是一个page相对于屏幕中心的位置。position的值跟随用户滑动page而变化。
     *
     * @param page
     * @param position
     */
    @Override
    public void transformPage(View page, float position) {
        if (effect == 0) {
            effectFade(page, position);
        }
    }

    /**
     * 淡进淡出效果
     * 当page填充屏幕完全可见的时候，它的position是0；page位于屏幕右边，它的position是1。
     * 两个page同时滑动到一半的时候，左边page的position是－0.5，右边page的position是0.5。
     * （因为左右是对称的）所以，为了不考虑正负值，我们取position的绝对值：
     *
     * @param position
     */
    private void effectFade(View page, float position) {
        final float normalizedposition = Math.abs(Math.abs(position) - 1);
        page.setAlpha(normalizedposition);
    }
}
