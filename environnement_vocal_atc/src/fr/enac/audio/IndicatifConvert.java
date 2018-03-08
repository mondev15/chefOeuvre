package fr.enac.audio;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * IndicatifConvert is the class used for the conversion of the callsign into text readable by the synthesizer.
 * For example 'AFR225' should be converted into 'Air France 225'
 */

public class IndicatifConvert implements Convert {
    /**
     * Hashtable containing the language and the pronunciation of a callsign id.
     */
	public static Hashtable<String, Hashtable<String, String>> callsignsInfo = new Hashtable<>();

	/**
	 * Load companies data into the global variable callsignsInfo.
     * This method is called at the initialization of the app
	 * 
	 * @param fileName Path to the file containing companies data
	 */
	public static void loadCompanies(String fileName){
		SAXBuilder sxb = new SAXBuilder();
		org.jdom2.Document document;
		try {
			document = sxb.build(new File(fileName));
			Element root = document.getRootElement();

			List<Element> children = root.getChildren();

			for (Object obj : children) {
				Element child = (Element) obj;
				String callsign = child.getAttributeValue("IDENT");
				String companyName = child.getAttributeValue("NAME");
				String lang = child.getAttributeValue("LANGUAGE");
				String language;
				if (lang.equals("FRENCH")){
					language = AudioPlayer.FRANCAIS;
				}
				else {
					language = AudioPlayer.ANGLAIS;
				}
				Hashtable<String , String> companyInfo = new Hashtable<>();
				companyInfo.put("companyName", companyName);
				companyInfo.put("Language", language);
				callsignsInfo.put(callsign, companyInfo);
			}

		}
		catch (JDOMException | IOException  e){
			System.out.println("Impossible de lire le fichier de compagnie");
		}
	}

	
	/**
	 * Get the ID of the company operating the flight.
     * For example, getCallsignId("AFR225") will return "AFR"
	 * 
	 * @param callsign The callsign of the flight 
	 */
	
	private static String getCallsignId(String callsign){
		StringBuilder id = new StringBuilder();
		int index = 0;
		int callsignLength = callsign.length();
		boolean atIdPart = true;
		while (atIdPart && index < callsignLength ){
			Character c = callsign.charAt(index);
			if (Character.isDigit(c)){
				atIdPart = false;
			} else {
				id.append(c);
			}
			index++;
		}
		return id.toString();
	}
	
	
	/**
	 * Get the number following the ID of the company
     * For example, getCallsignId("AFR225") will return "225"
	 * 
	 * @param callsign The callsign of the flight
	 */	

	private static String getCallsignNb(String callsign){
		int index = 0;
		int callsignLength = callsign.length();
		boolean atNbPart = false;
		while (!atNbPart && index < callsignLength ){
			Character c = callsign.charAt(index);
			if (Character.isDigit(c)){
				atNbPart = true;
			} else {
				index++;
			}
		}
        return callsign.substring(index);
	}

	
	/**
	 * Convert the callsign of the flight into the company name followed by the number
	 * For example, getCallsignId("AFR225") will return "Air France 225"
     *
	 * @param callsign The callsign of the flight
	 */	

	public static String convert1(String callsign, String language){
		String id = getCallsignId(callsign);
		String nb = getCallsignNb(callsign);
		try {
			return callsignsInfo.get(id).get("companyName") + " " + nb;
		}
		catch (NullPointerException e){
			return BalisesConvert.convert(id, language) + " " + nb;
		}
	}
	public static String convert(String callsign, String language){
		String prefix = "";
		String nb = "";
		String postfix = "";
		Pattern p = Pattern.compile("([A-Z]{2,3})([0-9]*)([A-Z]*)") ;   
		Matcher m = p.matcher(callsign) ;
		boolean b = m.matches();
		// si recherche fructueuse
		if(b) {
		    // pour chaque groupe
		    //for(int i=0; i <= m.groupCount(); i++) {
		        // affichage de la sous-chaîne capturée
		    //    System.out.println("Groupe " + i + " : " + m.group(i));
		    //}
		    prefix = m.group(1);
			nb = m.group(2);
			postfix = m.group(3);
		}
		
		try {
			return callsignsInfo.get(prefix).get("companyName") + " " + nb +" "+BalisesConvert.convert(postfix,language);
		}
		catch (NullPointerException e){
			return BalisesConvert.convert(prefix,language) + " " + nb +" "+BalisesConvert.convert(postfix,language);
		}
	}
	
	/**
	 * Get the language used by the pilot of a flight, according to the company language
	 * 
	 * @param callsign The callsign of the flight
	 */	
	
	public static String getLanguage(String callsign){
		String id = getCallsignId(callsign);
		if (callsignsInfo.get(id) == null) {
			return AudioPlayer.FRANCAIS;
		} else {
			return callsignsInfo.get(id).get("Language");
		}
	}

}
