/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message;

import java.awt.Event;
import java.util.Date;

/**
 *
 * @author mundial
 */

// les messages clock provenant du fichier rejeu
public class ClockEvent {
    
    private Event event;
    private Date heure;

    public ClockEvent(Event event, Date heure) {
        this.event = event;
        this.heure = heure;
    }
    
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Date getHeure() {
        return heure;
    }

    public void setHeure(Date heure) {
        this.heure = heure;
    }
    
}
