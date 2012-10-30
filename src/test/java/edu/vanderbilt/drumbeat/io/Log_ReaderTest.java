package edu.vanderbilt.drumbeat.io;

import org.junit.Test;
import edu.vanderbilt.drumbeat.domain.Audio;

public class Log_ReaderTest {

    private Log_Reader log_Reader = new Log_Reader();
    @Test
    public void Input() {
    	Audio audio = new Audio();
    	log_Reader.setAudio(audio);
    	try {
    		log_Reader.Input("src/test/resources/testlog.log");
    	}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
    	org.junit.Assert.assertTrue(1353 == audio.getFrames());
    	org.junit.Assert.assertTrue(63069 == audio.getDuration());    	
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
