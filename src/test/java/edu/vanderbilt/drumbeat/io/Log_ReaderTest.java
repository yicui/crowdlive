package edu.vanderbilt.drumbeat.io;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import edu.vanderbilt.drumbeat.domain.Audio;
import edu.vanderbilt.drumbeat.domain.AudioDataOnDemand;
import edu.vanderbilt.drumbeat.domain.DefaultDataManager;
import edu.vanderbilt.drumbeat.domain.TransposableData;

/* @author Yi Cui */
public class Log_ReaderTest {

    private Log_Reader log_Reader = new Log_Reader();
    @Test
    public void Input() {
    	AudioDataOnDemand dod = new AudioDataOnDemand();
    	Audio audio = dod.getNewTransientAudio(5);
    	audio.setPathurl("src/test/resources/testlog.log");
    	audio.setDatamanager(new DefaultDataManager());
    	List<Object> dataset = new ArrayList<Object>();
    	int[] frame = new int[1];
    	dataset.add(frame);
    	TransposableData data = new TransposableData();
    	data.setDataset(dataset);
    	log_Reader.setAudio(audio);
    	// A correct log file
    	try {
    		audio.getDatamanager().push(data);
    		log_Reader.Input();
        	dataset = audio.getDatamanager().peek().getDataset();
        	frame = (int[])dataset.get(0);
    	}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
    	org.junit.Assert.assertTrue(63069 == audio.getDuration());
    	org.junit.Assert.assertTrue(1353 == dataset.size());
    	org.junit.Assert.assertTrue(512 == frame.length);
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
