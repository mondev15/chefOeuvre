package ivy;

import test.*;
import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Plane;
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

        //---connexion au bus
        try {
            bus.start("127.255.255.255:2010");
        } catch (IvyException ex) {
            Logger.getLogger(IvyManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        //---PlnEvent
        try {
            //Plane{Flight=352, CallSign=DIWAL, X=0.0, Y=0.0, Vx=0.0, Vy=0.0, GroundSpeed=258, Dep=LEBB, Arr=EDLN}
            bus.bindMsg("PlnEvent Flight=(.*)", new IvyMessageListener() {
                @Override
                public void receive(IvyClient client, String[] args) {
                    //CREATE PLANE
                    //--- plane
                    Plane p = new Plane();
                    String splitted[] = args[0].split(" ");
                    //---    
                    p.setFlight(splitted[0]);
                    p.setCallSign(splitted[2].split("=")[1]);
                    p.setSpeed(Integer.parseInt(splitted[5].split("=")[1]));
                    p.setDep(splitted[7].split("=")[1]);
                    p.setArr(splitted[8].split("=")[1]);
                    radar.addPlaneToRadar(p);
                    System.out.println(p.toString());
                    //---affichage de la de liste de plane dans radar
                    System.out.println("\nradar : " + radar.getPlaneList().toString());
                }
            });

            //---TrackMovedEvent
            bus.bindMsg("TrackMovedEvent Flight=(.*)", new IvyMessageListener() {
                @Override
                public void receive(IvyClient client, String[] args) {
                    //---
                    String splitted[] = args[0].split(" ");
                    //UPDATE PLANE (x, y, vx, vy, groundSpeed)
                    /*System.out.println("\n------------------TrackMovedEvent------------------");
                    System.out.print("\nFlight = " + splitted[0]);
                    System.out.print("\t" + splitted[1]);
                    System.out.print("\t" + splitted[2]);
                    System.out.print("\t" + splitted[3]);
                    System.out.print("\t" + splitted[4]);
                    System.out.print("\t" + splitted[5]);
                    System.out.print("\t" + splitted[6]);
                    System.out.print("\t" + splitted[7]);
                    System.out.print("\t" + splitted[8]);
                    System.out.print("\t" + splitted[12]);*/
                }
            });

            //---SectorEvent
            bus.bindMsg("SectorEvent Flight=(.*)", new IvyMessageListener() {
                @Override
                public void receive(IvyClient client, String[] args) {
                    //---
                    //UPDATE PLANE (sector in, sector out)
                    String splitted[] = args[0].split(" ");
                    /*System.out.print("\nFlight = " + splitted[0]);
                    System.out.print("\t" + splitted[1]);
                    System.out.print("\t" + splitted[2]);*/
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
