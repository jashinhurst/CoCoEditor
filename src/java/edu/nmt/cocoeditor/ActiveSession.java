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
 * Stores information about an active session.
 * Provides access to the data a little easier
 * @author Skyler
 */
public class ActiveSession extends DBAccessor {
    
    private String sessionID;
    
    public ActiveSession(String sessionID) {
           this.sessionID = sessionID;
    }
    
    public void submit(String text, Date date) {
        Connection c = getConnection();
        if (c == null) {
            CoCoEditor.printError("Failed to open session connection");
            return;
        }
        
        Statement statement = null;
        
        try {
            statement = c.createStatement();
            String query = "INSERT INTO cocosessions "
                    + "VALUES ('" + sessionID + "', '" + text + "', '" 
                    + date + "');";
            

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
    }
    
    public String getText() {
        Connection c = getConnection();
        if (c == null) {
            CoCoEditor.printError("Failed to open session connection");
            return null;
        }
        
        Statement statement = null;
        String text;
        
        try {
            statement = c.createStatement();
            String query = "SELECT text "
                    + "FROM cocosessions "
                    + "WHERE sessionId = " + sessionID + ";";
            

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
    
    public Date getLastModified() {
        Connection c = getConnection();
        if (c == null) {
            CoCoEditor.printError("Failed to open session connection");
            return null;
        }
        
        Statement statement = null;
        Date date;
        
        try {
            statement = c.createStatement();
            String query = "SELECT lastModified "
                    + "FROM cocosessions "
                    + "WHERE sessionId = " + sessionID + ";";
            

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
