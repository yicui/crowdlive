package edu.vanderbilt.drumbeat.io;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.vanderbilt.drumbeat.domain.Audio;
import edu.vanderbilt.drumbeat.domain.AudioDataOnDemand;
import edu.vanderbilt.drumbeat.domain.DefaultDataManager;
import edu.vanderbilt.drumbeat.domain.TransposableData;

/* @author Yi Cui */
public class Wav_ReaderTest {

    private Wav_Reader wav_Reader = new Wav_Reader();

    @Test
    public void Input() {
    	AudioDataOnDemand dod = new AudioDataOnDemand();
    	Audio audio = dod.getNewTransientAudio(5);
    	audio.setPathurl("src/test/resources/testwav.wav");
    	audio.setDatamanager(new DefaultDataManager());
    	List<Object> dataset = new ArrayList<Object>();
    	int[] frame = new int[1];
    	dataset.add(frame);
    	TransposableData data = new TransposableData();
    	data.setDataset(dataset);
    	wav_Reader.setAudio(audio);
    	// A correct WAV file
    	try {
    		audio.getDatamanager().push(data);    		
    		wav_Reader.Input();
        	dataset = audio.getDatamanager().peek().getDataset();
        	frame = (int[])dataset.get(0);    		
    	}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
    	org.junit.Assert.assertTrue(33697 == audio.getDuration());
    	org.junit.Assert.assertTrue(2903 == dataset.size());
    	org.junit.Assert.assertTrue(512 == frame.length);
    	
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
