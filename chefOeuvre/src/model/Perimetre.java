/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Point;

/**
 *
 * @author mundial
 */
public class Perimetre {
    
    private Point haut; 
    private Point bas; 
    private Point gauche; 
    private Point droit;

    public Perimetre(Point haut, Point bas, Point gauche, Point droit) {
        this.haut = haut;
        this.bas = bas;
        this.gauche = gauche;
        this.droit = droit;
    }

    public Point getHaut() {
        return haut;
    }

    public void setHaut(Point haut) {
        this.haut = haut;
    }

    public Point getBas() {
        return bas;
    }

    public void setBas(Point bas) {
        this.bas = bas;
    }

    public Point getGauche() {
        return gauche;
    }

    public void setGauche(Point gauche) {
        this.gauche = gauche;
    }

    public Point getDroit() {
        return droit;
    }

    public void setDroit(Point droit) {
        this.droit = droit;
    }
    
    
}
