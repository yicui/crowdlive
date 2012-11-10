package edu.vanderbilt.drumbeat.algo;

import org.junit.Test;

import edu.vanderbilt.drumbeat.domain.AudioDataOnDemand;
import edu.vanderbilt.drumbeat.domain.Data;

public class CountingFilterTest {

    private CountingFilter countingFilter = new CountingFilter();

    @Test
    public void Process() {
    	this.countingFilter.setMinThreshold(0);
    	this.countingFilter.setMaxThreshold(1<<31-1);
    	AudioDataOnDemand dod = new AudioDataOnDemand();
    	Data data = new Data();
    	data.setDataset(dod.mockRandomAudioData(100, 256));
    	// whether the filter returns dataset with the expected framesize
    	try {
    		this.countingFilter.Process(data);
    	}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
    	org.junit.Assert.assertTrue(data.getDataset().get(0).length == 1);
  	
   		// the filter should return a dataset of total zero when given conflicting thresholds
    	this.countingFilter.setMaxThreshold(-1);
    	data.setDataset(dod.mockRandomAudioData(100, 256));    	
    	try {
    		this.countingFilter.Process(data);
    	}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
    	for (int i = 0; i < data.getDataset().size(); i ++)
    		for (int index = 0; index < data.getDataset().get(i).length; index ++)
    			if (data.getDataset().get(i)[index] != 0)
    				org.junit.Assert.fail("Counter is positive when maxThreshold is even smaller than minThreshold");
    }
}