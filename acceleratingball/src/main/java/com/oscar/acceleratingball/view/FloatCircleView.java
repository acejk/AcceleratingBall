package com.oscar.acceleratingball.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/8/7 0007.
 */
public class FloatCircleView extends View {
    public int mWidth = 150;//小球宽
    public int mHeight = 150;//小球高

    private Paint mCirclePaint;//绘制小球的画笔
    private Paint mTextPaint;//绘制文字的画笔

    private String mText = "50%";

    public FloatCircleView(Context context) {
        super(context);
        initPaint();

    }

    public FloatCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FloatCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    private void initPaint() {
        mCirclePaint = new Paint();
        mCirclePaint.setColor(Color.GRAY);
        mCirclePaint.setAntiAlias(true);

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(25);
        mTextPaint.setFakeBoldText(true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mWidth, mHeight);//测量小球的大小
    }

    /**
     * 绘制小球和文字
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 2, mCirclePaint);
        float textWidth = mTextPaint.measureText(mText);
        float x = mWidth / 2 - textWidth / 2;
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float dy = -(fontMetrics.descent + fontMetrics.ascent) / 2;
        float y = mHeight / 2 + dy;
        canvas.drawText(mText, x, y, mTextPaint);
    }
}
