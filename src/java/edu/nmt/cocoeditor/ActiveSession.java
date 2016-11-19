/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.cocoeditor;

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
    
    
}
