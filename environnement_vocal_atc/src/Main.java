import fr.dgac.ivy.IvyException;
import fr.enac.audio.AudioPlayer;
import fr.enac.audio.IndicatifConvert;
import fr.enac.audio.MaryTTS;
import fr.enac.ivy.IvyManager;

/**
 * Main is the class used to launch the application with vocal synthesis
 */
public class Main {

    public static void main(String[] args) {

        AudioPlayer audioPlayer = new MaryTTS();
        IndicatifConvert.loadCompanies("data/compagny.xml");
        try {
        	String ivydomain = "224.2.0.11:2011";
        	if ( args.length > 0) ivydomain = args[0];
            //IvyManager ivyManager = 
            		new IvyManager(audioPlayer,ivydomain);
            System.out.println("Ready");
        } catch (IvyException e) {
            System.out.println(e);
        }
    }

}
