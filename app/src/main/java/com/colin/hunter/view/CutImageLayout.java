package com.colin.hunter.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.RelativeLayout;

public class CutImageLayout extends RelativeLayout {
    private GestureDetectorImageView mGestureDetectorImageView;
    private MyCanvasView myCanvasView;
    private int mHorizontalPadding = 20;

    public CutImageLayout(Context context) {
        super(context, null);
    }

    public CutImageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mGestureDetectorImageView = new GestureDetectorImageView(context, attrs);
        myCanvasView = new MyCanvasView(context, attrs);

        android.view.ViewGroup.LayoutParams layoutParams = new LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT);

        this.addView(mGestureDetectorImageView, layoutParams);
        this.addView(myCanvasView, layoutParams);

        // 计算padding的px
        mHorizontalPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mHorizontalPadding,
                getResources().getDisplayMetrics());
        mHorizontalPadding = px2dip(context, mHorizontalPadding);
        myCanvasView.setHorizontalPadding(mHorizontalPadding);
        mGestureDetectorImageView.setHorizontalPadding(mHorizontalPadding);
    }

    public CutImageLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 对外公布设置边距的方法,单位为dp
     *
     * @param mHorizontalPadding
     */
    public void setHorizontalPadding(int mHorizontalPadding) {
        this.mHorizontalPadding = mHorizontalPadding;
    }

    /**
     * 剪切长方形图片
     *
     * @return
     */
    public Bitmap cutRectangle() {
        return mGestureDetectorImageView.cutRectangle();
    }

    /**
     * 剪切方形图片
     *
     * @return
     */
    public Bitmap cutSquare() {
        return mGestureDetectorImageView.cutSquare();
    }

    /**
     * 剪切圆形图片
     *
     * @return
     */
    public Bitmap cutImageCircle() {
        return mGestureDetectorImageView.cutCircle();
    }

    public void setBitmap(Bitmap bitmap) {
        mGestureDetectorImageView.setImageBitmap(bitmap);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
