package com.andriod.leonardj.campgroundmania;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by leonardj on 5/5/2015.
 */
public class Tree extends GameObject {

    public static final float BORDER_SIZE = 50f;

    private Bitmap image;

    public Tree(Bitmap res, float x, float y){
        height = BORDER_SIZE;
        width = BORDER_SIZE;

        this.x = x;
        this.y = y;

        image = Bitmap.createBitmap(res, 0, 0, (int)width, (int)height);
    }

    public void update(){
        x += dx;
        y += dy;
    }

    public void draw(Canvas canvas){
        try{
            canvas.drawBitmap(image, x, y, null);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
