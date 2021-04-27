package view;

import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import entity.User;

// 排行榜界面
public class RankFrame extends JFrame {

    // private static RankFrame self = null;
    public static void main(String[] args) {
        new RankFrame();
    }
    public RankFrame(){
        super("排行榜");
        JPanel panel = (JPanel) getContentPane();

        List<User> list = User.getUsers();
        String[] columnTitle = {"用户名","历史最高成绩","排名"};
        String[][] tableData = new String[list.size()][columnTitle.length];
        for (int i = 0; i < list.size(); i++) {
            User u = list.get(i);
            tableData[i][0] = u.getName();
            tableData[i][1] = "" + u.getMaxscore();
            tableData[i][2] = "" + u.getRanking();
        }
        list = null;
        JTable table = new JTable(tableData,columnTitle);
        table.setFont(new Font(Font.DIALOG,Font.BOLD,16));
        table.setRowHeight(20);
        // JTableHeader header = table.getTableHeader();
        // scrollPane.add(header);
        JScrollPane scrollPane = new JScrollPane(table);
        // scrollPane.setPreferredSize(new Dimension(400,300));
        // scrollPane.add(table);

        // panel.setLayout(null);
        panel.add(scrollPane);
        panel.setPreferredSize(new Dimension(400,300));
        pack();
        // setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    // public static void Show(){
    //     if(self==null){
    //         self = new RankFrame();
    //     }
    //     self.setVisible(true);
    // }
}