package com.example.stevew.newtons;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Items {

    Bitmap item;
    int x,y;
    Rect bound;


    public void drawBitMap(Canvas canvas){

        canvas.drawBitmap(item,x,y,null);
    }

    public void setNull(){
        item = null;
    }



}
