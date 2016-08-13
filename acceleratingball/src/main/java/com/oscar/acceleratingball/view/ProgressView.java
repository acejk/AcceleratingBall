package com.oscar.acceleratingball.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2016/8/13 0013.
 */
public class ProgressView extends View {
    private int mWidth = 200;
    private int mHeight = 200;

    private Paint mCiclePaint;
    private Paint mProgressPaint;
    private Paint mTextPaint;

    private Bitmap mBitmap;
    private Canvas mBitmapCanvas;

    private Path mPath = new Path();

    private int mProgress = 80;
    private int mMax = 100;
    private int mCurrentProgress = 0;
    private int mCount = 50;


    private DoubleRunnble mDoubleRunnble = new DoubleRunnble();
    private SingleRunnable mSingleRunnble = new SingleRunnable();

    private boolean isSingleTap;

    private GestureDetector mDetector;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

        }
    };

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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mWidth, mHeight);
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
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(25);

        mBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        mBitmapCanvas = new Canvas(mBitmap);

        mDetector = new GestureDetector(new MyGestureDetectorListener());
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mDetector.onTouchEvent(event);
            }
        });
        setClickable(true);
    }

    class  MyGestureDetectorListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            startDoubleTapAnimation();

            return super.onDoubleTap(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            isSingleTap = true;
            mCurrentProgress = mProgress;
            startSingleTapAnimation();
            return super.onSingleTapConfirmed(e);
        }
    }

    private void startSingleTapAnimation() {
        mHandler.postDelayed(mSingleRunnble, 200);
    }

    class SingleRunnable implements Runnable {
        @Override
        public void run() {
            mCount--;
            if(mCount >= 0) {
                invalidate();
                mHandler.postDelayed(mSingleRunnble, 200);
            } else {
                mHandler.removeCallbacks(mSingleRunnble);
                mCount = 50;
            }
        }
    }

    /**
     * 双击动画
     */
    private void startDoubleTapAnimation() {
        mHandler.postDelayed(mDoubleRunnble, 50);

    }

    class DoubleRunnble implements Runnable {

        @Override
        public void run() {
            mCurrentProgress++;
            if(mCurrentProgress <= mProgress) {
                invalidate();//重画
                mHandler.postDelayed(mDoubleRunnble, 50);
            } else {
                mHandler.removeCallbacks(mDoubleRunnble);
                mCurrentProgress = 0;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mBitmapCanvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 2, mCiclePaint);
        mPath.reset();
        float y = (1 - (float)mCurrentProgress / mMax) * mHeight;
        mPath.moveTo(mWidth, y);
        mPath.lineTo(mWidth, mHeight);
        mPath.lineTo(0, mHeight);
        mPath.lineTo(0, y);
        if(!isSingleTap) {
            float d = (1 - ((float)mCurrentProgress / mProgress)) * 10;
            for(int i=0; i<5; i++) {
                mPath.rQuadTo(10, -d, 20, 0);
                mPath.rQuadTo(10, d, 20, 0);
            }
        } else {
            float d = ((float)mCount / 50) * 10;
            if(mCount % 2 == 0) {
                for(int i=0; i<5; i++) {
                    mPath.rQuadTo(20, -d, 40, 0);
                    mPath.rQuadTo(20, d, 40, 0);
                }
            } else {
                for(int i=0; i<5; i++) {
                    mPath.rQuadTo(20, d, 40, 0);
                    mPath.rQuadTo(20, -d, 40, 0);

                }
            }
        }


        mPath.close();
        mBitmapCanvas.drawPath(mPath, mProgressPaint);

        String text = (int) (((float)mCurrentProgress / mMax) * 100) + "%";
        float textWidth = mTextPaint.measureText(text);
        Paint.FontMetrics metrics = mTextPaint.getFontMetrics();
        float baseLine = mHeight / 2 - (metrics.ascent + metrics.descent) / 2;
        mBitmapCanvas.drawText(text, mWidth / 2 - textWidth / 2, baseLine, mTextPaint);
        canvas.drawBitmap(mBitmap, 0, 0, null);
    }
}
