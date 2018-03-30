/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Defines the very basic of time property
 * 
 * @author Charlelie
 */
public interface IBlock{
        
//    public SimpleStringProperty stateProperty();
    
    public void setTime(int s);
    
    public IntegerProperty timeProperty();
        
}
