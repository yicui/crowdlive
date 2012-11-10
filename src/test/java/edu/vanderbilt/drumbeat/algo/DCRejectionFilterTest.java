package edu.vanderbilt.drumbeat.algo;

import org.junit.Test;

import edu.vanderbilt.drumbeat.domain.AudioDataOnDemand;
import edu.vanderbilt.drumbeat.domain.Data;

public class DCRejectionFilterTest {

    private DCRejectionFilter dCRejectionFilter = new DCRejectionFilter();

    @Test
    public void Process() {
    	this.dCRejectionFilter.setFilterGain((short)((float)(1<<15)*0.975f));
    	
    	AudioDataOnDemand dod = new AudioDataOnDemand();
    	Data data = new Data();
    	data.setDataset(dod.mockRandomAudioData(100, 256));
    	int oldDataframesize = data.getDataset().get(0).length;    	
    	// whether the filter returns dataset with the expected framesize
    	try {
    		this.dCRejectionFilter.Process(data);
    	}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
   		org.junit.Assert.assertTrue(data.getDataset().get(0).length == oldDataframesize);
    }
}
