package com.dtechmonkey.d_techmonkey;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.os.Handler;
import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {
    private static long DELAY=8000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //remove title
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_splash_screen);
        //create timer
        //Timer runSplash=new Timer();

        //create timer task
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, Language.class);
                startActivity(i);
                finish();
            }
        },DELAY);
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
