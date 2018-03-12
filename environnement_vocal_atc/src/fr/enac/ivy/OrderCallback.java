package fr.enac.ivy;

import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyMessageListener;
import fr.enac.audio.AudioPlayer;
import fr.enac.parser.Preprocessor;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * OrderCallback is a class used to receive order messages from the Ivy bus
 */
public class OrderCallback extends AudioCallback implements IvyMessageListener {

	/**
	 * 
	 * @param audioPlayer the AudioPlayer
	 * @param orderStack the stack of orders
	 */
    public OrderCallback(AudioPlayer audioPlayer, ArrayList<Hashtable<String, String>> orderStack) {
        super(audioPlayer, orderStack);
    }

    /**
     * Adds an order to the stack containing the orders
     * 
     * @param env
     */
    public void addOrder(Hashtable<String, String> env) {
        this.orderStack.add(0, env);
        System.out.println("Add order to stack");
    }

    /**
     * Receives all parameters and choose the right order file to read
     * 
     * @param client The Ivy client used
     * @param strings The list of parameters received
     */
    @Override
    public void receive(IvyClient client, String[] strings) {
        int nb_parameters = strings.length;
        String flightId = strings[1].split("=")[1];

        Hashtable<String, String> env = new Hashtable<>(); //Order parameters dictionary
        Hashtable<String, String> flightHashtable = FlightCallback.getFlightData(flightId);


        if (flightHashtable == null){
            System.out.println("Unknown flight " + flightId + " : can not generate order");
        } else {
            String orderFile;
            String language = flightHashtable.get("Language");
            /*if (language.equals(AudioPlayer.FRANCAIS)) {
                orderFile = "data/ssml/" + strings[0] + "_fr.xml";
            } else {*/
                orderFile = "data/ssml/" + strings[0] + ".xml";
            //}

            for (int i = 1; i < nb_parameters; i++) { // Order parameters starts after name of the order
                String s = strings[i];
                String[] key_value = s.split("=");
                env.put(key_value[0], key_value[1]);
            }

            Preprocessor preprocessor = new Preprocessor(flightHashtable, env);
            env = preprocessor.getEnvironment();
            addOrder(env);

            Document ssml = preprocessor.convertToSSML(orderFile,language);
            addSsmlToQueue(ssml, audioPlayer.getDefaultVoice(language));
        }
    }
    

}
