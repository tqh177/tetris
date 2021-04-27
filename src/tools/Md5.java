package tools;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JOptionPane;

public class Md5 {

    String string = null;
    public Md5(String text){
        MessageDigest md;
        byte[] secretBytes = null;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(text.getBytes());
            // 获得加密后的数据
            secretBytes = md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "不支持md5算法");
            return;
        }
        // 对字符串进行加密
        text = new BigInteger(1, secretBytes).toString(16);// 16进制数字
        // 如果生成数字未满32位，需要前面补0
        for (int i = 0; i < 32 - text.length(); i++) {
            text = "0" + text;
        }
        string = text;
    }
    @Override
    public String toString(){
        return string;
    }
}