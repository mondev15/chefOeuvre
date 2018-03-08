package fr.enac.ivy;

import java.util.Hashtable;

import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyMessageListener;

/**
 * DieCallback is the class removing the flight from the array parametresVol
 */

public class DieCallback implements IvyMessageListener {

	/**
	 * parametresVol is the dictionary of flight parameters of each aircraft
	 */
	private static Hashtable<String, Hashtable<String, String>> parametresVol = new Hashtable<>();
	
	/**
     * Receive all flight parameters and remove a flight from the database
     * 
     * @param client The Ivy client used
     * @param strings The list of parameters received
     */
	@Override
	public void receive(IvyClient client, String[] strings) {
		parametresVol.remove(strings[0]);		
		System.out.println("Flight " + strings[0] + " deleted from database");
		
	}

}
