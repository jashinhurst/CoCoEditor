/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.cocoeditor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Skyler
 */
public abstract class DBAccessor {
    
    private static final String URL = "jdbc:mysql://localhost:3306/apollo8_CoCoEditor";
    
    private static final String USERNAME = "apollo8";
    
    private static final String PASSWORD = "";
    
    protected Connection getConnection() {
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            CoCoEditor.instance.printError("Could not load mysql driver class!");
            return null;
        }
        
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            CoCoEditor.instance.printError("Failed to connect to the DB!");
        }
        
        return con;
    }
    
}

