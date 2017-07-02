/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kisoki.passcheck.functions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;

/**
 *
 * @author nwneal
 */
public class checkPassword {
    
    public static boolean comparePass(String pass) throws ServletException {
        // connect to database and check row...
        String sqlStmt = "SELECT COUNT(*) AS pcount FROM password_list WHERE pass=?";
        String url = System.getProperty("passcheck.pass_db");
        
        try {
            // Load the class that provides the new "jdbc:google:mysql://" prefix.
            Class.forName("com.mysql.jdbc.GoogleDriver");
        } catch (ClassNotFoundException e) {
            throw new ServletException("Error loading Google JDBC Driver", e);
        }
        
        boolean result = false;
        int count = 0;
        try (Connection conn = DriverManager.getConnection(ZosCrypt.decryptDB(url));PreparedStatement stmt = conn.prepareStatement(sqlStmt)) {
            stmt.setString(1, pass);
            try(ResultSet rs = stmt.executeQuery()) {
                while(rs.next()) {
                    count = rs.getInt("pcount");
                    
                }
                rs.close();
            }
            stmt.close();
            conn.close();
            
        }  catch (SQLException e) {
            throw new ServletException("SQL error", e);
        }
        
        if (count != 0) {
            result = true;
        }
        
        return result;
    }
    
    public static boolean uploadNewPass(List<String> pass) throws ServletException {
        String updateStmt = "INSERT INTO password_list VALUES ";
        for (int i = 0; i < pass.size()-1; i++) {
            updateStmt += "(?,1),";
        }
        updateStmt += "(?,1) ON DUPLICATE KEY UPDATE list_count=list_count+1";
        
        String url = System.getProperty("passcheck.pass_db");
        
        try {
            // Load the class that provides the new "jdbc:google:mysql://" prefix.
            Class.forName("com.mysql.jdbc.GoogleDriver");
        } catch (ClassNotFoundException e) {
            throw new ServletException("Error loading Google JDBC Driver", e);
        }

        try (Connection conn = DriverManager.getConnection(ZosCrypt.decryptDB(url));) {
            PreparedStatement stmt = conn.prepareStatement(updateStmt);
            for (int i = 0; i < pass.size(); i++) {
                stmt.setString((i+1), pass.get(i));
            }
            stmt.execute();
            stmt.close();
            conn.close();
        }  catch (SQLException e) {
            //throw new ServletException("SQL error", e);
            return false;
        }
        return true;
    }
    
    public static int grabPassCount(String pass) throws ServletException {
        // connect to database and check row...
        String sqlStmt = "SELECT list_count FROM password_list WHERE pass=?";
        String url = System.getProperty("passcheck.pass_db");
        
        try {
            // Load the class that provides the new "jdbc:google:mysql://" prefix.
            Class.forName("com.mysql.jdbc.GoogleDriver");
        } catch (ClassNotFoundException e) {
            throw new ServletException("Error loading Google JDBC Driver", e);
        }

        int count = 0;
        try (Connection conn = DriverManager.getConnection(ZosCrypt.decryptDB(url));PreparedStatement stmt = conn.prepareStatement(sqlStmt)) {
            stmt.setString(1, pass);
            try(ResultSet rs = stmt.executeQuery()) {
                while(rs.next()) {
                    count = rs.getInt("list_count");
                }
                rs.close();
            }
            stmt.close();
            conn.close();
        }  catch (SQLException e) {
            throw new ServletException("SQL error", e);
        }
        
        return count;
    }
    
//    public static void updatePassCount(String pass) throws ServletException {
//        String updateStmt = "UPDATE password_list SET list_count=list_count+1 WHERE pass=? LIMIT 1";
//        String url = System.getProperty("passcheck.pass_db");
//        
//        try {
//            // Load the class that provides the new "jdbc:google:mysql://" prefix.
//            Class.forName("com.mysql.jdbc.GoogleDriver");
//        } catch (ClassNotFoundException e) {
//            throw new ServletException("Error loading Google JDBC Driver", e);
//        }
//
//        try (Connection conn = DriverManager.getConnection(ZosCrypt.decryptDB(url));) {
//            PreparedStatement stmt = conn.prepareStatement(updateStmt);
//            stmt.setString(1, pass);
//            stmt.execute();
//            stmt.close();
//            conn.close();
//        }  catch (SQLException e) {
//            throw new ServletException("SQL error", e);
//        }
//    }
    
}
