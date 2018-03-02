/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ivy;

import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Plane;
import view.Radar;

/**Vérifier si 
 * PlnFlightEvent,
 * TrackMovedEvent
 * SectorEvent 
 * ont le même numéro de flight avant de faire des mises  à jour des position et vitesse
*/
public class IvyManager {

    private Ivy bus;
    private Radar radar;

    public IvyManager() {

    radar = new Radar();
    bus = new Ivy("IvyManager", "IvyManager CONNECTED", null);

        //---Connexion au bus
        try {
            bus.start("127.255.255.255:2010");
        } catch (IvyException ex) {
            Logger.getLogger(IvyManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        //---ClockEvent
        try {
            bus.bindMsg("ClockEvent Time=(.*)", new IvyMessageListener() {
                @Override
                public void receive(IvyClient client, String[] args) {
                    
                }
            });
        } catch (IvyException ex) {
            Logger.getLogger(IvyManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String args[]) {

        IvyManager ivyTest = new IvyManager();
    }

}