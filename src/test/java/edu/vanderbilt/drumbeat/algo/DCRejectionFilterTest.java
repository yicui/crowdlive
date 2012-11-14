package edu.vanderbilt.drumbeat.algo;

import org.junit.Test;

import edu.vanderbilt.drumbeat.domain.AudioDataOnDemand;
import edu.vanderbilt.drumbeat.domain.TransposableData;

/* @author Yi Cui */
public class DCRejectionFilterTest {

    private DCRejectionFilter dCRejectionFilter = new DCRejectionFilter();

    @Test
    public void Process() {
    	this.dCRejectionFilter.setFilterGain((short)((float)(1<<15)*0.975f));
    	
    	AudioDataOnDemand dod = new AudioDataOnDemand();
    	TransposableData data = new TransposableData();
    	data.setDataset(dod.mockRandomAudioData(100, 256));
		int[] frame = (int[])data.getDataset().get(0);    		    	
    	int oldDataframesize = frame.length;    	
    	// whether the filter returns dataset with the expected framesize
    	try {
    		this.dCRejectionFilter.Process(data);
    		frame = (int[])data.getDataset().get(0);
    	}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
   		org.junit.Assert.assertTrue(frame.length == oldDataframesize);
    }
}
