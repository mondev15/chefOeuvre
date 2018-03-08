package fr.enac.ivy;

import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyMessageListener;
import fr.enac.audio.AudioPlayer;
import fr.enac.audio.IndicatifConvert;

import java.util.Hashtable;

/**
 * FlightCallback is the class used to store parameters of flights reveive in the Ivy bus.
 */
public class FlightCallback implements IvyMessageListener {

	/**
	 * parametresVol is the dictionary of flight parameters of each aircraft
	 */
    private static Hashtable<String, Hashtable<String, String>> parametresVol;
    
    /**
     * audioPlayer the name of the AudioPlayer used
     */
    private AudioPlayer audioPlayer;

    /**
     * Counter used to attribute different voices for each flight, if possible
     */
    private int compteur=0;
    
    /**
     * 
     * @param audioPlayer
     */
    public FlightCallback(AudioPlayer audioPlayer) {
        super();
        this.audioPlayer = audioPlayer;
        parametresVol = new Hashtable<>();
    }

    /**
     * Get the dictionary of the flight parameters of the flight which ID is the parameter
     * @param flightId is the ID of the flight
     */
    public static Hashtable<String, String> getFlightData(String flightId) {
        return parametresVol.get(flightId);
    }

    /**
     * Receive all flight parameters and initiate the voice
     * 
     * @param client The Ivy client used
     * @param args The list of parameters received
     */
    @Override
    public void receive(IvyClient client, String[] args) {

        /* Parsing flight parameters */
        int nb_parametres = args.length;
        Hashtable<String, String> parametres = new Hashtable<>();

        for (int i = 1; i < nb_parametres; i++) {
            String s = args[i];
            String[] key_value = s.split("=");
            parametres.put(key_value[0], key_value[1]);
        }

        String flightID = args[0].split("=")[1];
        String flightCallsign = parametres.get("CallSign");
        String language;
        String voice;

        /* Getting language and voice */
        if (parametresVol == null || !parametresVol.contains(flightID)) { // If the flight is unknown
            language = IndicatifConvert.getLanguage(parametres.get("CallSign"));
            compteur = (compteur + 1)% audioPlayer.getVoices(language).size();
            voice = audioPlayer.getVoices(language).get(compteur);
        } else {
            language = getFlightData(flightID).get("Language");
            voice = getFlightData(flightID).get("Voice");
        }
        parametres.put("Language", language);
        parametres.put("Voice", voice);
        parametresVol.put(flightID, parametres);

        System.out.println("Add/Update parameters for flight " + flightCallsign  + " (" + flightID + ")");
    }

}
