package ivy;

import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Plane;
import view.RadarView;

public class IvyManager {

    private Ivy bus;
    private RadarView radarView;

    public IvyManager() {

        radarView = new RadarView();
        bus = new Ivy("IvyManager", "IvyManager CONNECTED", null);

        //---connexion au bus
        try {
            bus.start("127.255.255.255:2010");
        } catch (IvyException ex) {
            Logger.getLogger(IvyManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        //---PlnEvent
        try {

            bus.bindMsg("PlnEvent Flight=(.*) Time=(.*) CallSign=(.*) AircraftType.*Speed=(.*) Rfl.*Dep=(.*) Arr=(.*) Rvsm.*List=(.*)", new IvyMessageListener() {
                @Override
                public void receive(IvyClient client, String[] args) {
                    Map<Integer, Plane> map = radarView.getPlanesMap();
                    int key = Integer.parseInt(args[0]);
                    //---on crée un plane s'il existe pas dans la map
                    if (!map.containsKey(key)) {
                        Plane p = new Plane();
                        p.setFlight(args[0]);
                        p.setTime(args[1]);
                        p.setCallSign(args[2]);
                        p.setSpeed(Integer.parseInt(args[3]));
                        p.setDep(args[4]);
                        p.setArr(args[5]);
                        p.setList(args[6]);
                        map.put(key, p);
                    }
                    System.out.println("---------pln event-------------");
                    System.out.println(map.toString());
                }
            });

            //---TrackMovedEvent
            bus.bindMsg("TrackMovedEvent Flight=(.*) CallSign=(.*) Ssr.*Sector=(.*) Layers.*X=(.*) Y=(.*) Vx=(.*) Vy=(.*) Afl.*GroundSpeed=(.*) Tendency.*Time=(.*)", new IvyMessageListener() {
                @Override
                public void receive(IvyClient client, String[] args) {

                    //---CREER PLANE
                    //---VERIFIER  SI LE PLANE N'EXISTE PAS DEJA AVANT DE LE CREER
                    Map<Integer, Plane> map = radarView.getPlanesMap();
                    int key = Integer.parseInt(args[0]);
                    //---on crée un plane s'il existe pas dans la map
                    String flight = args[0]; 
                    String callSign = args[1];
                    float x = Float.parseFloat(args[3]);
                    float y = Float.parseFloat(args[4]);
                    float vx = Float.parseFloat(args[5]);
                    float vy = Float.parseFloat(args[6]);
                    int speed = Integer.parseInt(args[7]);
                    String time = args[8];
                    //---
                    if (!map.containsKey(key)) {
                        Plane p = new Plane(flight, callSign, x, y, vx, vy);
                        p.setSector(args[2]);
                        p.setSpeed(speed);
                        p.setTime(time);
                        map.put(key, p);
                    } else {
                        //---si le plane existe, on le met à jour
                        Plane p = map.get(key);
                        p.setSector(args[2]);
                        p.setX(x);
                        p.setY(y);
                        p.setVx(vx);
                        p.setVy(vy);
                        p.setSpeed(speed);
                        p.setTime(time);
                        map.put(key, p);
                    }
                    System.out.println("---------trackmoved-------------");
                    System.out.println(map.toString());
                }
            });

            //---SectorEvent
            /*bus.bindMsg("SectorEvent Flight=(.*) Sector_Out=(.*) Sector_In=(.*)", new IvyMessageListener() {
                @Override
                public void receive(IvyClient client, String[] args) {
                    //---
                    //UPDATE PLANE Sector
                      Map<Integer, Plane> map = radarView.getPlanesMap();
                     int key = Integer.parseInt(args[0]);
                     Plane p = map.get(key);
                    String splitted[] = args[0].split(" ");
                    System.out.print("\nFlight = " + splitted[0]);
                    System.out.print("\t" + splitted[1]);
                    System.out.print("\t" + splitted[2]);
                }
            });*/
        } catch (IvyException ex) {
            Logger.getLogger(IvyManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String args[]) {

        IvyManager ivyTest = new IvyManager();
    }

}
