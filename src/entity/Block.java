package entity;

import java.awt.Color;

public class Block {

    private Point ps[];
    // private int[][][] type = { { { 0, 0, 1, 0, 0, 1, 1, 1 } }, { { 0, 0, 0, 1, 0,
    // 2, 0, 3 } },
    // { { 0, 0, 1, 0, 2, 0, 3, 0 } }, { { 0, 0, -1, 1, 0, 1, 1, 1 } }, { { 0, 0, 0,
    // 1, 0, 2, 1, 1 } },
    // { { 0, 0, 1, 0, 2, 0, 1, 1 } }, { { 0, 0, -1, 1, 0, 1, 0, 2 } } };
    public static final int UP = 8;
    public static final int DOWN = 2;
    public static final int LEFT = 4;
    public static final int RIGHT = 6;
    public static final int shapesNum = 19;
    public int shapes = 0;
    private Color color;

    public Block(int x, int y, int shapes,Color color) {
        ps = new Point[4];
        for (int i = 0; i < ps.length; i++) {
            ps[i] = new Point(color);
        }
        changeType(x, y, shapes);
        this.shapes = shapes;
        this.color = color;
    }

    public Block copy() {
        return new Block(ps[0].x, ps[0].y, shapes,color);
    }

    private void changeType(int x, int y, int i) {
        switch (i) {
        case 0:
            ps[0].setLocation(x + 0, y + 0);
            ps[1].setLocation(x + 0, y - 1);
            ps[2].setLocation(x + 0, y - 2);
            ps[3].setLocation(x + 0, y - 3);
            break;
        case 1:
            ps[0].setLocation(x + 0, y + 0);
            ps[1].setLocation(x + 1, y + 0);
            ps[2].setLocation(x + 2, y + 0);
            ps[3].setLocation(x + 3, y + 0);
            break;
        case 2:
            ps[0].setLocation(x + 0, y + 0);
            ps[1].setLocation(x + 1, y + 0);
            ps[2].setLocation(x + 0, y - 1);
            ps[3].setLocation(x + 1, y - 1);
            break;
        case 3:
            ps[0].setLocation(x + 0, y + 0);
            ps[1].setLocation(x + 1, y + 0);
            ps[2].setLocation(x + 1, y - 1);
            ps[3].setLocation(x + 2, y + 0);
            break;
        case 4:
            ps[0].setLocation(x + 0, y + 0);
            ps[1].setLocation(x + 0, y - 1);
            ps[2].setLocation(x + 0, y - 2);
            ps[3].setLocation(x + 1, y - 1);
            break;
        case 5:
            ps[0].setLocation(x + 0, y + 0);
            ps[1].setLocation(x - 1, y - 1);
            ps[2].setLocation(x + 0, y - 1);
            ps[3].setLocation(x + 1, y - 1);
            break;
        case 6:
            ps[0].setLocation(x + 0, y + 0);
            ps[1].setLocation(x + 0, y - 1);
            ps[2].setLocation(x + 0, y - 2);
            ps[3].setLocation(x - 1, y - 1);
            break;
        case 7:
            ps[0].setLocation(x + 0, y + 0);
            ps[1].setLocation(x + 0, y - 1);
            ps[2].setLocation(x - 1, y - 1);
            ps[3].setLocation(x - 1, y - 2);
            break;
        case 8:
            ps[0].setLocation(x + 0, y + 0);
            ps[1].setLocation(x + 1, y + 0);
            ps[2].setLocation(x + 1, y - 1);
            ps[3].setLocation(x + 2, y - 1);
            break;
        case 9:
            ps[0].setLocation(x + 0, y + 0);
            ps[1].setLocation(x + 0, y - 1);
            ps[2].setLocation(x + 1, y - 1);
            ps[3].setLocation(x + 1, y - 2);
            break;
        case 10:
            ps[0].setLocation(x + 0, y + 0);
            ps[1].setLocation(x + 1, y + 0);
            ps[2].setLocation(x + 0, y - 1);
            ps[3].setLocation(x - 1, y - 1);
            break;//
        case 11:
            ps[0].setLocation(x + 0, y + 0);
            ps[1].setLocation(x + 0, y - 1);
            ps[2].setLocation(x + 0, y - 2);
            ps[3].setLocation(x + 1, y - 2);
            break;
        case 12:
            ps[0].setLocation(x + 0, y + 0);
            ps[1].setLocation(x + 0, y - 1);
            ps[2].setLocation(x - 1, y - 1);
            ps[3].setLocation(x - 2, y - 1);
            break;
        case 13:
            ps[0].setLocation(x + 0, y + 0);
            ps[1].setLocation(x + 1, y + 0);
            ps[2].setLocation(x + 1, y - 1);
            ps[3].setLocation(x + 1, y - 2);
            break;
        case 14:
            ps[0].setLocation(x + 0, y + 0);
            ps[1].setLocation(x + 0, y - 1);
            ps[2].setLocation(x + 1, y + 0);
            ps[3].setLocation(x + 2, y - +0);
            break;
        case 15:
            ps[0].setLocation(x + 0, y + 0);
            ps[1].setLocation(x + 0, y - 1);
            ps[2].setLocation(x + 1, y -1);
            ps[3].setLocation(x + 2, y -1);
            break;
        case 16:
            ps[0].setLocation(x + 0, y + 0);
            ps[1].setLocation(x + 0, y - 1);
            ps[2].setLocation(x + 0, y -2);
            ps[3].setLocation(x -1, y -2);
            break;
        case 17:
            ps[0].setLocation(x + 0, y + 0);
            ps[1].setLocation(x + 1, y +0);
            ps[2].setLocation(x + 2, y +0);
            ps[3].setLocation(x + 2, y -1);
            break;
        case 18:
            ps[0].setLocation(x + 0, y + 0);
            ps[1].setLocation(x + 0, y - 1);
            ps[2].setLocation(x + 0, y -2);
            ps[3].setLocation(x + 1, y +0);
            break;
        }
    }

    // 移动
    public void move(int direction, int distance) {
        switch (direction) {
        case UP:
            distance = 0 - distance;
        case DOWN:
            for (Point p : ps) {
                p.setLocation(p.x, p.y + distance);
            }
            break;
        case LEFT:
            distance = 0 - distance;
        case RIGHT:
            for (Point p : ps) {
                p.setLocation(p.x + distance, p.y);
            }
            break;
        default:
            break;
        }
    }

    // 旋转
    public void rotate() {
        if (shapes < 2) {
            shapes = shapes == 0 ? 1 : 0;
        } else if (shapes == 2) {
            return;
        } else if (shapes < 7) {
            shapes = shapes < 6 ? shapes + 1 : 3;
        } else if (shapes < 9) {
            shapes = shapes == 8 ? 7 : 8;
        } else if (shapes < 11) {
            shapes = shapes == 9 ? 10 : 9;
        } else if(shapes < 15) {
            shapes = shapes < 14 ? shapes + 1 : 11;
        } else if(shapes < 19) {
            shapes = shapes < 18 ? shapes + 1 : 15;
        } else {
            return;
        }
        this.changeType(ps[0].x, ps[0].y, shapes);
    }

    public Point[] getPs() {
        return ps;
    }

    public boolean isTop() {
        for (Point p : ps) {
            if (p.y <= 0) {
                return true;
            }
        }
        return false;
    }

    public Color getColor(){
        return color;
    }

    public void setColor(Color color){
        this.color = color;
        for (int i = 0; i < ps.length; i++) {
            ps[i].color = color;
        }
    }

    public int getWidth(){
        int min=ps[0].x,max=ps[0].x;
        for (Point p : ps) {
            min = min <= p.x ? min :p.x;
            max = max >= p.x ? max :p.x;
        }
        return max - min + 1;
    }
}