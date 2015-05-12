package com.andriod.leonardj.campgroundmania;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    public static final float HELI_WIDTH = 65f;
    public static final float HELI_HEIGHT = 25f;
    public static final int HELI_FRAMES = 3;

    public static final float WIDTH = 480f;
    public static final float HEIGHT = 856f;

    public static final int MOVESPEED = 3;
    public static Bitmap backgroundImage;
    public static Bitmap heliImage;
    public static Bitmap missileImage;
    public static Bitmap brickImage;
    public static Bitmap treeImage;

    private MainThread thread;
    private Background background;
    private Player player;

    public GamePanel(Context context) {
        super(context);

        backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.campground);
        heliImage = BitmapFactory.decodeResource(getResources(), R.drawable.helicopter);
        missileImage = BitmapFactory.decodeResource(getResources(), R.drawable.missile);
        brickImage = BitmapFactory.decodeResource(getResources(), R.drawable.brick);
        treeImage = BitmapFactory.decodeResource(getResources(), R.drawable.tree);

        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        //make gamePanel focusable so it can handle events
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        background = new Background(backgroundImage, treeImage);
        player = new Player(heliImage, HELI_WIDTH, HELI_HEIGHT, HELI_FRAMES);

        newGame();

        //we can safely start the game loop
        thread.setRunning(true);
        thread.start();
    }

    public boolean collision(GameObject a, GameObject b){
        boolean collision = false;
        if (RectF.intersects(a.getRectangle(), b.getRectangle())){
            collision = true;
        }
        return collision;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            if(!player.getPlaying()){
                player.setPlaying(true);
            }
            float pointX = event.getX() / getWidth() * WIDTH;
            float pointY = event.getY() / getHeight() * HEIGHT;

            player.setTarget(pointX, pointY);

            return true;
        }
        if(event.getAction()==MotionEvent.ACTION_UP){
            //player.setUp(false);
            return true;
        }

        return super.onTouchEvent(event);
    }

    public void update(){
        if(player.getPlaying()) {
            player.update();

            background.setDX(-player.getDX());
            background.setDY(-player.getDY());

            background.update();
            //if (collision(tree, player)){
            //    player.resetDY();
            //    player.resetDX();
            //    player.setPlaying(false);
            //}
        }
    }

    @Override
    public void draw(Canvas canvas){
        final float scaleFactorX = getWidth() / WIDTH;
        final float scaleFactorY = getHeight() / HEIGHT;

        if(canvas!=null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            background.draw(canvas);
            player.draw(canvas);
            canvas.restoreToCount(savedState);
        }
    }

    public void newGame(){
        background.newGame();

        player.resetDY();
        player.resetScore();
        player.setY(HEIGHT / 2);
        player.setX(WIDTH / 2);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        int counter = 0;
        while(retry && counter < 1000){
            try{
                thread.setRunning(false);
                thread.join();
                retry = false;
            }catch(InterruptedException e){e.printStackTrace();}
            counter++;
        }
    }
}