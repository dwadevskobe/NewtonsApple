package com.example.stevew.newtons;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HighScore extends Activity {

    int high;
    String highTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        SharedPreferences shared = getSharedPreferences("Highscore", Context.MODE_PRIVATE);
        SharedPreferences time = getSharedPreferences("Time", Context.MODE_PRIVATE);
        TextView high1 = (TextView)findViewById(R.id.high1);
        TextView high2 = (TextView)findViewById(R.id.high2);
        TextView high3 = (TextView)findViewById(R.id.high3);
        TextView time1 = (TextView)findViewById(R.id.time1);
        TextView time2 = (TextView)findViewById(R.id.time2);
        TextView time3 = (TextView)findViewById(R.id.time3);
        TextView title = (TextView)findViewById(R.id.title);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "ronaldo.ttf");
        Typeface title_font = Typeface.createFromAsset(getAssets(), "high.ttf");
        high1.setTypeface(custom_font);
        high2.setTypeface(custom_font);
        high3.setTypeface(custom_font);
        time1.setTypeface(custom_font);
        time2.setTypeface(custom_font);
        time3.setTypeface(custom_font);
        title.setTypeface(title_font);

        Button back = (Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HighScore.this,MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(i);
                overridePendingTransition(R.animator.animation1, R.animator.animation2);
            }
        });

            if(shared.contains("Highscore"))

            {
                high = shared.getInt("Highscore", 0);
                high1.setText("" + high);
            }

            if(shared.contains("Highscore2"))

            {
                high = shared.getInt("Highscore2", 0);
                high2.setText("" + high);
            }

            if(shared.contains("Highscore3"))

            {
                high = shared.getInt("Highscore3", 0);
                high3.setText("" + high);
            }

            if(time.contains("Time1"))

            {
                highTime = time.getString("Time1", null);
                time1.setText(highTime);
            }

            if(time.contains("Time2"))

            {
                highTime = time.getString("Time2", null);
                time2.setText(highTime);
            }

            if(time.contains("Time3"))

            {
                highTime = time.getString("Time3", null);
                time3.setText(highTime);
            }

        }


    }


