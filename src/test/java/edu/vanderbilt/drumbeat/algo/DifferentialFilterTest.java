package edu.vanderbilt.drumbeat.algo;

import org.junit.Test;

import edu.vanderbilt.drumbeat.domain.AudioDataOnDemand;
import edu.vanderbilt.drumbeat.domain.TransposableData;

/* @author Yi Cui */
public class DifferentialFilterTest {

    private DifferentialFilter differentialFilter = new DifferentialFilter();

    @Test
    public void Process() {
    	this.differentialFilter.setFrameDistance(1);
    	AudioDataOnDemand dod = new AudioDataOnDemand();
    	TransposableData data = new TransposableData();
    	data.setDataset(dod.mockRandomAudioData(100, 256));
		int[] frame = (int[])data.getDataset().get(0);    		    	    	
    	int oldDataframesize = frame.length;    	    	
    	// whether the filter returns dataset with the expected framesize
    	try {
    		this.differentialFilter.Process(data);
    		frame = (int[])data.getDataset().get(0);
    	}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
    	org.junit.Assert.assertTrue(frame.length == oldDataframesize);
		for (int index = 0; index < frame.length; index ++)
			if (frame[index] != 0)
				org.junit.Assert.fail("The beginning frameDistance frames are not set to be zero");
  	
   		// the filter should return a dataset of total zero when given conflicting thresholds
    	this.differentialFilter.setFrameDistance(100);
    	data.setDataset(dod.mockRandomAudioData(100, 256));    	
    	try {
    		this.differentialFilter.Process(data);
    	}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
    	for (int i = 0; i < data.getDataset().size(); i ++) {
    		frame = (int[])data.getDataset().get(i);    		
    		for (int index = 0; index < frame.length; index ++)
    			if (frame[index] != 0)
    				org.junit.Assert.fail("Result is nonzero when frameDistance is set to be larger than the dataset size");
    	}
    }
}
