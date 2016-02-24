package com.example.noteme.project.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.noteme.R;
import com.example.noteme.project.database.DataHandler;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        DataHandler.getInstance(this).open();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, ContactListActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}
