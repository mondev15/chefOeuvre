package fr.enac.audio;

import marytts.exceptions.SynthesisException;
import javax.sound.sampled.AudioInputStream;
import java.util.List;

/**
 * AudioPlayer is the interface used for the lecture of SSML files.
 * A instance of a class implementing Audioplayer is created at the beginning of the application and is used by every
 * module which need audio to be play.
 * The fact that this instance is always referred as an Audioplayer allows to change easily the synthesizer used.
 */
public interface AudioPlayer {
	
	/**
	 * The languages available in this application
	 * ANGLAIS for English
	 * FRANCAIS for French
	 */
    String ANGLAIS = "ANGLAIS";
	String FRANCAIS = "FRANCAIS";

    /**
     * Return true if the audioPlayer is currently playing a sound, false otherwise
     */
	boolean isPlaying();

    /**
     * Generate an AudioInputStream from a SSML file with a voice specified
     * @param fileName the name of the file
     * @param Voice the name of the voice
     */
    AudioInputStream generateAudioSsml(String fileName, String Voice) throws SynthesisException;

    /**
     * Play an audio stream on the standart audio output
     * @param stream The AudioInputStream to play
     */
    void playStream(AudioInputStream stream) throws SynthesisException;

    /**
     * Generate an AudioInputStream from SSML file with a specified voice and with noise
     * @param fileName the name of the file
     */
    void playSSMLWithNoise(String fileName);


    /**
     * set the voice to be used
     * @param voice the name of the voice
     */
    void setVoice(String voice);

    /**
     * get a list of voices of the language specified in the parameter
     * @param lang the language
     */
    List<String> getVoices(String lang);

    /**
     * get the name of the default voice used in the language given in the parameter
     * @param language the language
     */
    String getDefaultVoice(String language);
}
