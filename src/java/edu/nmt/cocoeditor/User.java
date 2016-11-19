/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.cocoeditor;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * A user's wrapper. Provides interface inter user data
 * @author Skyler
 */
public class User extends DBAccessor {
    
    private String userID;
    
    public User(String userID) {
           this.userID = userID;
    }
    
    public void submit(String sessionID, String alias) {
        Connection c = getConnection();
        if (c == null) {
            CoCoEditor.instance.printError("Failed to open session connection");
            return;
        }
        
        Statement statement = null;
        
        try {
            statement = c.createStatement();
            String query = "INSERT INTO cocousers "
                    + "VALUES ('" + userID + "', '" + sessionID + "', '" 
                    + alias + "');";
            

            statement.executeQuery(query);
            
        } catch (SQLException e) {
            e.printStackTrace();
            CoCoEditor.instance.printError("Failed in query operations");
            return;
        }
        
        
        try {
            if (statement != null)
                statement.close();
            
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
            CoCoEditor.instance.printError("Failed to close database connection");
            return;
        }
    }
    
    public String getAlias() {
        Connection c = getConnection();
        if (c == null) {
            CoCoEditor.instance.printError("Failed to open session connection");
            return null;
        }
        
        Statement statement = null;
        String text;
        
        try {
            statement = c.createStatement();
            String query = "SELECT alias "
                    + "FROM cocousers "
                    + "WHERE userId = " + userID + ";";
            

            ResultSet rs = statement.executeQuery(query);
            if (!rs.next()) {
                CoCoEditor.instance.printError("Invalid session ID");
                text = null;
            } else
                text = rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
            CoCoEditor.instance.printError("Failed in query operations");
            return null;
        }
        
        
        try {
            if (statement != null)
                statement.close();
            
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
            CoCoEditor.instance.printError("Failed to close database connection");
            return null;
        }
        return text;
    }
    
    public Date getSessionID() {
        Connection c = getConnection();
        if (c == null) {
            CoCoEditor.instance.printError("Failed to open session connection");
            return null;
        }
        
        Statement statement = null;
        Date date;
        
        try {
            statement = c.createStatement();
            String query = "SELECT sessionId "
                    + "FROM cocousers "
                    + "WHERE userId = " + userID + ";";
            

            ResultSet rs = statement.executeQuery(query);
            if (!rs.next()) {
                CoCoEditor.instance.printError("Invalid session ID");
                date = null;
            } else
                date = rs.getDate(1);
        } catch (SQLException e) {
            e.printStackTrace();
            CoCoEditor.instance.printError("Failed in query operations");
            return null;
        }
        
        
        try {
            if (statement != null)
                statement.close();
            
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
            CoCoEditor.instance.printError("Failed to close database connection");
            return null;
        }
        return date;
    }
    
}
