package com.oscar.acceleratingball.engine;

import android.content.Context;
import android.view.WindowManager;

/**
 * Created by Administrator on 2016/8/7 0007.
 */
public class FloatViewManager {
    private static FloatViewManager mInstance;

    private Context mContext;

    private WindowManager mWindomManager;

    private FloatViewManager(Context context){
        this.mContext = context;
        mWindomManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
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
}
