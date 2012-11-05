package edu.vanderbilt.drumbeat.io;

import java.util.ArrayList;

import org.junit.Test;
import edu.vanderbilt.drumbeat.domain.Audio;
import edu.vanderbilt.drumbeat.domain.AudioDataOnDemand;

public class Log_ReaderTest {

    private Log_Reader log_Reader = new Log_Reader();
    @Test
    public void Input() {
    	AudioDataOnDemand dod = new AudioDataOnDemand();
    	Audio audio = dod.getNewTransientAudio(5);
    	audio.setPathurl("src/test/resources/testlog.log");
    	log_Reader.setAudio(audio);
    	// A correct log file
    	try {
    		log_Reader.Input();
    	}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
    	org.junit.Assert.assertTrue(63069 == audio.getDuration());
    	ArrayList<int[]> data = audio.getData();
    	org.junit.Assert.assertTrue(1353 == data.size());
    	org.junit.Assert.assertTrue(512 == data.get(0).length);
    	// A wrong log file    	
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
