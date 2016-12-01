/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.cocoeditor;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * A user action wrapper class
 * @author Skyler
 */
public class UserAction extends DBAccessor {
    
    private final String actionID;
    private final String userID;
    private final int delta;
    private final String text;
    private final Date timestamp;

    public UserAction(String actionID, String userID, int delta, String text,
            Date timestamp) {
        this.actionID = actionID;
        this.userID = userID;
        this.delta = delta;
        this.text = text;
        this.timestamp = timestamp;
    }
    
    /**
     * Submits the action to the database
     * @param user the cache user to use pos of
     */
    public void submit(User user) {
        Connection c = getConnection();
        if (c == null) {
            CoCoEditor.printError("Failed to open session connection");
            return;
        }
        
        Statement statement;
        
        try {
            statement = c.createStatement();
//            String query = "INSERT INTO cocoactions "
//                    + "VALUES ('" + userID + "', '" + actionID + "', '" 
//                    + alias + "');";
            String query = "INSERT INTO cocoactions "
                    + "VALUES (";
            then(query, userID);
            then(query, actionID);
            then(query, delta + "");
            then(query, user.getPos() + "");
            then(query, text);
            then(query, timestamp.toString());
            
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
    
    
    
}
