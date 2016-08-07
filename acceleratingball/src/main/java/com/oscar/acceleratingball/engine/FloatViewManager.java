package com.oscar.acceleratingball.engine;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.WindowManager;

import com.oscar.acceleratingball.view.FloatCircleView;

/**
 * Created by Administrator on 2016/8/7 0007.
 */
public class FloatViewManager {
    private static FloatViewManager mInstance;

    private Context mContext;

    private WindowManager mWindomManager;

    private FloatCircleView mFloatCirCleView;

    private FloatViewManager(Context context){
        this.mContext = context;
        mWindomManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mFloatCirCleView = new FloatCircleView(context);
    };
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
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.width = mFloatCirCleView.mWidth;
        params.height = mFloatCirCleView.mHeight;
        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        params.x = 0;
        params.y = 0;
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.format = PixelFormat.RGBA_8888;
        mWindomManager.addView(mFloatCirCleView, params);
    }
}
