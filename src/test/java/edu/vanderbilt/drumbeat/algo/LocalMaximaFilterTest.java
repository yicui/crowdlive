package edu.vanderbilt.drumbeat.algo;

import org.junit.Test;

import edu.vanderbilt.drumbeat.domain.AudioDataOnDemand;
import edu.vanderbilt.drumbeat.domain.TransposableData;

/* @author Yi Cui */
public class LocalMaximaFilterTest {

    private LocalMaximaFilter localMaximaFilter = new LocalMaximaFilter();

    @Test
    public void Process() {
    	this.localMaximaFilter.setWindow(17);
    	this.localMaximaFilter.setExpectedMaximaPosition(8);
    	AudioDataOnDemand dod = new AudioDataOnDemand();
    	TransposableData data = new TransposableData();
    	data.setDataset(dod.mockRandomAudioData(100, 256));
		int[] frame = (int[])data.getDataset().get(0);    		    	    	    	
    	int oldDataframesize = frame.length;    	    	
    	// whether the filter returns dataset with the expected framesize
    	try {
    		this.localMaximaFilter.Process(data);
    		frame = (int[])data.getDataset().get(0);    		
    	}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
    	org.junit.Assert.assertTrue(frame.length == oldDataframesize);
  	
    	// For asymptotic audio dataset, the same dataset should be returned when expectedMaximaPosition=0     	
    	data.setDataset(dod.mockAsymptoticAudioData(100, 256));
    	this.localMaximaFilter.setExpectedMaximaPosition(0);    	
    	try {
    		this.localMaximaFilter.Process(data);
    	}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
    	for (int i = this.localMaximaFilter.getWindow()-1; i < data.getDataset().size(); i ++) {
			frame = (int[])data.getDataset().get(i);
    		for (int index = 0; index < frame.length; index ++) 
    			org.junit.Assert.assertTrue(frame[index] == i*frame.length+index);
    	}

    	// the filter should return a dataset of total zero when given conflicting parameters
    	this.localMaximaFilter.setExpectedMaximaPosition(17);
    	data.setDataset(dod.mockRandomAudioData(100, 256));    	
    	try {
    		this.localMaximaFilter.Process(data);
    	}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
    	for (int i = 0; i < data.getDataset().size(); i ++) {
			frame = (int[])data.getDataset().get(i);
    		for (int index = 0; index < frame.length; index ++)
   				org.junit.Assert.assertTrue(frame[index] == 0);
    	}
    }
}