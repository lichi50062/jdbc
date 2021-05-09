package dao;

import Util.JDBCUtil;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDao {
    private Connection conn = null;
    private PreparedStatement ps=null;
    private int result=0;
    private ResultSet rs=null;

    //使用者註冊
    public int register(User user){
        String sql="insert into users(name,password,age) value (?,?,?)";
        try {
            //獲取資料庫連線物件
            conn= JDBCUtil.getConnection();
            //獲取資料庫操作物件
            ps=conn.prepareStatement(sql);
            ps.setString(1,user.getName());
            ps.setString(2,user.getPassword());
            ps.setInt(3,user.getAge());
            //執行sql
            result=ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.close(null,ps,conn);
        }
        return result;
    }

    //登入驗證使用者資訊
    public int login(String userName,String password){
        String sql ="select count(*) from users where name=? and password=?";
        try {
            conn=JDBCUtil.getConnection();
            ps=conn.prepareStatement(sql);
            ps.setString(1,userName);
            ps.setString(2,password);
            rs=ps.executeQuery();
            while (rs.next()){
                result=rs.getInt("count(*)");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(rs,ps,conn);
        }
        return result;
    }

    //根據使用者名稱 顯示使用者名稱、密碼、年齡
    public User findByName(String userName){
        String sql="select name,password,age from users where name=?";
        User user = null;
        try {
            conn=JDBCUtil.getConnection();
            ps=conn.prepareStatement(sql);
            ps.setString(1,userName);
            rs=ps.executeQuery();
            while (rs.next()){
                String name = rs.getString("name");
                String password = rs.getString("password");
                int age = rs.getInt("age");
                user = new User(name,password,age);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.close(null,ps,conn);
        }
        return user;
    }
}