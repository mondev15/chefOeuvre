/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author mundial
 */
    //contiendra les données radar (la liste des secteurs par exemple)
public class Radar {
    
    private ArrayList<Secteur> secteurs;    

    public Radar(ArrayList<Secteur> secteurs) {
        this.secteurs = secteurs;
    }

    public ArrayList<Secteur> getSecteurs() {
        return secteurs;
    }

    public void setSecteurs(ArrayList<Secteur> secteurs) {
        this.secteurs = secteurs;
    }
    
    
}
