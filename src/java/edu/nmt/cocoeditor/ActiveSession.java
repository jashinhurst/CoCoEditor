/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.cocoeditor;

import java.sql.Connection;
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
    
    public String getText() {
        Connection c = getConnection();
        if (c == null) {
            CoCoEditor.instance.printError("Failed to open session connection");
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
                CoCoEditor.instance.printError("Invalid session ID");
                return null;
            }
            
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
    
    public String getLastModified() {
        Connection c = getConnection();
        if (c == null) {
            CoCoEditor.instance.printError("Failed to open session connection");
            return null;
        }
        
        Statement statement = null;
        String text;
        
        try {
            statement = c.createStatement();
            String query = "SELECT lastModified "
                    + "FROM cocosessions "
                    + "WHERE sessionId = " + sessionID + ";";
            

            ResultSet rs = statement.executeQuery(query);
            if (!rs.next()) {
                CoCoEditor.instance.printError("Invalid session ID");
                return null;
            }
            
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
    
}
