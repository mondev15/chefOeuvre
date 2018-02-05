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

// message où le contrôle donne un ordre de changement de cap ou de niveau
public class Ordre {
    
    private Secteur secteur;
    private String ordre;
    private Date heure;

    public Ordre(Secteur secteur, String ordre, Date heure) {
        
        this.secteur = secteur;
        this.ordre = ordre;
        this.heure = heure;
    }

    public Secteur getSecteur() {
        return secteur;
    }

    public void setSecteur(Secteur secteur) {
        this.secteur = secteur;
    }

    public String getOrdre() {
        return ordre;
    }

    public void setOrdre(String ordre) {
        this.ordre = ordre;
    }

    public Date getHeure() {
        return heure;
    }

    public void setHeure(Date heure) {
        this.heure = heure;
    }
    
    

}
