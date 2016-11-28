/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.cocoeditor;

/**
 *
 * @author Skyler
 */
public enum AttributeNames {
    SESSION_ID("sid"),
    ALIAS_ID("alias"),
    USER_ID("uid");
    
    private String key;
    
    private AttributeNames(String key) {
        this.key = key;
    }
    
    @Override
    public String toString() {
        return key;
    }
    
    public String getKey() {
        return key;
    }
}
