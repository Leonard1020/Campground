package com.andriod.leonardj.campgroundmania;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by leonardj on 5/3/2015.
 */
public class MainThread extends Thread {

    private int FPS = 30;
    private double avgFPS;
    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;
    public static Canvas canvas;

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel){
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    @Override
    public void run(){
        long startTime, timeMillis, waitTime, totalTime = 0, frameCount = 0;
        long targetTime = 1000/FPS;

        while(running){
            startTime = System.nanoTime();
            canvas = null;
            try{
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    gamePanel.update();
                    gamePanel.draw(canvas);
                }
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                if (canvas != null){
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeMillis;

            try{
                if (waitTime > 0){
                    this.sleep(waitTime);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if (frameCount == FPS){
                avgFPS = 1000 / ((totalTime/frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
            }
        }
    }

    public void setRunning(boolean running){
        this.running = running;
    }
}
