package com.andriod.leonardj.campgroundmania;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

import java.util.ArrayList;

public class Background {

    private Bitmap backgroundImage;
    private Bitmap treeImage;

    private float x, y, dx, dy, width, height;

    private ArrayList<Tree> treeBorder;

    public Background(Bitmap backgroundImage, Bitmap treeImage){
        this.backgroundImage = backgroundImage;
        this.treeImage = treeImage;

        treeBorder = new ArrayList<>();

        width = backgroundImage.getWidth();
        height = backgroundImage.getHeight();
    }

    public void newGame(){
        treeBorder.clear();

        //initial horizontal border
        for(int i = 0; i * Tree.BORDER_SIZE < width; i++){
            treeBorder.add(new Tree(treeImage, i * Tree.BORDER_SIZE, 0));
            treeBorder.add(new Tree(treeImage, (i + 1) * Tree.BORDER_SIZE, height - Tree.BORDER_SIZE));
        }
        //initial vertical border
        for(int i = 1; i * Tree.BORDER_SIZE < height; i++){
            treeBorder.add(new Tree(treeImage, 0, i * Tree.BORDER_SIZE));
            treeBorder.add(new Tree(treeImage, width - Tree.BORDER_SIZE, i * Tree.BORDER_SIZE));
        }
    }

    public void setDX(float dx){
        this.dx = dx;
        for (Tree tree : treeBorder){
            tree.setDX(dx);
        }
    }

    public void setDY(float dy){
        this.dy = dy;
        for (Tree tree : treeBorder){
            tree.setDY(dy);
        }
    }

    public void stopX(float x){
        this.x = x;
        dx = 0;
        for (Tree tree : treeBorder){
            tree.setDX(0);
        }
    }

    public void stopY(float y){
        this.y = y;
        dy = 0;
        for (Tree tree : treeBorder){
            tree.setDY(0);
        }
    }

    public void update(){
        x += dx;
        if (x > 0){
            stopX(0f);
        } else if (x < -width + GamePanel.WIDTH){
            stopX(-width + GamePanel.WIDTH);
        }
        y += dy;
        if (y > 0){
            stopY(0f);
        } else if (y < -height + GamePanel.HEIGHT){
            stopY(-height + GamePanel.HEIGHT);
        }

        for (Tree tree : treeBorder){
            tree.update();
        }
    }

    public void draw(Canvas canvas){
        try{
            canvas.drawColor(Color.BLACK);
            canvas.drawBitmap(backgroundImage, x, y,null);
            for (Tree tree : treeBorder){
                tree.draw(canvas);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}