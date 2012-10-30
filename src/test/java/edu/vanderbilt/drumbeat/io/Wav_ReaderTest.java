package edu.vanderbilt.drumbeat.io;

import org.junit.Test;

import edu.vanderbilt.drumbeat.domain.Audio;

public class Wav_ReaderTest {

    private Wav_Reader wav_Reader = new Wav_Reader();

    @Test
    public void Input() {
    	Audio audio = new Audio();
    	wav_Reader.setAudio(audio);
    	try {
    		wav_Reader.Input("src/test/resources/testwav.wav");
    	}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
    	org.junit.Assert.assertTrue(2903 == audio.getFrames());
    	org.junit.Assert.assertTrue(33697 == audio.getDuration());    	
    	org.junit.Assert.assertTrue(512 == audio.getFramesize());
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
