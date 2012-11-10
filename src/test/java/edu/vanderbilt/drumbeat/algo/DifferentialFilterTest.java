package edu.vanderbilt.drumbeat.algo;

import org.junit.Test;

import edu.vanderbilt.drumbeat.domain.AudioDataOnDemand;
import edu.vanderbilt.drumbeat.domain.Data;

public class DifferentialFilterTest {

    private DifferentialFilter differentialFilter = new DifferentialFilter();

    @Test
    public void Process() {
    	this.differentialFilter.setFrameDistance(1);
    	AudioDataOnDemand dod = new AudioDataOnDemand();
    	Data data = new Data();
    	data.setDataset(dod.mockRandomAudioData(100, 256));
    	int oldDataframesize = data.getDataset().get(0).length;    	    	
    	// whether the filter returns dataset with the expected framesize
    	try {
    		this.differentialFilter.Process(data);
    	}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
    	org.junit.Assert.assertTrue(data.getDataset().get(0).length == oldDataframesize);
		for (int index = 0; index < data.getDataset().get(0).length; index ++)
			if (data.getDataset().get(0)[index] != 0)
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
    	for (int i = 0; i < data.getDataset().size(); i ++)
    		for (int index = 0; index < data.getDataset().get(i).length; index ++)
    			if (data.getDataset().get(i)[index] != 0)
    				org.junit.Assert.fail("Result is nonzero when frameDistance is set to be larger than the dataset size");
    }
}
