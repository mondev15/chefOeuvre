/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mundial
 */
public class Vol {
    
    private int volId;
    private Point positionCourante;
    private double cap;
    private String niveau;
    private int vitesse; 
    //scenario
    private List<Integer> planDeVol;
    
    public Vol(int id){
     volId = id;
     positionCourante = new Point(0,0);
     planDeVol = new ArrayList<Integer>();    
    }

    public int getVolId() {
        return volId;
    }

    public void setVolId(int volId) {
        this.volId = volId;
    }

    public Point getPositionCourante() {
        return positionCourante;
    }

    public void setPositionCourante(Point positionCourante) {
        this.positionCourante = positionCourante;
    }

    public List<Integer> getPlanDeVol() {
        return planDeVol;
    }

    public void setPlanDeVol(List<Integer> planDeVol) {
        this.planDeVol = planDeVol;
    }

    public double getCap() {
        return cap;
    }

    public void setCap(double cap) {
        this.cap = cap;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public int getVitesse() {
        return vitesse;
    }

    public void setVitesse(int vitesse) {
        this.vitesse = vitesse;
    }          
}
