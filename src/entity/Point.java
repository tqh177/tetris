package entity;

import java.awt.Color;

public class Point {

    public int x;
    public int y;
    public Color color = Color.red;
    public Point(){
        this.x = 0;
        this.y = 0;
    }
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Point(int x, int y, Color color){
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public Point (Color color){
        this();
        this.color = color;
    }

    public void setLocation(int x,int y){
        this.x = x;
        this.y = y;
    }

    public void setColor(Color c){
        color = c;
    }

    public Color getColor() {
        return color;
    }
}