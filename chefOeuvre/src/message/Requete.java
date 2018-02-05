/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message;

import java.util.Date;
import model.Secteur;

/**
 *
 * @author mundial
 */

 // message  où un avion demande de traverser un secteur 
public class Requete {
    
   private Secteur secteur; 
   private Requete requete;
   private Date heure;

    public Requete(Secteur secteur, Requete requete, Date heure) {
        this.secteur = secteur;
        this.requete = requete;
        this.heure = heure;
    }

    public Secteur getSecteur() {
        return secteur;
    }

    public void setSecteur(Secteur secteur) {
        this.secteur = secteur;
    }

    public Requete getRequete() {
        return requete;
    }

    public void setRequete(Requete requete) {
        this.requete = requete;
    }

    public Date getHeure() {
        return heure;
    }

    public void setHeure(Date heure) {
        this.heure = heure;
    }
   
   
}
