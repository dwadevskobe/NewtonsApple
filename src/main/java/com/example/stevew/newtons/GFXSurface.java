package com.example.stevew.newtons;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.os.Vibrator;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;



public class GFXSurface extends Activity implements OnTouchListener {

    Game ourSurfaceView;
    Random rand;
    float speed = 10;
    int offset = 700;
    int lives = 3;
    Items apple1,apple2,apple3,apple4,apple5,apple6,temp;
    List<Items> foodList;
    Bitmap face,face2,pauseButton,life,gapple;
    MediaPlayer sp,gameSound;
    int sound;
    SurfaceHolder holder;
    public static final int SCALE = 1000;
    Paint textPaint, resumePaint, pausePaint;
    Typeface font,baldur,ronaldo;
    int pauseWidth, pauseHeight, cWidth, cHeight;
    int resumeX, resumeY, quitX, quitY;
    float x,y;
    Rect pauseMenu, resumeBounds,quitBounds;
    Canvas pauseC,endC;
    boolean paused = false;
    boolean end = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rand = new Random();
        int randY = -rand.nextInt(SCALE);
        ourSurfaceView = new Game(this);
        ourSurfaceView.setOnTouchListener(this);

        apple1 = new Apple(this,0,randY-40);
        apple2 = new Apple(this,0,randY-SCALE);
        apple3 = new Apple(this,0,randY-SCALE*2);
        apple4 = new Apple(this,0,randY-SCALE*3);
        apple5 = new Apple(this,0,randY-SCALE*4);
        apple6 = new Green(this,0,randY-SCALE*5);
        foodList = new ArrayList<Items>();
        foodList.add(apple1);
        foodList.add(apple2);
        foodList.add(apple3);
        foodList.add(apple4);
        foodList.add(apple5);
        foodList.add(apple6);

        sp = MediaPlayer.create(this,R.raw.hit);
        gameSound = MediaPlayer.create(this, R.raw.main2);
        face = BitmapFactory.decodeResource(getResources(), R.drawable.face);
        face2 = BitmapFactory.decodeResource(getResources(), R.drawable.face2);
        pauseButton = BitmapFactory.decodeResource(getResources(),R.drawable.pause);
        life = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
        gapple = BitmapFactory.decodeResource(getResources(), R.drawable.green);

        textPaint = new Paint();
        textPaint.setARGB(255, 0, 255, 0);
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setTextSize(45);
        textPaint.setTypeface(font);
        resumePaint = new Paint();
        resumePaint.setARGB(255, 151, 255, 255);
        resumePaint.setTextAlign(Paint.Align.CENTER);
        resumePaint.setTextSize(120);
        resumePaint.setTypeface(ronaldo);
        pauseWidth = pauseButton.getWidth();
        pauseHeight = pauseButton.getHeight();
        pausePaint = new Paint();
        pausePaint.setARGB(160,128,128,128);
        resumeBounds = new Rect();
        quitBounds = new Rect();
        setContentView(ourSurfaceView);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    // method called when activity is paused
    @Override
    protected void onPause() {
        gameSound.stop();
        ourSurfaceView.pause();
        super.onPause();
    }

    // method called when activity is resumed
    @Override
    protected void onResume() {
        gameSound.setLooping(true);
        gameSound.start();
        paused = false;
        super.onResume();
        ourSurfaceView.resume();
    }

    @Override
    protected void onDestroy() {
        face = null;
        face2 = null;
        pauseButton=null;
        life = null;
        apple1.setNull();
        apple2.setNull();
        apple3.setNull();
        apple4.setNull();
        apple5.setNull();
        apple6.setNull();

        ourSurfaceView.destroy();
        super.onDestroy();

    }

    // when screen is touched
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        x = event.getX();
        y = event.getY();


        if ( !paused && x>0 && x<pauseWidth && y>0 && y<pauseHeight){
            Paused();
        }

        // resume button is pressed
        if ( paused && x>(cWidth/2 - resumeX/2)  && x<(cWidth/2 +resumeX/2)
             && y>(400-resumeY) && y<(400+resumeY) ){

            ourSurfaceView.resume();
            paused = false;
            pauseMenu = null;
        }

        // quit button is pressed
        if ( (paused || end) && x>(cWidth/2 - quitX/2) && x<(cWidth/2 + quitX/2)
            && y>(cHeight-400-quitY) && y<(cHeight-400+quitY) ){
            Intent i = new Intent(this,MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(i);
            overridePendingTransition(R.animator.animation1, R.animator.animation2);
        }

        // play again button is pressed, restart game
        if ( end && x>(cWidth/2 - resumeX/2)  && x<(cWidth/2 +resumeX/2)
                && y>(400-resumeY) && y<(400+resumeY) ){
            recreate();
        }

        return true;
    }

    private void Paused(){

        ourSurfaceView.pause();
        pauseC =holder.lockCanvas();
        cWidth = pauseC.getWidth();
        cHeight = pauseC.getHeight();
        pauseMenu = new Rect(100,100,cWidth-100,cHeight-100);
        pauseC.drawRect(pauseMenu, pausePaint);
        pauseC.drawText("Resume", cWidth/2, 400, resumePaint);
        pauseC.drawText("Quit",cWidth/2,cHeight-400,resumePaint);
        holder.unlockCanvasAndPost(pauseC);
        resumePaint.getTextBounds("Resume", 0, "Resume".length(), resumeBounds);
        resumeX = resumeBounds.width();
        resumeY = resumeBounds.height();
        resumePaint.getTextBounds("Quit",0,"Quit".length(),quitBounds);
        quitX = quitBounds.width();
        quitY = quitBounds.height();
        paused = true;
    }


    public class Game extends SurfaceView implements Runnable,SensorEventListener {

        Thread thread = null;
        boolean running = false;
        float pX,pY,sensorX,sensorY,sensorZ;
        float newX;
        SensorManager sm;
        Sensor sensor;
        public String scoreString, greenString;
        public int score = 0;
        public int greens = 0;
        Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.back);
        Bitmap resizedBack;
        Display display = getWindowManager().getDefaultDisplay();
        Vibrator v = (Vibrator)this.getContext().getSystemService(Context.VIBRATOR_SERVICE);
        int width = display.getWidth();
        int height = display.getHeight();
        boolean menu = false;
        int i = 0;

        // game constructor
        public Game(Context context) {
            super(context);
            holder = getHolder();
            sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            font = Typeface.createFromAsset(context.getAssets(),"italic.ttf");
            baldur = Typeface.createFromAsset(context.getAssets(),"baldur.ttf");
            // takes up lots of memory
            resizedBack = Bitmap.createScaledBitmap(background, width, height, false);
        }



        // called in over-ridden pause method
        public void pause(){
            running = false;
            while (true) {
                try{
                    thread.join();
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }
                break;
            }
            sm.unregisterListener(this);

        }

        // called in over-ridden resume method
        public void resume(){

            running = true;
            thread = new Thread(this);
            thread.start();

            sm.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
        }

        public void destroy(){
            thread = null;
        }

        // when the application is running
        @Override
        public void run() {

            pX = pY = sensorX = sensorZ = sensorY = 0;
            scoreString = Integer.toString(score);
            greenString = Integer.toString(greens);

            // checks to see if device has accelerometer
            if (sm.getSensorList(Sensor.TYPE_GRAVITY).size() != 0){
                sensor = sm.getSensorList(Sensor.TYPE_GRAVITY).get(0);
                sm.registerListener(this,sensor,SensorManager.SENSOR_DELAY_FASTEST);
            }

            // continuous game loop
            while(running){

                // checks to see if there is a surface that is valid
                if(!holder.getSurface().isValid())
                    continue;
                // lock canvas to begin drawing on canvas
                Canvas canvas = holder.lockCanvas();
                // draw background image

                canvas.drawBitmap(resizedBack, 0, 0, null);
                canvas.drawBitmap(pauseButton, 0, 0, null);
                canvas.drawBitmap(gapple,canvas.getWidth()-gapple.getWidth(),canvas.getHeight()-gapple.getHeight(),null);

                if (lives > 2)
                   canvas.drawBitmap(life,0,canvas.getHeight()-life.getHeight(),null);
                if (lives > 1)
                   canvas.drawBitmap(life,life.getWidth(),canvas.getHeight()-life.getHeight(),null);
                if (lives > 0)
                  canvas.drawBitmap(life,life.getWidth()*2,canvas.getHeight()-life.getHeight(),null);
                else {  // no more lives, guy falls down
                    end = true;
                    i += 11;
                }

                // draw score
                canvas.drawText("Score:", canvas.getWidth()/2 + 50, 50, textPaint);
                canvas.drawText(scoreString, canvas.getWidth() - 220, 50, textPaint);
                canvas.drawText(greenString, canvas.getWidth() - gapple.getWidth()-130, canvas.getHeight()-50, textPaint);
                // person object with starting x and y positions
                pX = canvas.getWidth()/2 - face.getWidth()/2;
                pY = canvas.getHeight()-face.getHeight()+i;
                // newX position for moving person
                newX = pX - sensorX*84;
                // do not let person go off canvas boundary
                if ( newX < 0 )
                    newX = 0;
                else if ( newX > canvas.getWidth()-face.getWidth() )
                    newX = canvas.getWidth()-face.getWidth();

                // person going left
                if ( sensorX >= 0)
                  canvas.drawBitmap(face,newX,pY,null);
                else  // person going right
                    canvas.drawBitmap(face2,newX,pY,null);

                // draw start menu on canvas
                if (!menu){
                    canvas.drawText("Tilt Device", canvas.getWidth()/2, canvas.getHeight()/4-110, resumePaint);
                    canvas.drawText("to Avoid Reds", canvas.getWidth()/2 ,canvas.getHeight()/4, resumePaint);
                    canvas.drawText("and Collect Greens", canvas.getWidth()/2 ,canvas.getHeight()/4+110, resumePaint);
                }

                if (!end) {
                    // loop through list of items
                    for (int i = 0; i < foodList.size(); i++) {
                        // store item into local variable
                        temp = foodList.get(i);
                        // set x position of item randomly
                        if (temp.x == 0)
                            temp.x = rand.nextInt(canvas.getWidth() - temp.item.getWidth());

                        // draw item onto canvas
                        temp.drawBitMap(canvas);
                        // update the position of the item boundary
                        temp.bound.set(temp.x, temp.y, temp.x + temp.item.getWidth(), temp.y + temp.item.getHeight());

                        // item hits person
                        if (temp.bound.intersect((int) (newX + (face.getWidth() / 2)),
                                (int) pY, (int) (newX + 0.8 * face.getWidth()), (int) pY + 10)) {
                            if (temp == apple6) {
                                score = score + 2;
                                scoreString = Integer.toString(score);
                                greens++;
                                greenString = Integer.toString(greens);
                            }
                            else {
                                sp.start();
                                lives--;
                                v.vibrate(500);
                            }
                                // generate new random x,y position for object
                                if (offset > 0)
                                    offset = offset - 50;
                                temp.y = -rand.nextInt(SCALE) - offset;
                                temp.x = rand.nextInt(canvas.getWidth() - temp.item.getWidth());

                            continue;
                        }

                        // while the item does not reach bottom
                        if (temp.y < canvas.getHeight())
                            temp.y += speed;
                        else {  // object reaches bottom, goes back to top and increment speed
                            score = score + 1;  // increase score
                            scoreString = Integer.toString(score);
                            if (speed < 30)
                                speed += 0.14;
                            // generate new random x,y position for object
                            if (offset > 0)
                                offset = offset - 50;
                            temp.y = -rand.nextInt(SCALE) - offset;
                            temp.x = rand.nextInt(canvas.getWidth() - temp.item.getWidth());
                        }
                    }
                }
                // draw canvas
                holder.unlockCanvasAndPost(canvas);

                // the beginning of the game menu
                if (!menu) {
                    try {
                        thread.sleep(3500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    menu = true;
                }
                // ends game after guy falls off screen
                if ( pY > height)
                    onGameEnd();

            }

        }

        public void onSensorChanged(SensorEvent event) {
            sensorX = event.values[0];
            sensorY = event.values[1];
            sensorZ = event.values[2];
        }


        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

        private void onGameEnd()
        {
            background = null;
            resizedBack = null;
            SharedPreferences shared = getSharedPreferences("Highscore",Context.MODE_PRIVATE);
            SharedPreferences time = getSharedPreferences("Time",Context.MODE_PRIVATE);
            int high = shared.getInt("Highscore", 0);
            int high2 = shared.getInt("Highscore2", 0);
            int high3 = shared.getInt("Highscore3", 0);
            int temp;
            String time1 = time.getString("Time1", null);
            String time2 = time.getString("Time2", null);
            String time3 = time.getString("Time3", null);
            String tempTime;
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH)+1;
            int day = c.get(Calendar.DATE);
            String date = month+"-"+day+"-"+year;

            if (score > high) {

                temp = high2;
                high2 = high;
                high3 = temp;
                tempTime = time2;
                time2 = time1;
                time3 = tempTime;

                saveHighScore();
                saveHighScore2(high2);
                saveHighScore3(high3);
                saveTime("Time1",date);
                saveTime("Time2",time2);
                saveTime("Time3",time3);
                high = shared.getInt("Highscore", 0);
            }
            else if (score > high2){
                high3 = high2;
                saveHighScore2(score);
                saveHighScore3(high3);
                saveTime("Time2",date);
                saveTime("Time3",time3);
            }
            else if (score > high3){
                saveHighScore3(score);
                saveTime("Time3",date);
            }

            running = false;
            endC =holder.lockCanvas();
            cWidth = endC.getWidth();
            cHeight = endC.getHeight();
            pauseMenu = new Rect(100,100,endC.getWidth()-100,endC.getHeight()-100);
            endC.drawRect(pauseMenu, pausePaint);
            endC.drawText("Play Again", cWidth/2, 400, resumePaint);
            endC.drawText("Quit", cWidth /2, cHeight-400,resumePaint);
            endC.drawText("High Score:",cWidth/2,cHeight/2-120,resumePaint);
            endC.drawText(""+high,cWidth/2,cHeight/2,resumePaint);
            holder.unlockCanvasAndPost(endC);
            resumePaint.getTextBounds("Play Again", 0, "Play Again".length(), resumeBounds);
            resumeX = resumeBounds.width();
            resumeY = resumeBounds.height();
            resumePaint.getTextBounds("Quit",0,"Quit".length(),quitBounds);
            quitX = quitBounds.width();
            quitY = quitBounds.height();
        }

        private void saveHighScore(){
            SharedPreferences shared = getSharedPreferences("Highscore", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = shared.edit();
            editor.putInt("Highscore",score);
            editor.commit();
        }

        private void saveHighScore2(int s){
            SharedPreferences shared = getSharedPreferences("Highscore", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = shared.edit();
            editor.putInt("Highscore2",s);
            editor.commit();
        }

        private void saveHighScore3(int s){
            SharedPreferences shared = getSharedPreferences("Highscore", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = shared.edit();
            editor.putInt("Highscore3",s);
            editor.commit();
        }

        private void clearPref(){
            SharedPreferences shared = getSharedPreferences("Highscore",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = shared.edit();
            SharedPreferences time = getSharedPreferences("Time",Context.MODE_PRIVATE);
            editor.clear();
            editor.commit();
            editor = time.edit();
            editor.clear();
            editor.commit();
        }

        private void saveTime(String tag,String date){
            SharedPreferences time = getSharedPreferences("Time", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = time.edit();
            editor.putString(tag, date);
            editor.commit();
        }

    }
}
