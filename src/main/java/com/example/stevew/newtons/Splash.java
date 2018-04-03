package com.example.stevew.newtons;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class Splash extends Activity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {

            public void run(){

                finish();
                startActivity(new Intent(Splash.this, MainActivity.class));
                overridePendingTransition(R.animator.animation1,R.animator.animation2);
            }

        },1500);

    }


}
