package com.oscar.acceleratingball.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.oscar.acceleratingball.engine.FloatViewManager;

/**
 * Created by Administrator on 2016/8/7 0007.
 */
public class FloatBallService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        FloatViewManager floatViewManager = FloatViewManager.getInstance(this);
        floatViewManager.showFloatCircle();
        super.onCreate();
    }
}
