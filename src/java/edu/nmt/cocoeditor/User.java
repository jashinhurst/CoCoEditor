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
    private boolean valid;
    
    public User(String userID) {
           this.userID = userID;
           valid = false;
    }
    
    public boolean isValid() {
        return valid;
    }
    
    public void invalidate() {
        valid = false;
    }
    
    /**
     * Submits the user to the database, and marks their cache profile (this)
     * as valid.
     * @param sessionID
     * @param alias 
     */
    public void submit(String sessionID, String alias) {
        Connection c = getConnection();
        if (c == null) {
            CoCoEditor.printError("Failed to open session connection");
            return;
        }
        
        Statement statement = null;
        
        try {
            statement = c.createStatement();
            String query = "INSERT INTO cocousers "
                    + "VALUES ('" + userID + "', '" + sessionID + "', '" 
                    + alias + "');";
            

            statement.executeUpdate(query);
            
        } catch (SQLException e) {
            e.printStackTrace();
            CoCoEditor.printError("Failed in query operations");
            return;
        }
        
        
        try {
            if (statement != null)
                statement.close();
            
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
            CoCoEditor.printError("Failed to close database connection");
            return;
        }
        
        valid = true;
    }
    
    public String getAlias() {
        Connection c = getConnection();
        if (c == null) {
            CoCoEditor.printError("Failed to open session connection");
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
                CoCoEditor.printError("Invalid session ID");
                text = null;
            } else
                text = rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
            CoCoEditor.printError("Failed in query operations");
            return null;
        }
        
        
        try {
            if (statement != null)
                statement.close();
            
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
            CoCoEditor.printError("Failed to close database connection");
            return null;
        }
        return text;
    }
    
    public Date getSessionID() {
        Connection c = getConnection();
        if (c == null) {
            CoCoEditor.printError("Failed to open session connection");
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
                CoCoEditor.printError("Invalid session ID");
                date = null;
            } else
                date = rs.getDate(1);
        } catch (SQLException e) {
            e.printStackTrace();
            CoCoEditor.printError("Failed in query operations");
            return null;
        }
        
        
        try {
            if (statement != null)
                statement.close();
            
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
            CoCoEditor.printError("Failed to close database connection");
            return null;
        }
        return date;
    }
    
}
