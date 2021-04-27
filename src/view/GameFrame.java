package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import entity.Point;
import entity.Squares;

// 游戏界面
public class GameFrame extends JFrame {

    private static final long serialVersionUID = -1850036952484112134L;
    /**游戏区面板 */
    private JPanel gamePanel;
    /**用户信息区面板 */
    private JPanel userPanel;
    /**控制区面板 */
    private JPanel controllerPanel;
    /**游戏Image */
    private BufferedImage gameImage;
    /**下一个Image */
    private BufferedImage nextImage;
    /**显示下一个方块的面板 */
    private JPanel nextPanel;
    /**显示用户名 */
    private JLabel username;
    /**显示成绩的标签 */
    private JLabel score;
    /** 最高成绩*/
    private JLabel maxscore;
    /**显示排名的标签 */
    private JLabel paiming;
    /**控制按钮 1.新游戏 2.暂停/继续 3.音乐 4.帮助 5.关于 */
    private JButton btn[] = new JButton[5];
    private JSlider slider;
    public GameFrame() {
        super("俄罗斯方块");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel container = (JPanel)getContentPane();
        container.setOpaque(false);
        container.setLayout(null);
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(null);
        panel.setLocation(6, 6);
        panel.setSize(Squares.WIDTH * Squares.BLOCK_WIDTH + 200, Squares.HEIGHT * Squares.BLOCK_WIDTH);
        container.add(panel);
        container.setPreferredSize(new Dimension(panel.getWidth(), panel.getHeight()));

        /**游戏区面板 */
        gamePanel = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                g.drawImage(gameImage, 0,0, null);
            }
        };
        gamePanel.setBorder(BorderFactory.createMatteBorder(1,1,1,0,Color.BLACK));
        // gamePanel.setBackground(Color.red);
        gamePanel.setBounds(0, 0, Squares.WIDTH * Squares.BLOCK_WIDTH, Squares.HEIGHT * Squares.BLOCK_WIDTH);
        gameImage = new BufferedImage(gamePanel.getWidth()-2,gamePanel.getHeight()-2,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D)gameImage.getGraphics();
        for (int i = 0; i < Squares.WIDTH; i++) {
            for (int j = 0; j < Squares.HEIGHT; j++) {
                g.setColor(new Color(0, 0, 0, 25));
                g.drawRect(i*Squares.BLOCK_WIDTH, j*Squares.BLOCK_WIDTH, Squares.BLOCK_WIDTH,Squares.BLOCK_WIDTH);
            }
        }
        gamePanel.setLayout(null);
        gamePanel.setOpaque(false);
        panel.add(gamePanel);

        /**用户信息区面板 */
        userPanel = new JPanel();
        userPanel.setLayout(null);
        userPanel.setBorder(BorderFactory.createMatteBorder(1,1,0,1,Color.BLACK));
        userPanel.setBounds(Squares.WIDTH * Squares.BLOCK_WIDTH, 0, 200, (Squares.HEIGHT * Squares.BLOCK_WIDTH / 2) - 60);
        /**显示下一个面板 */
        JLabel nextLabel = new JLabel("下一个方块:");
        nextLabel.setFont(new Font("宋体",Font.PLAIN,15));
        nextLabel.setBounds(50,0,100,20);
        nextLabel.setForeground(Color.white);
        nextPanel = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                g.drawImage(nextImage, 0, 0, null);
            }
        };
        nextPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        nextPanel.setBounds(50, 20, 100, 100);
        nextImage = new BufferedImage(nextPanel.getWidth(),nextPanel.getHeight(),BufferedImage.TYPE_INT_ARGB);
        // nextPanel.setBackground(Color.blue);
        nextPanel.setOpaque(false);
        /**显示用户名 */
        username = new JLabel("用户名:");
        username.setFont(new Font("宋体",Font.BOLD,15));
        username.setBounds(50, 130, 150, 20);
        username.setForeground(Color.white);
        /**显示成绩 */
        score = new JLabel("分数:0");
        score.setFont(new Font("宋体",Font.BOLD,15));
        score.setBounds(50, 130+30, 150, 20);
        score.setForeground(Color.white);
        /**显示历史最高 */
        maxscore = new JLabel("历史最高:0");
        maxscore.setFont(new Font("宋体",Font.BOLD,15));
        maxscore.setForeground(Color.white);
        maxscore.setBounds(50, 160+30, 150, 20);
        /**显示排名 */
        paiming = new JLabel("排名:");
        paiming.setFont(new Font("宋体",Font.BOLD,15));
        paiming.setBounds(50, 190+30, 150, 20);
        paiming.setForeground(Color.white);
        userPanel.add(username);
        userPanel.add(nextLabel);
        userPanel.add(nextPanel);
        userPanel.add(score);
        userPanel.add(maxscore);
        userPanel.add(paiming);
        userPanel.setOpaque(false);
        panel.add(userPanel);

        /**控制区面板 */
        controllerPanel = new JPanel();
        controllerPanel.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.BLACK));
        controllerPanel.setBounds(Squares.WIDTH * Squares.BLOCK_WIDTH, (Squares.HEIGHT * Squares.BLOCK_WIDTH / 2) - 60, 200, (Squares.HEIGHT * Squares.BLOCK_WIDTH / 2) + 60);
        btn[0] = new JButton("新游戏");
        btn[1] = new JButton("暂停/继续");
        btn[2] = new JButton("音乐");
        btn[3] = new JButton("帮助");
        btn[4] = new JButton("关于");
        controllerPanel.setLayout(null);
        controllerPanel.setOpaque(false);
        for (int i=0;i<btn.length;i++){
            btn[i].setBounds(50, 80 + (50+10) *i, 100,50);
            controllerPanel.add(btn[i]);
        }
        slider = new JSlider(1,5,1);
        // slider.setToolTipText("等级控制");
        // 设置主刻度间隔
        slider.setMajorTickSpacing(1);
        // 设置次刻度间隔
        // slider.setMinorTickSpacing(1);
        // 绘制 刻度 和 标签
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setOpaque(false);
        slider.setBounds(10, 30, controllerPanel.getWidth()-20, 50);
        JLabel sliderLabel = new JLabel("等级控制",JLabel.CENTER);
        sliderLabel.setBounds(10, 10, controllerPanel.getWidth()-20, 20);
        controllerPanel.add(sliderLabel);
        controllerPanel.add(slider);
        panel.add(controllerPanel);

        // panel.setSize(Squares.WIDTH * Squares.BLOCK_WIDTH + 200, Squares.HEIGHT * Squares.BLOCK_WIDTH);
        panel.setOpaque(false);
        pack();

        /**背景图片 */
        ImageIcon imageIcon = new ImageIcon("img/cyan.png");
        Image bkImage=imageIcon.getImage();
        imageIcon.setImage(bkImage.getScaledInstance(container.getWidth(), container.getHeight(), Image.SCALE_AREA_AVERAGING));
        JLabel bkLabel = new JLabel(imageIcon);
        bkLabel.setLocation(6, 6);
        bkLabel.setSize(container.getWidth(), container.getHeight());
        getLayeredPane().add(bkLabel,new Integer(Integer.MIN_VALUE));

        // 游戏区背景图
        ImageIcon gameIcon = new ImageIcon("img/1.png");
        gameIcon.setImage(gameIcon.getImage().getScaledInstance(gamePanel.getWidth(), gamePanel.getHeight(), Image.SCALE_AREA_AVERAGING));
        JLabel gamebklabel = new JLabel(gameIcon);
        gamebklabel.setLocation(panel.getLocation());
        gamebklabel.setSize(gamePanel.getSize());
        // System.out.println(gamePanel.getSize());
        getLayeredPane().add(gamebklabel,new Integer(Integer.MIN_VALUE+1));
        // System.out.println("width:"+getWidth() + "\theight:" + getHeight());
        // System.out.println("containerwidth:"+container.getWidth() + "\tcontainerheight:" + container.getHeight());
        // System.out.println("panelwidth:"+panel.getWidth() + "\tpanelheight:" + panel.getHeight());

        /**主面板设置 */
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        // image = gamePanel.createImage(Squares.WIDTH*Squares.BLOCK_WIDTH-2, Squares.HEIGHT*Squares.BLOCK_WIDTH-2);
        // graphics = image.getGraphics();
        // nextImage = nextPanel.createImage(100, 100);
        // graphics.setColor(Color.red);
        // graphics.drawImage(bkImage, 0, 0, null);
    }

    public void repaintGame(boolean[][] b,Color c[][]){
        // graphics.drawImage(img, x, y, observer)
        Graphics2D g = (Graphics2D)(gameImage.getGraphics());
        g.setBackground(new Color(0,0,0,0));
        g.clearRect(0, 0, gameImage.getWidth(),gameImage.getHeight());
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b[i].length; j++) {
                if(b[i][j]==true){
                    // System.out.println(i+"\t"+j);
                    g.setColor(c[i][j]);
                    g.fillRect(i*Squares.BLOCK_WIDTH+1, j*Squares.BLOCK_WIDTH+1, Squares.BLOCK_WIDTH-1,Squares.BLOCK_WIDTH-1);
                } else {
                    g.setColor(new Color(0, 0, 0, 25));
                    g.drawRect(i*Squares.BLOCK_WIDTH, j*Squares.BLOCK_WIDTH, Squares.BLOCK_WIDTH,Squares.BLOCK_WIDTH);
                }
            }
        }
        gamePanel.repaint();
    }

    public void repaintGame(Point[] points){
        Graphics g = gamePanel.getGraphics();
        for (Point p : points) {
            if(p.y>=0){
                g.setColor(p.getColor());
                g.fillRect(p.x*Squares.BLOCK_WIDTH+1, p.y*Squares.BLOCK_WIDTH+1,Squares.BLOCK_WIDTH-1, Squares.BLOCK_WIDTH-1);
            }
        }
    }

    public void repaintNext(Point[] point){
        Graphics2D g = (Graphics2D)(nextImage.getGraphics());
        g.setBackground(new Color(0,0,0,0));
        g.clearRect(0, 0, gamePanel.getWidth()-2,gamePanel.getHeight()-2);
        g.setColor(Color.red);
        // g.clearRect(0, 0, 100, 100);
        for (int i = 0; i < point.length; i++) {
            g.fillRect(point[i].x*Squares.BLOCK_WIDTH, point[i].y*Squares.BLOCK_WIDTH, Squares.BLOCK_WIDTH-2, Squares.BLOCK_WIDTH-2);
        }
        // nextPanel.getGraphics().clipRect(0, 0, 100, 100);
        // nextPanel.getGraphics().drawImage(nextImage,0,0,null);
        nextPanel.repaint();
    }

    public JPanel getGamePanel() {
        return gamePanel;
    }

    public JPanel getUserPanel() {
        return userPanel;
    }

    public JPanel getControllerPanel() {
        return controllerPanel;
    }

    public JLabel getScore() {
        return score;
    }

    public JLabel getMaxscore(){
        return maxscore;
    }

    public JLabel getPaiming() {
        return paiming;
    }

    public JButton[] getBtn() {
        return btn;
    }

    public JSlider getSlider() {
        return slider;
    }

    public JLabel getUsername() {
        return username;
    }
}