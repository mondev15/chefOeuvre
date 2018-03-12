package fr.enac.audio;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import marytts.LocalMaryInterface;
import marytts.exceptions.MaryConfigurationException;
import marytts.exceptions.SynthesisException;
import marytts.util.dom.DomUtils;

/**
 * Implementation of AudioPlayer using MaryTTS API, the synthesizer retains in the project.
 */
public class MaryTTS implements fr.enac.audio.AudioPlayer {
	
	/**
     * Object on which most of MaryTTS method are called.
     * Is instantiated at the beginning of the app.
     */
    private static LocalMaryInterface maryInterface;

    /**
     * English voice used to give order
     */
    private final String DEFAULT_ENGLISH = "cmu-rms-hsmm";

    /**
     * French voice used to give order
     */
    private final String DEFAULT_FRENCH = "upmc-pierre-hsmm";

    private boolean isPlaying;

    /**
     * Instantiate a MaryTTS object (can take several seconds)
     */
    public MaryTTS() {
        try {
            maryInterface = new LocalMaryInterface();
            this.setVoice(DEFAULT_ENGLISH);
            maryInterface.setInputType("SSML");
            isPlaying = false;
        } catch (MaryConfigurationException e) {
            e.printStackTrace();
        }

    }

    public boolean isPlaying() {
        return isPlaying;
    }

    /**
     * Set the voice use by the synthesizer
     *
     * @param voice The voice chosen
     */
    @Override
    public void setVoice(final String voice) {
        maryInterface.setVoice(voice);
    }

    /**
     * Return the list of all voices available for a specific language
     *
     * @param lang The language
     */
    @Override
    public List<String> getVoices(String lang) {
        Set<String> voices;
        List<String> voicesList = new ArrayList<>();
        /*switch (lang) {
            case AudioPlayer.FRANCAIS:
                voices = maryInterface.getAvailableVoices(new Locale("fr"));
                voices.remove(DEFAULT_FRENCH);
                break;
            case AudioPlayer.ANGLAIS:
                voices = maryInterface.getAvailableVoices(new Locale("en"));
                voices.remove(DEFAULT_ENGLISH);
                break;
            default:
                voices = maryInterface.getAvailableVoices(new Locale("en"));
                voices.remove(DEFAULT_ENGLISH);
                break;
        }*/
        voices = maryInterface.getAvailableVoices(new Locale("en"));
        voices.remove(DEFAULT_ENGLISH);
        voicesList.addAll(voices);
        return voicesList;
    }

    @Override
    public AudioInputStream generateAudioSsml(String fileName, String voice) throws SynthesisException {

        this.setVoice(voice);
        File xml = new File(fileName);
        Document ssml;
        try {
            ssml = DomUtils.parseDocument(xml);
        } catch (ParserConfigurationException | SAXException | IOException e) {
        	System.out.println("generateAudioSsml "+e.getMessage());
            throw new SynthesisException();
        }
        AudioInputStream stream = null;
        stream = maryInterface.generateAudio(ssml);
        return stream;
    }


    @Override
    public synchronized void playStream(AudioInputStream stream) throws SynthesisException {
        /* Change playing state */
    	isPlaying = true;
    	
        AudioFormat format = stream.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, format);
        try {
            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            LineListener listener = new LineListener() {
                public void update(LineEvent event) {
                	//System.out.println("LineListener event "+event.getType());
                	if ( event.getType() == LineEvent.Type.STOP) {
                		synchronized (clip) {
                			isPlaying = false;
                			clip.notify();
                		}
                	}
                }
            };
            clip.addLineListener(listener);
            clip.start();
            synchronized (clip) {
            	while(isPlaying) clip.wait();
            }
            
            clip.close();

        } catch (Exception e) {
            isPlaying = false;
            System.out.println("playStream exception "+e.getMessage());
            e.printStackTrace();
            throw new SynthesisException();
        }

        /* Change playing state */
        isPlaying = false;
    }

    /**
     * add noise when playing SSML file
     * NOT IMPLEMENTED
     *
     * @param fileName The SSML file to play with noise
     */
    public void playSSMLWithNoise(String fileName) {
    }

    /**
     * Return the voice set as default voice for a specific language
     *
     */
    public String getDefaultVoice(String language) {
//        if (language.equals(AudioPlayer.ANGLAIS)) {
            return DEFAULT_ENGLISH;
//        } else {
//            return DEFAULT_FRENCH;
//        }
    }
}
