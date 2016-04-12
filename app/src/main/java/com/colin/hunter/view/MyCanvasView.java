package com.colin.hunter.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class MyCanvasView extends View {
    /**
     * 水平方向与View的边距
     */
    private int mHorizontalPadding;
    /**
     * 垂直方向与View的边距
     */
    private int mVerticalPadding = 0;// ——屏幕顶部离截图区的高度
    /**
     * 边框的宽度 单位dp
     */
    private int mBorderWidth = 2;

    private Paint mPaint;
    private boolean isFirst = true;

    public MyCanvasView(Context context) {
        this(context, null);
    }

    public MyCanvasView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyCanvasView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mBorderWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mBorderWidth, getResources()
                .getDisplayMetrics());
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制边框
        mPaint.setColor(Color.parseColor("#FFFFFF"));
        mPaint.setStrokeWidth(mBorderWidth);
        mPaint.setStyle(Style.STROKE);
        // canvas.drawRect(left, top, right, bottom, paint);

        // 方形边框
//        if (isFirst && mVerticalPadding == 0) {
//            mVerticalPadding = (getHeight() - (getWidth() - 2 * mHorizontalPadding)) / 2;
//        }

        //圆形边框
//        if (isFirst && mVerticalPadding == 0) {
//            mVerticalPadding = (getHeight() - (getWidth() - 2 * mHorizontalPadding)) / 2;
//        }
//        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2 - mHorizontalPadding, mPaint);

        // 长方形边框4:3
        if (isFirst && mVerticalPadding == 0) {
            mVerticalPadding = (getHeight() - (((getWidth() - 2 * mHorizontalPadding) / 4) * 3)) / 2;
        }
        canvas.drawRect(mHorizontalPadding, mVerticalPadding, getWidth() - mHorizontalPadding, getHeight() - mVerticalPadding, mPaint);
        isFirst = false;
    }

    public void setHorizontalPadding(int mHorizontalPadding) {
        this.mHorizontalPadding = mHorizontalPadding;
    }
}
