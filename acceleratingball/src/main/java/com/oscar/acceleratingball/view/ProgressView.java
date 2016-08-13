package com.oscar.acceleratingball.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/8/13 0013.
 */
public class ProgressView extends View {
    private int mWidth = 100;
    private int mHeight = 100;

    private Paint mCiclePaint;
    private Paint mProgressPaint;
    private Paint mTextPaint;

    private Bitmap mBitmap;
    private Canvas mBitmapCanvas;

    private Path mPath = new Path();

    private int mProgress = 20;
    private int mMax = 100;

    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mCiclePaint = new Paint();
        mCiclePaint.setAntiAlias(true);
        mCiclePaint.setColor(Color.argb(0xff, 0x3a, 0x8c, 0x6c));

        mProgressPaint = new Paint();
        mProgressPaint.setAntiAlias(true);
        mProgressPaint.setColor(Color.argb(0xff, 0x4e, 0xc9, 0x63));
        mProgressPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);;
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(25);

        mBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        mBitmapCanvas = new Canvas(mBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mBitmapCanvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 2, mCiclePaint);
        mPath.reset();
        float y = (1 - (float)mProgress / mMax) * mHeight;
        mPath.moveTo(mWidth, y);
        mPath.lineTo(mWidth, mHeight);
        mPath.lineTo(0, mHeight);
        mPath.lineTo(0, y);

        for(int i=0; i<3; i++) {
            mPath.rQuadTo(10, -10, 20, 0);
            mPath.rQuadTo(10, 10, 20, 0);
        }
        mPath.close();
        mBitmapCanvas.drawPath(mPath, mProgressPaint);

        String text = (int) (((float)mProgress / mMax) * 100) + "%";
        float textWidth = mTextPaint.measureText(text);
        Paint.FontMetrics metrics = mTextPaint.getFontMetrics();
        float baseLine = mHeight / 2 - (metrics.ascent + metrics.descent) / 2;
        mBitmapCanvas.drawText(text, mWidth / 2 - textWidth / 2, baseLine, mTextPaint);
        canvas.drawBitmap(mBitmap, 0, 0, null);
    }
}
