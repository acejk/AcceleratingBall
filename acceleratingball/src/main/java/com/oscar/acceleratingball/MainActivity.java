package com.oscar.acceleratingball;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.oscar.acceleratingball.service.FloatBallService;

public class MainActivity extends AppCompatActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mContext = this;

    }

    public void startFloatBall(View view) {
        Intent intent = new Intent(mContext, FloatBallService.class);
        startService(intent);
        finish();
    }
}
