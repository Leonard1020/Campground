package com.andriod.leonardj.campgroundmania;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Player extends GameObject{

    private Bitmap spritesheet;
    private int score;
    private boolean playing;
    private Animation animation = new Animation();
    private long startTime;

    private float targetX;
    private float targetY;

    public Player(Bitmap res, float w, float h, int numFrames) {

        x = targetX = GamePanel.WIDTH / 2;
        y = targetY = GamePanel.HEIGHT / 2;
        dx = 0;
        dy = 0;
        score = 0;
        height = h;
        width = w;

        Bitmap[] image = new Bitmap[numFrames];
        spritesheet = res;

        for (int i = 0; i < image.length; i++) {
            image[i] = Bitmap.createBitmap(spritesheet, (int)(i*width), 0, (int)width, (int)height);
        }

        animation.setFrames(image);
        animation.setDelay(10);
        startTime = System.nanoTime();

    }

    public void setTarget(float x, float y){
        targetX = x;
        targetY = y;
        if (this.x < x){
            dx = GamePanel.MOVESPEED;
        } else if (this.x > x){
            dx = -GamePanel.MOVESPEED;
        }
        if (this.y < y){
            dy = GamePanel.MOVESPEED;
        } else if (this.y > y){
            dy = -GamePanel.MOVESPEED;
        }
    }

    public void update()
    {
        long elapsed = (System.nanoTime()-startTime)/1000000;
        if(elapsed>100){
            score++;
            startTime = System.nanoTime();
        }
        animation.update();

        if (x > targetX - 1.5f && x < targetX + 1.5f){
            dx = 0;
        }
        targetX -= dx;

        if (y > targetY - 1.5f && y < targetY + 1.5f){
            dy = 0;
        }
        targetY -= dy;

        System.out.println(x + " " + targetX);

        if (y > targetY - 1.5f && y < targetY + 1.5f && x > targetX - 1.5f && x < targetX + 1.5f){
            setPlaying(false);
        }
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(animation.getImage(),x,y,null);
    }
    public int getScore(){return score;}
    public boolean getPlaying(){return playing;}
    public void setPlaying(boolean b){playing = b;}
    public void resetDY(){dy = 0;}
    public void resetDX(){dx = 0;}
    public void resetScore(){score = 0;}
}