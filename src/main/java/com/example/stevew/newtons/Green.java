package com.example.stevew.newtons;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Green extends Items{

    public Green(Context context,int x, int y){
        item = BitmapFactory.decodeResource(context.getResources(), R.drawable.green);
        bound = new Rect(x,item.getHeight()+y-10,x+item.getWidth(),item.getHeight()+y);
        this.x = x;
        this.y = y;
    }

}
