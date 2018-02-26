package ivy;

import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import model.Plane;
import model.Position;
import model.Route;
import view.RadarView;

public class IvyManager {

    private static Ivy bus;
    private RadarView radar;

    public IvyManager() {

        radar = new RadarView();
        bus = new Ivy("IvyManager", "IvyManager CONNECTED", null);

        try {
            //---Connexion au bus ivy
            bus.start("127.255.255.255:2010");

            //---Selection de l'avion surlequel la vue est centrée
            bus.bindMsg("SelectionEvent acc.*Flight=(.*)", new IvyMessageListener() {
                @Override
                public void receive(IvyClient client, String[] args) {
                    Plane p = radar.getPlane();
                    //---TO DO
                    //--- DONNER LA POSSIBILITE DE CHANGER D'AVION
                    if (p.getFlight().equals("default")) 
                    {
                        p.setFlight(args[0]);
                        Plane copy = radar.getPlanes().get(Integer.parseInt(args[0]));
                        p.setCallSign(copy.getCallSign());
                        p.setPosition(copy.getPosition());
                        p.setRoute(copy.getRoute());
                        p.setAfl(copy.getAfl());
                        p.setHeading(copy.getHeading());
                        p.setSpeed(copy.getSpeed());
                        //on supprime l'avion de la map
                        radar.getPlanes().remove(Integer.parseInt(args[0]));
                        //---updating  radarview
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                radar.addCentralPlane();
                            }
                        });
                    }
                }
            });

            //---PlnEvent
            bus.bindMsg("PlnEvent Flight=(.*) Time=(.*) CallSign=(.*) AircraftType.*Speed=(.*) Rfl.*Dep=(.*) Arr=(.*) Rvsm.*List=(.*)", new IvyMessageListener() {
                @Override
                public void receive(IvyClient client, String[] args) {
                    Map<Integer, Plane> map = radar.getPlanes();
                    int key = Integer.parseInt(args[0]);
                    //---on crée un plane s'il n'existe pas dans la map
                    if (args[0].equals(radar.getPlane().getFlight())) {
                        Plane p = radar.getPlane();
                        p.setTime(args[1]);
                        p.setCallSign(args[2]);
                        p.setSpeed(Integer.parseInt(args[3]));
                        p.setRoute(new Route(args[4], args[5], args[6])); //args[4] : dep, args[5]arr, args[6]: list 
                    } else {
                        Plane p = new Plane();
                        p.setFlight(args[0]);
                        p.setTime(args[1]);
                        p.setCallSign(args[2]);
                        p.setSpeed(Integer.parseInt(args[3]));
                        p.setRoute(new Route(args[4], args[5], args[6])); //args[4] : dep, args[5]arr, args[6]: list
                        map.put(key, p);
                    }
                    System.out.println("---------pln event-------------");
                    System.out.println(radar.getPlane());
                    System.out.println(radar.getPlanes().toString());
                }

            });

            //---TrackMovedEvent
            bus.bindMsg("TrackMovedEvent Flight=(.*) CallSign=(.*) Ssr.*Sector=(.*) Layers.*X=(.*) Y=(.*) Vx=(.*) Vy=(.*) Afl=(.*) Rate.*Heading=(.*) GroundSpeed=(.*) Tendency.*Time=(.*)", new IvyMessageListener() {
                @Override
                public void receive(IvyClient client, String[] args) {

                    //---CREER PLANE
                    //---VERIFIER  SI LE PLANE N'EXISTE PAS DEJA AVANT DE LE CREER
                    Map<Integer, Plane> map = radar.getPlanes();
                    int key = Integer.parseInt(args[0]);
                    //---on crée un plane s'il existe pas dans la map
                    String flight = args[0];
                    String callSign = args[1];
                    String sector = args[2];
                    float x = Float.parseFloat(args[3]);
                    float y = Float.parseFloat(args[4]);
                    float vx = Float.parseFloat(args[5]);
                    float vy = Float.parseFloat(args[6]);
                    int afl = Integer.parseInt(args[7]);
                    int heading = Integer.parseInt(args[8]);
                    int speed = Integer.parseInt(args[9]);
                    String time = args[10];

                    //--- avion sur lequel la vue est centrée
                    if (args[0].equals(radar.getPlane().getFlight())) {
                        Plane p = radar.getPlane();
                        p.setCallSign(callSign);
                        p.setTime(time);
                        p.setPosition(new Position(x, y));
                        p.setSector(sector);
                        p.setVx(vx);
                        p.setVy(vy);
                        p.setAfl(afl);
                        p.setHeading(heading);
                        p.setSpeed(speed);
                        //---updating  radarview
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                               radar.addCentralPlane();
                            }
                        });
                    } else {
                        //---l'avion n'existe pas , on le crée et on l'ajoute au map
                        if (!radar.getPlanes().containsKey(key)) {
                            Plane p = new Plane(flight, callSign, new Position(x, y), vx, vy);
                            p.setSector(sector);
                            p.setAfl(afl);
                            p.setHeading(heading);
                            p.setSpeed(speed);
                            p.setTime(time);
                            map.put(key, p);
                        } else {
                            //---si le plane existe, on le met à jour
                            Plane p = radar.getPlanes().get(key);
                            p.setSector(args[2]);
                            p.setPosition(new Position(x, y));
                            p.setCallSign(callSign);
                            p.setVx(vx);
                            p.setVy(vy);
                            p.setAfl(afl);
                            p.setHeading(heading);
                            p.setSpeed(speed);
                            p.setTime(time);
                        }
                    }
                    System.out.println("---------trackmoved-------------");
                    System.out.println(radar.getPlane());
                    System.out.println(radar.getPlanes().toString());
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

    public Ivy getBus() {
        return bus;
    }

    public RadarView getRadarView() {
        return radar;
    }
}
