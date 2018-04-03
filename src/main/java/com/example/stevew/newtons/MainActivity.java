package com.example.stevew.newtons;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends Activity {

    Intent i;
    MediaPlayer player;
    public static boolean shouldPlay = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        player = MediaPlayer.create(this, R.raw.main2);
        player.setLooping(true);
        TextView title1 = (TextView)findViewById(R.id.title1);
        TextView title2 = (TextView)findViewById(R.id.title2);
        TextView title3 = (TextView)findViewById(R.id.title3);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "ronaldo.ttf");
        title1.setTypeface(custom_font);
        title2.setTypeface(custom_font);
        title3.setTypeface(custom_font);
        ImageView apple = (ImageView)findViewById(R.id.apple);
        TranslateAnimation animation = new TranslateAnimation(0, 0,
                -100, 100);
        animation.setDuration(2000);
        animation.setRepeatMode(2);
        animation.setRepeatCount(Animation.INFINITE);
        apple.startAnimation(animation);

        Button play = (Button)findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(MainActivity.this, GFXSurface.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(i);
                overridePendingTransition(R.animator.animation1, R.animator.animation2);
            }
        });

        Button high = (Button)findViewById(R.id.high);
        high.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(MainActivity.this, HighScore.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(i);
                overridePendingTransition(R.animator.animation1, R.animator.animation2);
            }
        });
        player.start();
    }

    @Override
    protected void onPause(){
        player.stop();
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
