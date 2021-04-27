package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import entity.Block;
import entity.Point;
import entity.Squares;
import entity.User;
import tools.MP3Player;
import tools.Md5;
import tools.Music;
import view.AboutFrame;
import view.GameFrame;
import view.HelpFrame;
import view.RankFrame;

public class Game extends KeyAdapter implements ActionListener {

    // 计时器 方块下落计时
    private Timer timer = null;
    // 计时器 方块变色计时
    private Timer timer2 = null;
    // 初始下落速度 500ms
    private static final int DELAY = 500;
    // 当前下落速度
    private int delay = DELAY;
    // 方格对象
    private Squares squares;
    // 游戏框架对象
    private GameFrame frame;
    // 方块对象
    private Block block;
    // 是否暂停
    private boolean playing;
    // 是否游戏结束
    private boolean gameovering;
    // 当前得分
    private int score;
    // 用户对象
    private User user;
    // 下一个下落的形状
    private int nextshape;
    // 访问数据库的线程
    private Thread thread;
    // 方块颜色数组
    private static final Color[] COLORS = {Color.red,Color.green,Color.BLUE,Color.GRAY,Color.ORANGE,Color.CYAN};
    private MP3Player player = null;

    public static void main(String[] args) {
        User user = new User("tqh", new Md5("123").toString(), 6, 10);
        new Game(user);
    }
    public Game(User user) {
        player = new MP3Player("res/bg.mp3");
        player.play();
        this.user = user;
        gameovering = true;
        playing = false;
        frame = new GameFrame();
        frame.getUsername().setText("用户名:"+user.getName());
        frame.getMaxscore().setText("历史最高:"+user.getMaxscore());
        frame.getPaiming().setText("排名:" + user.getRanking());
        // 开始键盘监听
        frame.addKeyListener(this);
        JButton[] button = frame.getBtn();
        for (JButton btn : button) {
            // 按钮监听
            btn.addActionListener(this);
        }
        // 查看排名监听
        frame.getPaiming().addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                new RankFrame();
            }
        });
        // 更新游戏难度监听
        frame.getSlider().addChangeListener(new ChangeListener(){

            @Override
            public void stateChanged(ChangeEvent e) {
                // System.out.println(frame.getSlider().getValue());
                // 等级
                int d = frame.getSlider().getValue();
                delay = DELAY-(d-1)*100;
                if(timer!=null){
                    timer.setDelay(delay);
                }
                frame.requestFocus();
            }
        });
    }

    // 开始游戏
    public void begin() {
        playing = true;
        gameovering = false;
        frame.getScore().setText("分数:0");
        squares = new Squares();
        nextshape = (int) (Math.random() * Block.shapesNum);
        // Music.playBegin();
        if(timer2 == null)
        {
            // 每两秒改变一次方块的颜色
            timer2 = new Timer(2000, new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (block != null) {
                        int i= (int) (Math.random() * COLORS.length);
                        if(COLORS[i].equals(block.getColor())){
                            i++;
                            i = i % COLORS.length;
                        }
                        block.setColor(COLORS[i]);
                        if(playing==false){
                            frame.repaintGame(block.getPs());
                        }
                    }
                }
            });
            timer2.start();
        }
        fall();
    }

    // 下落
    private void fall() {
        block = new Block((int) (Math.random() * Squares.WIDTH), 0, nextshape,COLORS[(int)(Math.random()*COLORS.length)]);
        nextshape = (int) (Math.random() * Block.shapesNum);
        {
            Block block = new Block(0, 3, nextshape,Color.red);
            int stepx = 0;
            int WIDTH = 4;
            // int HEIGHT = 4;
            for (Point p : block.getPs()) {
                if (p.x < 0) {
                    stepx = stepx < p.x ? stepx : p.x;
                } else if (p.x >= WIDTH) {
                    stepx = stepx > p.x - WIDTH + 1 ? stepx : p.x - WIDTH + 1;
                }
            }
            if (stepx != 0) {
                block.move(Block.LEFT, stepx);
            }
            frame.repaintNext(block.getPs());
        }
        // block = new Block(0, 0, 2);
        int stepx = 0;
        for (Point p : block.getPs()) {
            if (p.x < 0) {
                stepx = stepx < p.x ? stepx : p.x;
            } else if (p.x >= Squares.WIDTH) {
                stepx = stepx > p.x - Squares.WIDTH + 1 ? stepx : p.x - Squares.WIDTH + 1;
            }
        }
        if (stepx != 0) {
            block.move(Block.LEFT, stepx);
        }
        squares.addBlock(block);
        frame.repaintGame(squares.getSquares(),squares.getColors());
        if (playing == false) {
            return;
        }
        if (timer == null) {
            timer = new Timer(delay, new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    move(Block.DOWN, 1);
                }
            });
        }
        timer.start();
    }

    // 移动
    private void move(int direction, int distance) {
        squares.removeBlock(block);
        Block blockTemp = block.copy();
        block.move(direction, distance);
        if (squares.judge(block)) {
            // 阻塞
            block = blockTemp;
            squares.addBlock(block);
            if (direction == Block.DOWN) {
                // block已经到底
                if (block.isTop()) {
                    // block已经到顶
                    // System.out.println("x:"+block.getPs()[0].x+"\ty:"+block.getPs()[0].y+"\ttype:"+block.shapes);
                    gameover();
                    return;
                } else {
                    clear();
                    fall();
                    return;
                }
            }
        } else {
            squares.addBlock(block);
            frame.repaintGame(squares.getSquares(),squares.getColors());
        }
    }

    // 游戏结束
    private void gameover() {
        if (timer != null) {
            timer.stop();
        }
        gameovering = true;
        block = null;
        Music.playGameover();
        JOptionPane.showMessageDialog(frame, "得分: "+score, "游戏结束", JOptionPane.WARNING_MESSAGE);
        // System.out.println("游戏结束");
    }

    // 开始及暂停
    private void play() {
        if (playing == true) {
            if (timer != null) {
                timer.stop();
                // timer2.stop();
            }
            playing = false;
        } else {
            if (timer != null) {
                timer.start();
                // timer2.start();
            }
            playing = true;
        }
    }

    // 消去方块
    private void clear() {
        int line = squares.clear(block.getPs()[0].y);
        if (line > 0) {
            Music.playEliminate();
            int currentScore = (2 * line - 1) * 5;
            if((currentScore+score)/100>score/100){
                int v = frame.getSlider().getValue();
                frame.getSlider().setValue(v==5?5:v+1);
            }
            score += currentScore;
            // System.out.println("得分："+this.score+"分");
            frame.getScore().setText("成绩:" + score);
            if (thread == null) {
                // 多线程查询更新数据库
                thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        while (true) {
                            System.out.println(user.getScore());
                            try {
                                synchronized(user){
                                    user.setScore(score);
                                    frame.getMaxscore().setText("历史最高:"+user.getMaxscore());
                                    frame.getPaiming().setText("排名:" + user.getRanking());
                                    user.wait();
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
            if(thread.isAlive()){
                synchronized(user){
                    user.notifyAll();
                }
            }else{
                thread.start();
            }
        }
    }

    // 加速
    public void speedUp(int delay) {
        if(delay == timer.getDelay())
            return;
        this.delay = delay;
        timer.setDelay(delay);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton[] button = frame.getBtn();
        if (e.getSource() == button[0]) {
            begin();
        } else if (e.getSource() == button[1]) {
            // 暂停/继续
            play();
        } else if (e.getSource() == button[2]) {
            // 播放音乐
            if(Music.getPaused()){
                player.play();
            }else{
                player.pause();
            }
            Music.setPaused(!Music.getPaused());
        } else if (e.getSource() == button[3]) {
            HelpFrame.showtop();
        } else if (e.getSource() == button[4]) {
            AboutFrame.showtop();
        }
        frame.requestFocus();
    }

    // 键盘按键监听
    @Override
    public void keyPressed(KeyEvent e) {
        if (gameovering == true) {
            return;
        }
        if(e.getKeyCode() == ' '){
            play();
            return;
        }
        if(playing == false){
            return;
        }
        switch (e.getKeyCode()) {
        case '5':
        case 'W':
        case 38:
            squares.removeBlock(block);
            Block blockTemp = block.copy();
            block.rotate();
            if (squares.judge(block)) {
                // 自动尝试以及平移
                boolean[][] b = squares.getSquares();
                boolean flag = true;
                Block blockRotate = block.copy();
                int width = blockRotate.getWidth();
                for (int ii = 0; ii < 2 && flag; ii++) {
                    for (int i = 0; i < width && flag; i++) {
                        // Block b = block;
                        int direction = ii == 0 ? Block.RIGHT : Block.LEFT;
                        block.move(direction, 1);
                        Point[] ps = block.getPs();
                        if(!squares.judge(block)){
                            // block = blockTemp;
                            flag = false;
                            break;
                        }else if(ps[0].x<0||ps[0].x>=Squares.WIDTH||b[ps[0].x][ps[0].y]){
                            break;
                        }
                    }
                    if(flag){
                        block = blockRotate;
                    }
                }
                if(flag){
                    block = blockTemp;
                    squares.addBlock(block);
                    return;
                }
            }
            Music.palyTransform();
            squares.addBlock(block);
            frame.repaintGame(squares.getSquares(),squares.getColors());
            break;
        case '2':
        case 'S':
        case 40:
            move(Block.DOWN, 1);
            break;
        case '4':
        case 'A':
        case 37:
            move(Block.LEFT, 1);
            break;
        case '6':
        case 'D':
        case 39:
            move(Block.RIGHT, 1);
            break;
        default:
            break;
        }
    }
}