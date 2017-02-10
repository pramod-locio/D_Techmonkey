package com.techmonkey.topyaps;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {
    private static long DELAY=1000;
    private String selectedLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //remove title
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_splash_screen);

        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        selectedLanguage=sharedPreferences.getString("LANGUAGES","");
        Log.d("kr",selectedLanguage);

        //create timer
        //Timer runSplash=new Timer();
        //create timer task

        if(!selectedLanguage.equals("")){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            },DELAY);
        }
        else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreen.this, Language.class);
                    startActivity(intent);
                    finish();
                }
            },DELAY);
        }
        /*TimerTask showSplash=new TimerTask() {
            @Override
            public void run() {
                finish();
                startActivity(new Intent(SplashScreen.this,Language.class));
            }
        };
        //start timer
        runSplash.schedule(showSplash,DELAY);*/
    }
}
