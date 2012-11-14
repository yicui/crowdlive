package edu.vanderbilt.drumbeat.algo;

import org.junit.Test;

import edu.vanderbilt.drumbeat.domain.AudioDataOnDemand;
import edu.vanderbilt.drumbeat.domain.TransposableData;

/* @author Yi Cui */
public class CountingFilterTest {

    private CountingFilter countingFilter = new CountingFilter();

    @Test
    public void Process() {
    	this.countingFilter.setMinThreshold(0);
    	this.countingFilter.setMaxThreshold(1<<31-1);
    	AudioDataOnDemand dod = new AudioDataOnDemand();
    	TransposableData data = new TransposableData();
    	data.setDataset(dod.mockRandomAudioData(100, 256));
    	int[] frame = new int[2];
    	// whether the filter returns dataset with the expected framesize
    	try {
    		this.countingFilter.Process(data);
    		frame = (int[])data.getDataset().get(0);    		
    	}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
    	org.junit.Assert.assertTrue(frame.length == 1);
  	
   		// the filter should return a dataset of total zero when given conflicting thresholds
    	this.countingFilter.setMaxThreshold(-1);
    	data.setDataset(dod.mockRandomAudioData(100, 256));    	
    	try {
    		this.countingFilter.Process(data);
    	}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
    	for (int i = 0; i < data.getDataset().size(); i ++) {
    		frame = (int[])data.getDataset().get(i);
    		for (int index = 0; index < frame.length; index ++)
    			if (frame[index] != 0)
    				org.junit.Assert.fail("Counter is positive when maxThreshold is even smaller than minThreshold");
    	}
    }
}