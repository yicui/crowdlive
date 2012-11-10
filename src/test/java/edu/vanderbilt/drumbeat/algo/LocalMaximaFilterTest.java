package edu.vanderbilt.drumbeat.algo;

import org.junit.Test;

import edu.vanderbilt.drumbeat.domain.AudioDataOnDemand;
import edu.vanderbilt.drumbeat.domain.Data;

public class LocalMaximaFilterTest {

    private LocalMaximaFilter localMaximaFilter = new LocalMaximaFilter();

    @Test
    public void Process() {
    	this.localMaximaFilter.setWindow(17);
    	this.localMaximaFilter.setExpectedMaximaPosition(8);
    	AudioDataOnDemand dod = new AudioDataOnDemand();
    	Data data = new Data();
    	data.setDataset(dod.mockRandomAudioData(100, 256));
    	int oldDataframesize = data.getDataset().get(0).length;    	    	
    	// whether the filter returns dataset with the expected framesize
    	try {
    		this.localMaximaFilter.Process(data);
    	}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
    	org.junit.Assert.assertTrue(data.getDataset().get(0).length == oldDataframesize);
  	
    	// For asymptotic audio dataset, the same dataset should be returned when expectedMaximaPosition=0     	
    	data.setDataset(dod.mockAsymptoticAudioData(100, 256));
    	this.localMaximaFilter.setExpectedMaximaPosition(0);    	
    	try {
    		this.localMaximaFilter.Process(data);
    	}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
    	for (int i = this.localMaximaFilter.getWindow()-1; i < data.getDataset().size(); i ++)
    		for (int index = 0; index < data.getDataset().get(i).length; index ++)
    			if (data.getDataset().get(i)[index] != i*data.getDataset().get(i).length+index)
    				org.junit.Assert.fail("Median filter fails to return the median value");

    	// the filter should return a dataset of total zero when given conflicting parameters
    	this.localMaximaFilter.setExpectedMaximaPosition(17);
    	data.setDataset(dod.mockRandomAudioData(100, 256));    	
    	try {
    		this.localMaximaFilter.Process(data);
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