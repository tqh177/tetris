package view;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JTextArea;

// 帮助界面
public class HelpFrame extends JFrame {

    private static HelpFrame self = null;
    public static void main(String[] args) {
        HelpFrame.showtop();
    }
    private HelpFrame() {
        super("帮助");
        String text = "1.登录\n    进入界面，玩家点击登录按钮跳转登入界面，玩家需要输入用户名和密码，如正确便可开始游戏。\n\n2.注册\n    当玩家登录失败，便可点击注册按钮，输入自己的基本信息，注册成功后，玩家便可重新登录该游戏。\n\n3.调速器\n   该调速器分五个等级从1-5速度依次增加,玩家在玩俄罗斯方块小游戏时可通过该调速器控制方块下落速度来更好消除方块找到游戏的体验感。\n\n4.新游戏\n   当用户登入该游戏时,点击新游戏,用户可以开始游戏或者一个游戏结束开始下一场游戏。\n\n5.暂停继续\n    当玩家在游戏中出现一些特殊事情,需要暂时离开,玩家可以通过点击暂停按钮,暂停该游戏。\n    当玩家回来时想继续该游戏只需点击继续按钮便可继续该游戏。\n\n6.音乐\n   在游戏中玩家可以点击音乐按钮播放或暂停背景音乐，给玩家带来更好的游戏体验感。\n\n7.帮助\n    在游戏过程中玩家出现一些问题可以通过点击帮助按钮，来获取帮助。\n    比如，新玩家第一次玩俄罗斯方块游戏不懂它的规则时，可以点击帮助按钮来获取规则。\n8.操作方法\n    变形:     ↑或8或w\n    向下移动: ↓或2或s\n    向左移动: ←或4或a\n    向右移动: →或6或d\n9.得分规则\n    得分 = 2 * (消去的行数 - 1) * 5 + 5\n    即消去一行得 5 分\n    即消去两行得 15 分\n    即消去三行得 25 分\n    即消去四行行得 35 分";
        JTextArea helper = new JTextArea(text);
        helper.setEditable(false);
        helper.setLineWrap(true);
        helper.setWrapStyleWord(true);
        helper.setPreferredSize(new Dimension(600, 600));
        this.getContentPane().add(helper);
        pack();
        setLocationRelativeTo(null);
    }

    public static void showtop(){
        if(self==null){
            self = new HelpFrame();
        }
        self.setVisible(true);
    }
}