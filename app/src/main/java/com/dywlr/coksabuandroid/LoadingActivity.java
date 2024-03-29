package com.dywlr.coksabuandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class LoadingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        startLoading();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("OnStop : " ,"로딩 액티비티 onStop");
    }

    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getBaseContext(), com.dywlr.coksabuandroid.MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}

