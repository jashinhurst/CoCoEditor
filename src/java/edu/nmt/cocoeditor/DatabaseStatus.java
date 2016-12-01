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
 *
 * @author Skyler
 */
public class DatabaseStatus extends DBAccessor {
    
    private static DatabaseStatus instance = null;
    
    public static DatabaseStatus instance() {
        if (instance == null)
            instance = new DatabaseStatus();
        
        return instance;
    }
    
    private DatabaseStatus() {
        
    }
    
    public boolean hasSession(String sessionID) {
        Connection c = getConnection();
        if (c == null) {
            CoCoEditor.printError("Failed to open session connection");
            return false;
        }
        
        Statement statement = null;
        boolean exists;
        
        try {
            statement = c.createStatement();
            String query = "SELECT text "
                    + "FROM cocosessions "
                    + "WHERE " + AttributeNames.SESSION_ID
                    + "= '" + sessionID + "';";
            

            ResultSet rs = statement.executeQuery(query);
            if (!rs.next()) {
                exists = false;
            } else 
                exists = true;
            
            
        } catch (SQLException e) {
            CoCoEditor.printError("DB Gen Access failed:");
            CoCoEditor.printError(e.getMessage());
            CoCoEditor.printError("Failed in query operations");
            return false;
        }
        
        
        try {
            if (statement != null)
                statement.close();
            
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
            CoCoEditor.printError("Failed to close database connection");
            return false;
        }
        return exists;
    }
}
