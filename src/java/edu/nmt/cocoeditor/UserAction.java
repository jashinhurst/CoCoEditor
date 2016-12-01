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
            query += "'" + userID + "'";
            query = then(query, actionID);
            query = then(query, delta + "");
            query = then(query, user.getPos() + "");
            query = query = then(query, text);
            query = then(query, timestamp.toString());
            query += ")";
            
            System.out.println("Query: ");
            System.out.println(query);
            
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
