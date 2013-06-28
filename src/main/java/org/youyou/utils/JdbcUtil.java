package org.youyou.utils;


import java.sql.*;
import java.util.Properties;

public class JdbcUtil {
    private static Properties config = new Properties();

    static {
        try {
            config.load(JdbcUtil.class.getResourceAsStream("/jdbc.properties"));
            String driver = config.getProperty("driverName");
            Class.forName(driver);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(config.getProperty("url"), config.getProperty("user"), config.getProperty("password"));
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return con;
    }

    public static void close(ResultSet rs, Statement stmt, Connection con) {
        try {
            if (rs != null) rs.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            if (stmt != null) stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            if (con != null) con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

