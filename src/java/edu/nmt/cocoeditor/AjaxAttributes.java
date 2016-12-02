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
public enum AjaxAttributes {
    TEXT_TEXT("text"),
    DELETE_COUNT("count"),
    MOVE_POS("pos");
    
    private String key;
    
    private AjaxAttributes(String key) {
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
