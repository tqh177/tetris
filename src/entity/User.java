package entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tools.DBHelper;

public class User {
    private String name;
    private String pwd;
    private int ranking;
    private int maxscore;
    private int score;

    public User(String name, String pwd, int ranking, int maxscore) {
        this.name = name;
        this.pwd = pwd;
        this.ranking = ranking;
        this.maxscore = maxscore;
        score = 0;
    }

    /**添加用户--注册 */
    public static int add(String un, String pwd) {
        String sql = "INSERT INTO user(name,pwd) values(?,?)";
        DBHelper db = new DBHelper();
        Connection conn = db.getConnection();
        PreparedStatement pstm = null;
        int result = -1;
        try {
            if (conn != null) {
                pstm = conn.prepareStatement(sql);
                pstm.setString(1, un);
                pstm.setString(2, pwd);
                result = pstm.executeUpdate();
                // 释放资源
                db.close(conn, pstm, null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**查询用户信息--登录 */
    public static User query(String un, String pwd) {
        User user = null;
        String sql = "SELECT name,maxscore,rowNum FROM (SELECT name,pwd,maxscore,@rowNum:=@rowNum+1 as rowNum FROM (SELECT name,maxscore,pwd from user ORDER BY maxscore DESC) a,(SELECT @rowNum:=0) b) d WHERE name=? and pwd=? limit 1";
        DBHelper db = new DBHelper();
        Connection conn = db.getConnection();
        PreparedStatement pstm = null;
        if (conn != null) {
            try {
                // 获取预编译接口
                pstm = conn.prepareStatement(sql);
                pstm.setString(1, un);
                pstm.setString(2, pwd);
                // System.out.println(un+"\t"+pwd);
                // System.out.println(pstm);
                ResultSet result = pstm.executeQuery();
                result.next();
                int maxscore = result.getInt("maxscore");
                int ranking = result.getInt("rowNum");
                user = new User(un, pwd, ranking, maxscore);
                // 释放资源
                db.close(conn, pstm, null);
            } catch (SQLException e) {
                // e.printStackTrace();
            }
        }
        return user;
    }

    /**获取所有用户信息 */
    public static List<User> getUsers(){
        List<User> list = new ArrayList<>();
        String sql = "SELECT name,maxscore,rowNum FROM (SELECT name,pwd,maxscore,@rowNum:=@rowNum+1 as rowNum FROM (SELECT name,maxscore,pwd from user ORDER BY maxscore DESC) a,(SELECT @rowNum:=0) b) d ORDER BY rowNum ASC";
        DBHelper db = new DBHelper();
        Connection conn = db.getConnection();
        PreparedStatement pstm = null;
        if (conn != null) {
            try {
                // 获取预编译接口
                pstm = conn.prepareStatement(sql);
                ResultSet result = pstm.executeQuery();
                while(result.next()){
                    String un = result.getString("name");
                    int maxscore = result.getInt("maxscore");
                    int ranking = result.getInt("rowNum");
                    list.add(new User(un, null, ranking, maxscore));
                }
                // 释放资源
                db.close(conn, pstm, null);
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
        return list;
    }

    private int setMaxScore(int maxscore) {
        this.maxscore = maxscore;
        int result = -1;
        String sql = "UPDATE user SET maxscore=? WHERE name=?";
        DBHelper db = new DBHelper();
        Connection conn = db.getConnection();
        PreparedStatement pstm = null;
        if (conn != null) {
            try {
                // 获取预编译接口
                pstm = conn.prepareStatement(sql);
                // 占位符赋值，与sql语句中的‘？’顺序有关，从第一列开始
                pstm.setInt(1, maxscore);
                pstm.setString(2, name);
                result = pstm.executeUpdate();
                // 释放资源
                db.close(conn, pstm, null);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public String getName() {
        return name;
    }

    public int getRanking() {
        return ranking;
    }

    public int getMaxscore() {
        return maxscore;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        if (score > maxscore) {
            setMaxScore(score);
            User user = query(name, pwd);
            ranking = user.ranking;
        }
    }
}