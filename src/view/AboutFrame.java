package view;

import java.awt.*;

import javax.swing.*;

// 关于界面
public class AboutFrame extends JFrame {

    private static JFrame self = null;
    public static void main(String[] args) {
        AboutFrame.showtop();
    }
    private AboutFrame(){
        super("关于");
        String text = "    游戏名：俄罗斯方块\n    开发团队：施工员团队\n    开发时间：201909\n     版本号：1.0";
        JTextArea helper = new JTextArea(text);
        helper.setEditable(false);
        helper.setLineWrap(true);
        helper.setWrapStyleWord(true);
        // Container panel = getContentPane();
        // panel.setLayout(new BoxLayout(panel,BoxLayout.PAGE_AXIS));
        this.getContentPane().setPreferredSize(new Dimension(400, 300));
        // panel.add(new JLabel("游戏名：俄罗斯方块"));
        // panel.add(new JLabel("开发团队：Team"));
        // panel.add(new JLabel("开发时间：201909"));
        // panel.add(new JLabel("版本号：1.0"));
        this.getContentPane().add(helper);
        pack();
        setLocationRelativeTo(null);
    }
    public static void showtop(){
        if(self==null){
            self = new AboutFrame();
        }
        self.setVisible(true);
    }
}