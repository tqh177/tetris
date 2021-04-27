package view;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import controller.Game;
import entity.User;
import tools.Md5;

// 登录界面
public class LoginFrame extends JFrame implements ActionListener {

    private static final long serialVersionUID = 8420855605600773400L;
    JButton[] button;
    JPasswordField passwordField;
    JTextField unText;

    public static void main(String[] args) {
        new LoginFrame();
    }
    public LoginFrame() {
        super("登录");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel panel = (JPanel) getContentPane();
        panel.setPreferredSize(new Dimension(537, 410));
        panel.setLayout(null);
        addBKPanel();

        JLabel unLabel = new JLabel("用户名");
        unLabel.setBounds(114, 93, 61, 47);
        unLabel.setFont(new Font(Font.DIALOG,Font.BOLD,18));
        // unLabel.setOpaque(true);
        // unLabel.setBackground(new Color(255, 255, 255, 50));
        unLabel.setForeground(Color.black);
        panel.add(unLabel);

        unText = new JTextField();
        unText.setBounds(175, 93, 247,47);
        unText.setBorder(BorderFactory.createEmptyBorder(5,10,5,0));
        unText.setFont(new Font(Font.DIALOG,Font.BOLD,18));
        // unText.setOpaque(true);
        // unText.setBackground(new Color(255, 255, 255, 200));
        panel.add(unText);

        JLabel pwdLabel = new JLabel("密\u3000码");
        pwdLabel.setBounds(114,170, 61, 47);
        pwdLabel.setFont(new Font(Font.DIALOG,Font.BOLD,18));
        // pwdLabel.setOpaque(true);
        // pwdLabel.setBackground(new Color(255, 255, 255, 50));
        pwdLabel.setForeground(Color.black);
        panel.add(pwdLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(175, 170, 247,47);
        passwordField.setBorder(BorderFactory.createEmptyBorder(5,10,5,0));
        passwordField.setFont(new Font(Font.DIALOG,Font.BOLD,18));
        panel.add(passwordField);

        button = new JButton[2];
        button[0] = new JButton("登录");
        // button[0].setText("登录");
        button[0].setBounds(114, 267, 100, 50);
        button[0].setBackground(new Color(4,186,251));
        button[0].addActionListener(this);
        button[1] = new JButton("注册");
        // button[1].setText("注册");
        button[1].setBounds(322, 267, 100, 50);
        button[1].setBackground(new Color(4,186,251));
        button[1].addActionListener(this);
        panel.add(button[0]);
        panel.add(button[1]);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void addBKPanel(){
        JPanel panel = (JPanel) getContentPane();
        ImageIcon imageIcon = new ImageIcon("img/login.jpeg");
        JLabel imgLabel = new JLabel(imageIcon);
        imgLabel.setBounds(0, 0, panel.getPreferredSize().width+12,panel.getPreferredSize().height+12);
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(imgLabel.getWidth(), imgLabel.getHeight(), Image.SCALE_AREA_AVERAGING));
        panel.setOpaque(false);
        getLayeredPane().add(imgLabel,new Integer(Integer.MIN_VALUE));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button[0]) {
            login();
        } else if (e.getSource() == button[1]) {
            regist();
        } else {
            ;
        }
    }

    private void login() {
        String un = unText.getText();
        String pwd = new String(passwordField.getPassword());
        if (!un.isEmpty() && un.length()<=8 && !pwd.isEmpty()) {
            // 生成一个MD5加密计算摘要
            pwd = new Md5(pwd).toString();
            User user = User.query(un, pwd);
            if (user != null) {
                dispose();
                new Game(user);
            } else {
                JOptionPane.showMessageDialog(null, "登录失败，用户名或密码错误", "登录提示", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "用户名或密码为空或用户名长度大于8", "登录提示", JOptionPane.WARNING_MESSAGE);
        }

    }

    private void regist() {
        String un = unText.getText();
        String pwd = new String(passwordField.getPassword());
        if (!un.isEmpty() && !pwd.isEmpty()) {
            // 生成一个MD5加密计算摘要
            pwd = new Md5(pwd).toString();
            if (User.add(un, pwd) > 0) {
                User user = User.query(un, pwd);
                dispose();
                new Game(user);
            } else {
                JOptionPane.showMessageDialog(null, "注册失败，用户名已存在", "注册提示", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "用户名或密码为空", "注册提示", JOptionPane.WARNING_MESSAGE);
        }
    }
}