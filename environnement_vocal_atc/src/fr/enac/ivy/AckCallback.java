package fr.enac.ivy;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import org.w3c.dom.Document;

import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyMessageListener;
import fr.enac.audio.AudioPlayer;
import fr.enac.parser.Preprocessor;

/**
 * AckCallback is the class us for the callback of an acknowledgement.
 * The method receive is called each time an acknowledgment is receive on the Ivy bus.
 */

public class AckCallback extends AudioCallback implements IvyMessageListener {

    /**
     * List containing all flight exempted of acknowledgment by the user.
     */
    private List<String> excludedFlights;

    /**
     * Timeout before aborting getting flight parameters
     */
    private final long REQUEST_FLIGHT_PARAM_TIMEOUT = 2000; //In milliseconds

    /**
     * Instantiate an AckCallback object. In particular, load all flight exempted of acknowledgment in the
     * config.properties file.
     */
    public AckCallback(AudioPlayer audioPlayer, ArrayList<Hashtable<String, String>> orderStack) {
        super(audioPlayer, orderStack);
        try {
            Properties prop = new Properties();
            prop.load(new FileInputStream("config/config.properties"));
            excludedFlights = Arrays.asList(prop.getProperty("flight").split(","));
            for (int i=0; i<excludedFlights.size(); i++){
                excludedFlights.set(i, excludedFlights.get(i).trim());
            }
            System.out.println("Flights excluded from acknowledgment : " + excludedFlights);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Get the parameters of the flight which the ID is the parameter.
     * Return null if the flight is unknown
     */
    public Hashtable<String, String> getParameters(String flightId){
        int i = 0;
        int ordersSent = this.orderStack.size();
        while (i < ordersSent) {
            Hashtable<String, String> env_i = this.orderStack.get(i);
            if (env_i.get("Flight").equals(flightId)) {
                //this.orderStack.remove(i);
                System.out.println("Remove order from stack");
                return env_i;
            }
            i++;
        }
        return null; // The flight is unknown
    }


    @Override
    public void receive(IvyClient client, String[] args) {

        /* The following parameters are not used in the app but could be used in further version of the app*/
        //String result = args[0];
        //String info = args[1];

        String[] orderParameters = args[2].split("\\|");
        String flightID = orderParameters[1]; // We assume to the second parameters is always the flight id

        /* Checking if the flight is allowed by the user to acknowledge */
        if (!excludedFlights.contains(flightID)) {
            Hashtable<String, String> env = null;
            long requestStartTime = System.currentTimeMillis();

            // TODO very bad practice do not put Sleep in Ivy Callback
            /* Wait if the order is still not in the stack */
            try {
                while (env == null && (requestStartTime - System.currentTimeMillis()) < REQUEST_FLIGHT_PARAM_TIMEOUT) {
                    env = getParameters(flightID);
                    Thread.sleep(200);
                }
                Thread.sleep(4000); //Additional wait in order to not synthesize the ack before the order
            } catch(InterruptedException e){
                e.printStackTrace();
            }

            /* Getting the right ssml file according to the flight language and voice */
            if (env == null){
                System.out.println("Unknown flight " + flightID + " : can not generate ack");
            } else {
                String orderFile;
                String language = env.get("flightLanguage");
                if (language.equals(AudioPlayer.FRANCAIS)) {
                    orderFile = "data/ssml/" + orderParameters[0] + "Ack_fr.xml";
                } else {
                    orderFile = "data/ssml/" + orderParameters[0] + "Ack.xml";
                }

                Preprocessor preprocessor = new Preprocessor(new Hashtable<>(), env);
                Document ssml = preprocessor.convertToSSML(orderFile,language);
                addSsmlToQueue(ssml, env.get("flightVoice"));
            }
        } else {
            System.out.println("The flight " + flightID + " is exempted from acknowledgment");
        }
    }

}
