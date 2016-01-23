package com.example.noteme.project.adapter;

import android.app.Activity;
import android.content.Intent;

import com.example.noteme.R;


public class UtilsTheme {

    private static int sTheme;

    public static final int THEME_GREEN = 0;
    public static final int THEME_BLUE = 1;
    public static final int THEME_DARK_BLUE = 2;
    public static final int THEME_SAND = 3;
    public static final int THEME_WHITE = 4;



    public static void chooseToTheme (Activity activity, int theme){

        sTheme = theme;
        activity.finish();

        activity.startActivity(new Intent(activity, activity.getClass()));
    }

    public static void onActivityCreateSetTheme(Activity activity){

        switch (sTheme){
            default:
            case THEME_DARK_BLUE:
                activity.setTheme(R.style.TheeTheme);
                break;
            case THEME_GREEN:
                activity.setTheme(R.style.OneTheme);
                break;
            case THEME_BLUE:
                activity.setTheme(R.style.TwoTheme);
                break;
            case THEME_SAND:
                activity.setTheme(R.style.FourTheme);
                break;
            case THEME_WHITE:
                activity.setTheme(R.style.FiveTheme);
                break;
        }
    }
}
