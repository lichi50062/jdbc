package Util;

import java.sql.*;

public class JDBCUtil {
    private java.sql.Connection  con = null;
    private static final String url = "jdbc:sqlserver://";
    private static final String serverName= "DESKTOP-IACHNIQ";
    private static final String portNumber = "1433";
    private static final String databaseName= "test";
    private static final String userName = "sa";
    private static final String password = "root";

    //靜態程式碼塊在類載入時執行，並且執行一次。
    static{
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    //獲取資料庫連線物件
    public static Connection getConnection() throws Exception{

        return DriverManager.getConnection(url,userName,password);
    }
    /**
     *關閉資源
     * @param conn 連線物件
     * @param ps 資料庫操作物件
     * @param rs 結果集
     */
    public static void close(ResultSet rs, Statement ps, Connection conn){
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


//
//    private String getConnectionUrl(){
//        return url+serverName+":"+portNumber+";databaseName="+databaseName+";user="+userName+";password="+password+";";
//    }
//
//    private java.sql.Connection getConnection(){
//        try{
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            con = DriverManager.getConnection(getConnectionUrl());
//            if(con!=null) {
//                System.out.println("Connection Successful!");
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//            System.out.println("Error Trace in getConnection() : " + e.getMessage());
//        }
//        return con;
//    }
//
//     /*
//          Display the driver properties, database details
//     */
//
//    public void displayDbProperties(){
//        java.sql.DatabaseMetaData dm = null;
//        java.sql.ResultSet rs = null;
//        try{
//            con= this.getConnection();
//            if(con!=null){
//                dm = con.getMetaData();
//                System.out.println("Driver Information");
//                System.out.println("\tDriver Name: "+ dm.getDriverName());
//                System.out.println("\tDriver Version: "+ dm.getDriverVersion ());
//                System.out.println("\nDatabase Information ");
//                System.out.println("\tDatabase Name: "+ dm.getDatabaseProductName());
//                System.out.println("\tDatabase Version: "+ dm.getDatabaseProductVersion());
//                System.out.println("Avalilable Catalogs ");
//                rs = dm.getCatalogs();
//                while(rs.next()){
//                    System.out.println("\tcatalog: "+ rs.getString(1));
//                }
//                rs.close();
//                rs = null;
//            }else {
//                System.out.println("Error: No active Connection");
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        finally {
//            closeConnection();
//        }
//        dm=null;
//    }
//
//    public void test_insert(){
//        java.sql.Statement stmt = null;
//        String str_sql = "";
//        java.text.SimpleDateFormat df = new java.text.SimpleDateFormat();
//        ResultSet rs;
//        try{
//            con= this.getConnection();
//            stmt = con.createStatement();
//            df.applyPattern("yyyy-MM-dd HH:mm:ss");
//
//            if(con!=null){
//                String sql = "CREATE TABLE REGISTRATION " +
//                        "(id INTEGER not NULL, " +
//                        " first VARCHAR(255), " +
//                        " last VARCHAR(255), " +
//                        " age INTEGER, " +
//                        " PRIMARY KEY ( id ))";
//                stmt.executeUpdate(sql);
//                java.util.Date now = new java.util.Date();
//                System.out.println("Inserted Successful:" + df.format(now));
//            }else {
//                System.out.println("Error: No active Connection");
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        finally {
//            if(stmt != null){
//                try{
//                    stmt.close();
//                }
//                catch(SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//            closeConnection();
//        }
//    }
//
//    private void closeConnection(){
//        try{
//            if(con!=null) {
//                con.close();
//            }
//            con=null;
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }
//    public static void main(String[] args) throws Exception
//    {
//        JdbcUtil myDbTest = new JdbcUtil();
//        myDbTest.displayDbProperties();
//        myDbTest.test_insert();
//    }
}