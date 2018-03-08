import java.io.IOException;

import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyException;
import fr.enac.tools.FileReader;

/**
 * This class is used to simulate order ans acknowledgment messages on the Ivy bus, in order to test the Main class.
 * Therefore, this class should be run simultaneously with Main.java.
 * The messages send are written in the file "data/Fichier_Demo.txt".
 * After running Demonstration.java, press Enter on the console to read message one by one.
 */
public class Demonstration {

    public static void main(String[] args) throws IvyException, IOException, NullPointerException {

        Ivy bus = new Ivy("Demo", "Demo1", null);
        bus.start("127.255.255.255:2010");
        
        FileReader fic = new FileReader("data/Fichier_Demo.txt");
        int nb_lines = fic.lineNumber();


        for (int i=0; i < nb_lines; i++) {
            String ligne = fic.nextLine();
            if (!ligne.equals("")) {
                System.in.read(new byte[2]);
                System.out.println(ligne);
                bus.sendMsg(ligne);
            }
        }
       
    }

}
