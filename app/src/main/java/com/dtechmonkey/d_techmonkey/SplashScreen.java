package com.dtechmonkey.d_techmonkey;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.os.Handler;
import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {
    private static long DELAY=3000;
    private String selectedLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //remove title
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_splash_screen);

        SharedPreferences sharedPreferences=SplashScreen.this.getSharedPreferences(getString(R.string.LANG_FILE),MODE_PRIVATE);
        selectedLanguage=sharedPreferences.getString(getString(R.string.LANGUAGE),"");

        //create timer
        //Timer runSplash=new Timer();
        //create timer task

        if(!selectedLanguage.equals("")){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            },DELAY);
        }
        else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(SplashScreen.this, Language.class);
                    startActivity(i);
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
