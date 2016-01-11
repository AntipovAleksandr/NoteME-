package com.example.noteme.project.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.example.noteme.R;
import com.example.noteme.project.adapter.UtilsTheme;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{

    private RadioButton radioButton, radioButton1, radioButton2, radioButton3, radioButton4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UtilsTheme.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_setting);

        radioButton = (RadioButton) findViewById(R.id.radioButtonDarkBlue);
        radioButton1 = (RadioButton) findViewById(R.id.radioButtonGreen);
        radioButton2 = (RadioButton) findViewById(R.id.radioButtonBlue);
        radioButton3 = (RadioButton) findViewById(R.id.radioButtonSand);
        radioButton4 = (RadioButton) findViewById(R.id.radioButtonWhite);

        radioButton.setOnClickListener(this);
        radioButton1.setOnClickListener(this);
        radioButton2.setOnClickListener(this);
        radioButton3.setOnClickListener(this);
        radioButton4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.radioButtonDarkBlue:
                UtilsTheme.chooseToTheme(this, UtilsTheme.THEME_DARK_BLUE);
                break;
            case R.id.radioButtonGreen:
                UtilsTheme.chooseToTheme(this, UtilsTheme.THEME_GREEN);
                break;
            case R.id.radioButtonBlue:
                UtilsTheme.chooseToTheme(this, UtilsTheme.THEME_BLUE);
                break;
            case R.id.radioButtonSand:
                UtilsTheme.chooseToTheme(this, UtilsTheme.THEME_SAND);
                break;
            case R.id.radioButtonWhite:
                UtilsTheme.chooseToTheme(this, UtilsTheme.THEME_WHITE);
                break;
        }

    }
}
