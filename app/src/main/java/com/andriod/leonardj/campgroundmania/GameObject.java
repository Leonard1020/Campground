package com.andriod.leonardj.campgroundmania;

import android.graphics.Rect;
import android.graphics.RectF;

public abstract class GameObject {
    protected float x;
    protected float y;
    protected float dy;
    protected float dx;
    protected float width;
    protected float height;


    public void setX(float x)
    {
        this.x = x;
    }
    public void setY(float y)
    {
        this.y = y;
    }
    public void setDX(float dx)
    {
        this.dx = dx;
    }
    public void setDY(float dy)
    {
        this.dy = dy;
    }
    public float getDX()
    {
        return dx;
    }
    public float getDY()
    {
        return dy;
    }
    public float getX()
    {
        return x;
    }
    public float getY()
    {
        return y;
    }
    public float getHeight()
    {
        return height;
    }
    public float getWidth()
    {
        return width;
    }
    public RectF getRectangle()
    {
        return new RectF(x, y, x+width, y+height);
    }
}