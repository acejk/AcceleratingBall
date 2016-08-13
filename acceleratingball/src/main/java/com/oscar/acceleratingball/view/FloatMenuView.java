package com.oscar.acceleratingball.view;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import com.oscar.acceleratingball.R;
import com.oscar.acceleratingball.engine.FloatViewManager;

/**
 * Created by Administrator on 2016/8/13 0013.
 */
public class FloatMenuView extends LinearLayout {
    private LinearLayout mll;
    private TranslateAnimation mAnimation;
    public FloatMenuView(final Context context) {
        super(context);
        View root = View.inflate(getContext(), R.layout.layout_menu, null);
        mll = (LinearLayout) root.findViewById(R.id.ll);
        mAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1.0f);
        mAnimation.setDuration(500);
        mAnimation.setFillAfter(true);
        mll.setAnimation(mAnimation);
        root.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                FloatViewManager floatViewManager = FloatViewManager.getInstance(context);
                floatViewManager.hideFloatMenuView();
                floatViewManager.showFloatCircle();
                return false;
            }
        });
        addView(root);
    }

    public void startAnimation() {
        mAnimation.start();
    }
}
