package fr.enac.audio;

import fr.enac.tools.FileReader;

/**
 * BalisesConvert is the class representing the conversion of the beacons 
 */

import java.util.Scanner;

/**
 * This class is use the right pronunciation of beacons and VFR callsigns.
 */
public class BalisesConvert implements Convert {

	/**
	 * Convert a succession of letters into words of the aeronautical alphabet.
	 * For example 'TOU' will be converted into 'tango oscar uniform'.
	 * 
	 * @param id The string to convert
	 * @param language 
	 */
	public static String convert(String id, String language){
		
		String baliseName = "";						
		
		char[] arraySplit = id.toCharArray();
		
		for (int i=0; i< arraySplit.length; i++){
			FileReader fic = new FileReader("data/alphabetAeronautique_"+language+".txt");
			int nbIdLettres = fic.lineNumber();
			String lettre = Character.toString(arraySplit[i]);
			
			try {
				for (int l=0; l<nbIdLettres; l++) {
					
					Scanner sc = new Scanner(fic.nextLine());
					if ((sc.next()).equals(lettre)){
						baliseName += sc.next();
						baliseName += " ";
					} 
					sc.close();
				}				
			} catch(NullPointerException e) {
					System.out.println("BalisesConvert "+e.getMessage());
			}
		}		
		return baliseName;
	}

}
