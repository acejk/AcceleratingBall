package com.oscar.acceleratingball.engine;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.oscar.acceleratingball.view.FloatCircleView;
import com.oscar.acceleratingball.view.FloatMenuView;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2016/8/7 0007.
 */
public class FloatViewManager {
    private static FloatViewManager mInstance;

    private Context mContext;

    private WindowManager mWindomManager;

    private FloatCircleView mFloatCirCleView;

    private WindowManager.LayoutParams mParams;

    //触摸点
    private float mStartX;
    private float mStartY;

    private float mX0;
    private float mY0;

    private FloatMenuView mFloatMenuView;

    private FloatViewManager(Context context){
        this.mContext = context;
        mWindomManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mFloatCirCleView = new FloatCircleView(context);

        mFloatCirCleView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mStartX = event.getRawX();
                        mStartY = event.getRawY();

                        mX0 = event.getRawX();
                        mY0 = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float x = event.getRawX();
                        float y = event.getRawY();

                        float dx = x - mStartX;
                        float dy = y - mStartY;
                        mParams.x += dx;
                        mParams.y += dy;
                        mFloatCirCleView.setDragState(true);
                        mWindomManager.updateViewLayout(mFloatCirCleView, mParams);
                        mStartX = x;
                        mStartY = y;
                        break;
                    case MotionEvent.ACTION_UP:
                        float x1 = event.getRawX();
                        if(x1 > getScreenWidth() / 2) {
                            mParams.x = getScreenWidth() - mFloatCirCleView.mWidth;
                        } else {
                            mParams.x = 0;
                        }
                        mFloatCirCleView.setDragState(false);
                        mWindomManager.updateViewLayout(mFloatCirCleView, mParams);
                        if(Math.abs(x1 - mX0) > 6) {
                            return true;
                        } else {
                            return false;
                        }
                }

                return false;
            }
        });

        mFloatCirCleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWindomManager.removeView(mFloatCirCleView);
                showFloatMenuView();
                mFloatMenuView.startAnimation();
            }
        });
        mFloatMenuView = new FloatMenuView(mContext);
    }

    private void showFloatMenuView() {
        WindowManager.LayoutParams Params = new WindowManager.LayoutParams();
        Params.width = getScreenWidth();
        Params.height = getScreenHeight() - getStatusHeight();
        Params.gravity = Gravity.BOTTOM | Gravity.LEFT;
        Params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        Params.x = 0;
        Params.y = 0;
        Params.type = WindowManager.LayoutParams.TYPE_PHONE;
        Params.format = PixelFormat.RGBA_8888;
        mWindomManager.addView(mFloatMenuView, Params);
    }

    public static FloatViewManager getInstance(Context context) {
        if(mInstance == null) {
            synchronized (FloatViewManager.class) {
                if(mInstance == null) {
                    mInstance = new FloatViewManager(context);
                }
            }
        }
        return mInstance;
    }

    public void showFloatCircle() {
        if(mParams == null) {
            mParams = new WindowManager.LayoutParams();
            mParams.width = mFloatCirCleView.mWidth;
            mParams.height = mFloatCirCleView.mHeight;
            mParams.gravity = Gravity.TOP | Gravity.LEFT;
            mParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
            mParams.x = 0;
            mParams.y = 0;
            mParams.type = WindowManager.LayoutParams.TYPE_PHONE;
            mParams.format = PixelFormat.RGBA_8888;
        }
        mWindomManager.addView(mFloatCirCleView, mParams);
    }

    public int getScreenWidth() {
        return mWindomManager.getDefaultDisplay().getWidth();
    }

    public int getScreenHeight() {
        return mWindomManager.getDefaultDisplay().getHeight();
    }

    public int getStatusHeight() {
        try {
            Class<?> c =  Class.forName("com.android.internal.R$dimen");
            Object o = c.newInstance();
            Field filed = c.getField("status_bar_height");
            int x = (int) filed.get(o);
            return mContext.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void hideFloatMenuView() {
        mWindomManager.removeView(mFloatMenuView);
    }
}
