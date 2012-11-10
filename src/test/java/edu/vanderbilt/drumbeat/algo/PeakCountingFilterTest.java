package edu.vanderbilt.drumbeat.algo;

import org.junit.Test;

import edu.vanderbilt.drumbeat.domain.AudioDataOnDemand;
import edu.vanderbilt.drumbeat.domain.Data;

/* @author Yi Cui */
public class PeakCountingFilterTest {

	private PeakCountingFilter peakCountingFilter = new PeakCountingFilter();

    @Test
    public void Process() {
    	AudioDataOnDemand dod = new AudioDataOnDemand();
    	Data data = new Data();
    	data.setDataset(dod.mockRandomAudioData(100, 256));
    	int oldDataframesize = data.getDataset().get(0).length;    	
    	// whether the filter returns dataset with the expected framesize 
    	this.peakCountingFilter.setMaxPeakWidth(8);
    	this.peakCountingFilter.setMinPeakWidth(0);
    	this.peakCountingFilter.setMinPeakVolume(10000);
    	this.peakCountingFilter.setMaxPeakVolume(100000);
    	try {
    		this.peakCountingFilter.Process(data);
    	}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
   		org.junit.Assert.assertTrue(data.getDataset().get(0).length == oldDataframesize);

   		// the filter should return a dataset of total zero when given conflicting parameters
    	this.peakCountingFilter.setMaxPeakWidth(5);
    	this.peakCountingFilter.setMinPeakWidth(6);
    	try {
    		this.peakCountingFilter.Process(data);
    	}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
    	for (int i = 0; i < data.getDataset().size(); i ++)
    		for (int index = 0; index < data.getDataset().get(i).length; index ++)
    			if (data.getDataset().get(i)[index] != 0)
    				org.junit.Assert.fail("Peaks are found when maxPeakWidth is even smaller than minPeakWidth");
    }
}