/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;

/**
 *
 * @author mundial
 */
    //Un secteur est caractérisé par un périmètre et une fréquence
public class Secteur {
    
    private String frequence;
    private Perimetre perimetre;
    private List<Vol> vols;

    public Secteur(String frequence, Perimetre perimetre, List<Vol> vols) {
        this.frequence = frequence;
        this.perimetre = perimetre;
        this.vols = vols;
    }

    public String getFrequence() {
        return frequence;
    }

    public void setFrequence(String frequence) {
        this.frequence = frequence;
    }

    public Perimetre getPerimetre() {
        return perimetre;
    }

    public void setPerimetre(Perimetre perimetre) {
        this.perimetre = perimetre;
    }

    public List<Vol> getVols() {
        return vols;
    }

    public void setVols(List<Vol> vols) {
        this.vols = vols;
    }
    
    
    
}
