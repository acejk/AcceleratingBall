package com.oscar.acceleratingball.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/8/7 0007.
 */
public class FloatCircleView extends View {
    private int mWidth;
    private int mHeight;

    private String mHk;

    public FloatCircleView(Context context) {
        super(context);
    }

    public FloatCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FloatCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mWidth, mHeight);
    }
}
