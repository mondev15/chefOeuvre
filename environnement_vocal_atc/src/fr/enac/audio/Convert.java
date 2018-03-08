package fr.enac.audio;

/**
 * This interface is used to convert strings into text that will be used to generate text with the right
 * pronunciation for the synthesizer.
 * Implemented by IndicatifConvert (to convert IFR callsigns) and BalisesConvert (to convert VFR callsigns and beacons)
 * 
 */
public interface Convert {

	/**
	 * Convert the string which is the parameter
	 * @param nom The string which is converted
	 */
    public static String convert(String nom, String language) {
        return null;
    }

}
