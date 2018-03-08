package fr.enac.ivy;

/**
 * Created by darian on 26/04/17.
 */

import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyException;
import fr.enac.audio.AudioPlayer;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * IvyManager is the class which manage the information send through and with Ivy
 */
public class IvyManager {

	/**
	 * The bus used to send and receive information
	 */
    private Ivy bus;
    
    /**
     * The audio player
     */
    private AudioPlayer audioPlayer;

    /**
     * Stack containing last orders sent
     */
    private ArrayList<Hashtable<String, String>> orderStack;

    /**
     * Creates an instance of IvyManager, where the connection to the bus is done and all bindings are created
     * @param audioPlayer
     * @param bus 
     */
    public IvyManager(AudioPlayer audioPlayer, String busdomain) throws IvyException {
        bus = new Ivy("IvyAtcSynth", "IvyAtcSynth ready", null);
        this.audioPlayer = audioPlayer;
        this.orderStack = new ArrayList<Hashtable<String, String>>();
        this.setOrderBindings("data/order_regex.txt");
        this.setFlightBindings();
        this.setAckBindings();
        bus.start(busdomain);
    }

    /**
     * Binds all regexp written in the file argument
     *
     * @param fileName : path to the file containing regexp
     * @param bus      : Ivy bus to bind
     */
    private void setOrderBindings(String fileName) throws IvyException {

        fr.enac.tools.FileReader regexes = new fr.enac.tools.FileReader(fileName);
        int nb_regex = regexes.lineNumber();
        OrderCallback ordercallback = new OrderCallback(audioPlayer, orderStack);
        for (int i = 0; i < nb_regex; i++) {
        	String regexp = regexes.nextLine();
        	System.out.println(" subscribe to "+regexp);
            bus.bindMsg(regexp, ordercallback);
        }

    }
  
    /**
     * Binds the events of apparition and modification of flight, in order to have an association between a flight
     * id  and a callsign
     *
     * @param bus : Ivy bus to bind
     */
    private void setFlightBindings() throws IvyException {

        FlightCallback flightCallback = new FlightCallback(audioPlayer);
        bus.bindMsg("^TrackMovedEvent (Flight=[0-9]+) (CallSign=[^ ]+) Ssr=[A-Za-z0-9]* Sector=[^ ]+ Layers=[^ ]+ X=[.0-9-]+ Y=[.0-9-]+ (Vx=[.0-9-]+) (Vy=[.0-9-]+) (Afl=[0-9]{2,3}) (Rate=[.0-9-]+) (Heading=[0-9]{2,3}) (GroundSpeed=[.0-9-]+) (Tendency=[A-Za-z0-9.-]+) Time=.*",
                flightCallback);

        bus.bindMsg("^StripEvent (Flight=[0-9]+) (Time=[0-9]{2}:[0-9]{2}:[0-9]{2}+) (CallSign=[^ ]+) (AircraftType=[^ ]+) (Ssr=[A-Za-z0-9]*) (Sector=[^ ]+) (Speed=[0-9]{2,3}) (Rfl=[0-9]{2,3}) (ExitSector=[^ ]+) (Frequency=[0-9]{3}\\.[0-9]{1,3}) (Efl=[0-9]{2,3}) (Tfl=[0-9]{2,3}) (List=.+)",
        		flightCallback);
        
        bus.bindMsg("^TrackDieEvent (Flight=[0-9]+)", new DieCallback()) ; 
    }

    /**
     * Binds the acknowledgment message
     * @param bus the Ivy  bus to bind
     */
    private void setAckBindings() throws IvyException {
        AckCallback ackCallback = new AckCallback(audioPlayer, orderStack);
        bus.bindMsg("^ReportEvent [^ ]* Result=(ERROR|OK|WARNING) Info=([^ ]*) Order=(.*)$",
                ackCallback);
    }


}
