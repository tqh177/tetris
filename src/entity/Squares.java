package entity;

import java.awt.Color;

public class Squares {
    public static final int WIDTH = 16;
    public static final int HEIGHT = 25;
    public static final int BLOCK_WIDTH = 25;
    private boolean[][] squares = new boolean[WIDTH][HEIGHT];
    private Color[][] colors = new Color[WIDTH][HEIGHT];

    public Squares() {
        // 初始化方格信息
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                squares[x][y] = false;
                colors[x][y] = Color.red;
            }
        }
    }

    public boolean[][] getSquares() {
        return squares;
    }

    public void setSquares(int x, int y, boolean flag) {
        squares[x][y] = flag;
    }

    public Color[][] getColors(){
        return colors;
    }

    // 把4个方格加进去
    public void addBlock(Block block) {
        for (Point p : block.getPs()) {
            if (p.x >= 0 && p.x < WIDTH && p.y >= 0 && p.y < HEIGHT) {
                squares[p.x][p.y] = true;
                colors[p.x][p.y] = p.color;
            }
        }
    }

    public void removeBlock(Block block) {
        for (Point p : block.getPs()) {
            if (p.x >= 0 && p.x < WIDTH && p.y >= 0 && p.y < HEIGHT)
                squares[p.x][p.y] = false;
        }
    }

    // 判断越界
    public boolean judge(Block block) {
        for (Point p : block.getPs()) {
            if (p.x < 0 || p.x >= WIDTH || p.y >= HEIGHT || (p.y >= 0 && squares[p.x][p.y] == true)) {
                return true;
            }
        }
        return false;
    }

    public int clear(int from) {
        int flagLen = 0;
        int to = from - 3 > 0 ? from - 3 : 0;
        for (int y = from; y >= to; y--) {
            int x = 0;
            for (; x < WIDTH; x++) {
                if (squares[x][y] == false) {
                    break;
                }
            }
            if (WIDTH == x) {
                flagLen++;
                for (int yy = y; yy > 0; yy--) {
                    int xx = 0;
                    boolean f = false;
                    for (; xx < WIDTH; xx++) {
                        squares[xx][yy] = squares[xx][yy - 1];
                        colors[xx][yy] = colors[xx][yy - 1];
                        if (squares[xx][yy - 1] == true) {
                            f = true;
                        }
                    }
                    if (f == false) {
                        y++;
                        break;
                    }
                }
            }
        }
        return flagLen;
    }
}