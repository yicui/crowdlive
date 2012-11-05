package edu.vanderbilt.drumbeat.io;

import java.util.ArrayList;

import org.junit.Test;

import edu.vanderbilt.drumbeat.domain.Audio;
import edu.vanderbilt.drumbeat.domain.AudioDataOnDemand;

public class Wav_ReaderTest {

    private Wav_Reader wav_Reader = new Wav_Reader();

    @Test
    public void Input() {
    	AudioDataOnDemand dod = new AudioDataOnDemand();
    	Audio audio = dod.getNewTransientAudio(5);
    	audio.setPathurl("src/test/resources/testwav.wav");
    	wav_Reader.setAudio(audio);
    	// A correct WAV file
    	try {
    		wav_Reader.Input();
    	}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
    	org.junit.Assert.assertTrue(33697 == audio.getDuration());
    	ArrayList<int[]> data = audio.getData();
    	org.junit.Assert.assertTrue(2903 == data.size());
    	org.junit.Assert.assertTrue(512 == data.get(0).length);
    	
    	// An MP3 file
    }

    @Test
    public void getAudio() {
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void setAudio() {
        org.junit.Assert.assertTrue(true);
    }
}
