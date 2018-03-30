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
import model.Route;
import view.RadarView;
import java.awt.geom.Point2D;
import javafx.scene.paint.Color;
import model.TimeFunctions;
import view.Timeline;

/**
 * Creates the RadarView (ND), the Timeline and the IVY bus connecting them.
 * 
 * @author Evergiste
 */

public class IvyManager {

    private static Ivy bus;
    private static RadarView radar;
    private Timeline timeline;

    public IvyManager() {

        radar = new RadarView();
        bus = new Ivy("IvyManager", "IvyManager CONNECTED", null);
        timeline = new Timeline();

        try {
            //---Connexion au bus ivy
            bus.start("127.255.255.255:2010");

            bus.bindMsg("FileReadEvent Type=REJEU Name=(.+) StartTime=(.*) EndTime=(.*)", new IvyMessageListener() {
                @Override
                public void receive(IvyClient client, String[] args) {
                    timeline.totalStartTimeProperty().set(TimeFunctions.hmsToInt(args[1]));
                    timeline.totalEndTimeProperty().set(TimeFunctions.hmsToInt(args[2]));
                }
            });
            
            bus.bindMsg("ClockEvent Time=(.+) Rate=(.+) Bs=.*", new IvyMessageListener() {
                @Override
                public void receive(IvyClient client, String[] args) {
//                    System.out.println("setClock time :"+TimeFunctions.hmsToInt(args[0]));
//                     System.out.println("integer :"+(args[1]));
                    timeline.setClockTime(TimeFunctions.hmsToInt(args[0]), (int)Double.parseDouble(args[1]));
                }
            });
            
            //---Selection depuis twinkle de l'avion surlequel la vue est centrée 
            bus.bindMsg("SelectionEvent acc.*Flight=(.*)", new IvyMessageListener() {
                @Override
                public void receive(IvyClient client, String[] args) {
                    Plane p = radar.getCentralPlane();

                    String b = Boolean.toString(radar.isCentralPlaneExists());
                    switch (b) {
                        case "false":
                            if (p.getFlight().equals("default_Flight")) {
                                p.setFlight(args[0]);
                                Plane copy = radar.getPlanes().get(Integer.parseInt(args[0]));
                                p.setCallSign(copy.getCallSign());
                                p.setTwinklePosition(copy.getTwinklePosition());;
                                p.setRoute(copy.getRoute());
                                p.setAfl(copy.getAfl());
                                p.setRate(copy.getRate());
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
                            } else {
                                p.setFlight(args[0]);
                                p.setFlight(args[0]);
                                Plane copy = radar.getPlanes().get(Integer.parseInt(args[0]));
                                p.setCallSign(copy.getCallSign());
                                p.setTwinklePosition(copy.getTwinklePosition());;
                                p.setRoute(copy.getRoute());
                                p.setAfl(copy.getAfl());
                                p.setRate(copy.getRate());
                                p.setHeading(copy.getHeading());
                                p.setSpeed(copy.getSpeed());
                                radar.getPlanes().remove(Integer.parseInt(args[0]));
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        radar.addCentralPlane();
                                    }
                                });
                            }
                            //radar.setCentralPlaneExists(true);
                            break;
                        case "true":
                            /*Plane copy = radar.getPlanes().get(Integer.parseInt(args[0]));
                            radar.setCentralPlane(copy);
                            radar.getPlanes().remove(Integer.parseInt(args[0]));
                            //---updating  radarview
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    radar.removeAllPlanes();
                                    radar.addCentralPlane();
                                    for (Map.Entry<Integer, Plane> entry : radar.getPlanes().entrySet()) {
                                        Plane plane = entry.getValue();
                                        radar.addPlane(plane);
                                    }
                                }
                            });
                            */
                            break;
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
                    if (args[0].equals(radar.getCentralPlane().getFlight())) {
                        Plane p = radar.getCentralPlane();
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
                    /*System.out.println("---------pln event-------------");
                    System.out.println(radar.getCentralPlane());
                    System.out.println(radar.getPlanes().toString());*/
                }

            });

            //---TrackMovedEvent
            bus.bindMsg("TrackMovedEvent Flight=(.*) CallSign=(.*) Ssr.*Sector=(.*) Layers.*X=(.*) Y=(.*) Vx=(.*) Vy=(.*) Afl=(.*) Rate=(.*) Heading=(.*) GroundSpeed=(.*) Tendency=(.*) Time=(.*)", new IvyMessageListener() {
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
                    double x = Double.parseDouble(args[3]);
                    double y = Double.parseDouble(args[4]);
                    double vx = Double.parseDouble(args[5]);
                    double vy = Double.parseDouble(args[6]);
                    int afl = Integer.parseInt(args[7]);
                    int rate = Integer.parseInt(args[8]);
                    double heading = Double.parseDouble(args[9]);
                    int speed = Integer.parseInt(args[10]);
                    int tendency = Integer.parseInt(args[11]);
                    String time = args[12];

                    //--- avion sur lequel la vue est centrée
                    if (args[0].equals(radar.getCentralPlane().getFlight())) {
                        Plane p = radar.getCentralPlane();
                        p.setCallSign(callSign);
                        p.setTime(time);
                        p.setTwinklePosition(new Point2D.Double(x, y));
                        p.setSector(sector);
                        p.setVx(vx);
                        p.setVy(vy);
                        p.setAfl(afl);
                        p.setRate(rate);
                        p.setHeading(heading);
                        p.setSpeed(speed);
                        p.setTendency(tendency);
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
                            Plane p = new Plane(flight, callSign, (new Point2D.Double(x, y)), vx, vy);
                            p.setSector(sector);
                            p.setAfl(afl);
                            p.setRate(rate);
                            p.setHeading(heading);
                            p.setSpeed(speed);
                            p.setTendency(tendency);
                            p.setTime(time);
                            map.put(key, p);
                            //---updating  radarview
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    radar.addPlane(p);
                                }
                            });
                        } else {
                            //---si le plane existe, on le met à jour
                            Plane p = radar.getPlanes().get(key);
                            p.setSector(args[2]);
                            p.setTwinklePosition(new Point2D.Double(x, y));
                            p.setCallSign(callSign);
                            p.setVx(vx);
                            p.setVy(vy);
                            p.setAfl(afl);
                            p.setRate(rate);
                            p.setHeading(heading);
                            p.setSpeed(speed);
                            p.setTendency(tendency);
                            p.setTime(time);
                            //---updating  radarview
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    radar.addPlane(p);
                                }
                            });
                        }
                    }
                    /*System.out.println("---------trackmoved-------------");
                    System.out.println(radar.getCentralPlane());
                    System.out.println(radar.getPlanes().toString());*/
                }

            });

            bus.bindMsg("ReportEvent.*Result=(.*) Info=(.*) Order=(.*)", new IvyMessageListener() {
                @Override
                public void receive(IvyClient client, String[] args) {
                    System.out.println(args[2]);
                    String splitted[] = args[2].split("\\|");
                    System.out.println(splitted[0]);
                    System.out.println(splitted[1]);
                    System.out.println(splitted[2]);
                    double value;
                    Plane p = radar.getCentralPlane();
                    switch (args[0]) {
                        case "OK":
                            //System.err.println("ok");
                            if (args[2].contains("AircraftHeading")) {
                                radar.getTopAxis().getNewValueLabel().setTextFill(Color.GREEN);
                            } else if (args[2].contains("AircraftSpeed")) {
                                radar.getLeftAxis().getNewValueLabel().setTextFill(Color.GREEN);
                            } else if (args[2].contains("AircraftLevel")) {
                                radar.getRightAxis().getNewValueLabel().setTextFill(Color.GREEN);
                            }

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    radar.addCentralPlane();
                                }
                            });
                            break;
                        case "ERROR":
                            System.err.println("error");
                            if (args[2].contains("AircraftHeading")) {
                                radar.getTopAxis().getNewValueLabel().setTextFill(Color.RED);
                            } else if (args[2].contains("AircraftSpeed")) {
                                radar.getLeftAxis().getNewValueLabel().setTextFill(Color.RED);
                            } else if (args[2].contains("AircraftLevel")) {
                                radar.getRightAxis().getNewValueLabel().setTextFill(Color.RED);
                            }
                    }
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
    
    public static void setClockTime(int time){
        try {
            bus.sendMsg("SetClock Time=" + TimeFunctions.intTohms(time));
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

    public static void setNewHeading(double newHeading) {
        try {
            int h = (int) newHeading;
            bus.sendMsg("AircraftHeading Flight=" + radar.getCentralPlane().getFlight() + " To=" + h);
        } catch (IvyException ex) {
            Logger.getLogger(IvyManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void setNewSpeed(double newSpeed) {
        try {
            int s = (int) newSpeed;
            bus.sendMsg("AircraftSpeed Flight=" + radar.getCentralPlane().getFlight() + " Type=GS Value=" + s);
        } catch (IvyException ex) {
            Logger.getLogger(IvyManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void setNewLevel(double newLevel) {
        try {
            int s = (int) newLevel;
            bus.sendMsg("AircraftLevel Flight=" + radar.getCentralPlane().getFlight() + " Fl=" + s);
        } catch (IvyException ex) {
            Logger.getLogger(IvyManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Timeline getTimeline(){
        return timeline;
    }
}
