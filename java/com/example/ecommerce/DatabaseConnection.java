package com.example.ecommerce;

import java.sql.*;

public class DatabaseConnection {
        Connection con = null;
        String sqlURL = "jdbc:mysql://localhost:3306/ecommerce?useSSL=false";
        String userName = "root";
        String password = "anilindana";
        DatabaseConnection() throws SQLException {
                con = DriverManager.getConnection(sqlURL,userName,password);
                if(con != null) System.out.println("DataBase connected!");
        }

        public ResultSet executeQuery(String query) throws SQLException {
                Statement statement = con.createStatement();
                ResultSet res = statement.executeQuery(query);
                return res;
        }
        public int executeUpdate(String query) throws SQLException {
                int rows = 0;
                Statement statement = con.createStatement();
                rows = statement.executeUpdate(query);
                return rows;
        }
}
