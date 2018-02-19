package test;

import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;
import java.util.logging.Level;
import java.util.logging.Logger;


public class IvyTest {

    private Ivy bus;
    private StringBuilder sb;

    public IvyTest() {

        bus = new Ivy("IvyTest", "IvyTest CONNECTED", null);
        sb = new StringBuilder();

        //---connexion au bus
        try {
            bus.start("127.255.255.255:2010");
        } catch (IvyException ex) {
            Logger.getLogger(IvyTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        //---bind
        sb = new StringBuilder();

        try {
            //CallSign=(.*) Sector=(.*) X=(.*) Y=(.*) Vx=(.*) Vy=(.*) GroundSpeed=(.*)
            bus.bindMsg("PlnEvent Flight=(.*)", new IvyMessageListener() {
                @Override
                public void receive(IvyClient client, String[] args) {
                    sb.append(args[0] + "\n");
                    System.out.println(args[0]);
                }
            });
        } catch (IvyException ex) {
            Logger.getLogger(IvyTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public static void main (String args[]){
    
     IvyTest ivy = new IvyTest();
    }

}
