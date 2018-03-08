package fr.enac.ivy;

import fr.dgac.ivy.IvyMessageListener;
import fr.enac.audio.AudioPlayer;
import fr.enac.parser.Preprocessor;
import org.w3c.dom.Document;

import javax.sound.sampled.AudioInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;


/**
 * AudioCallback is an abstract class generalizing all audio callback (order, acknowledgement)
 */

public abstract class AudioCallback implements IvyMessageListener {

    /**
     * audioPlayer is an AudioPlayer 
     */
    protected AudioPlayer audioPlayer;
    
    /**
     * orderSatck is the stack into all the orders ready to be synthesized will be added
     */
    protected ArrayList<Hashtable<String, String>> orderStack;

    /**
     * dateFormat is the format of the date, used to create custom SSML files according to the date
     */
    private DateFormat dateFormat = new SimpleDateFormat("HH_mm_ss_");

    /**
     * 
     * @param audioPlayer the AudioPlayer
     * @param orderStack the stack of orders
     */
    public AudioCallback(AudioPlayer audioPlayer, ArrayList<Hashtable<String, String>> orderStack) {
        this.audioPlayer = audioPlayer;
        this.orderStack = orderStack;
    }

    /**
     * Add the document SSML to be read in a queue of files
     * 
     * @param ssml The SSML file to be synthesized
     * @param voice The voice used for the voice synthesis
     */
    
    public void addSsmlToQueue(Document ssml, String voice) {
        String[] docPath = ssml.getDocumentURI().split("/");
        String docName = docPath[docPath.length - 1];
        Date time = new Date();
        String logName = "cache/" + dateFormat.format(time) + docName;
        try {

            /* Print SSML file on the disk */
            Preprocessor.printDocument(ssml, new FileOutputStream(docName));

            /* Generate audio */
            AudioInputStream stream = audioPlayer.generateAudioSsml(docName, voice);

            /* Wait for audioPlayer to be free */
            while (audioPlayer.isPlaying()){
                Thread.sleep(10);
            }

            /* Play audio */
            System.out.print("Playing " + docName + "...");
            audioPlayer.playStream(stream);
            System.out.println("Done");
        } catch (Exception e) {
            System.out.println("Can not synthesize " + docName+ " error "+e.getMessage());
        }
    }


}
