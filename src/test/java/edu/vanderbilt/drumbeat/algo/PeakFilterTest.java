package edu.vanderbilt.drumbeat.algo;

import org.junit.Test;

import edu.vanderbilt.drumbeat.domain.AudioDataOnDemand;
import edu.vanderbilt.drumbeat.domain.Data;

/* @author Yi Cui */
public class PeakFilterTest {

	private PeakFilter peakFilter = new PeakFilter();

    @Test
    public void Process() {
    	AudioDataOnDemand dod = new AudioDataOnDemand();
    	Data data = new Data();
    	data.setDataset(dod.mockRandomAudioData(100, 256));
    	int oldDataframesize = data.getDataset().get(0).length;    	
    	// whether the filter returns dataset with the expected framesize 
    	this.peakFilter.setMaxPeakWidth(8);
    	this.peakFilter.setMinPeakWidth(0);
    	this.peakFilter.setMinPeakVolume(10000);
    	this.peakFilter.setMaxPeakVolume(100000);
    	try {
    		this.peakFilter.Process(data);
    	}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
   		org.junit.Assert.assertTrue(data.getDataset().get(0).length == oldDataframesize);

   		// the filter should return a dataset of total zero when given conflicting parameters
    	this.peakFilter.setMaxPeakWidth(5);
    	this.peakFilter.setMinPeakWidth(6);
    	data.setDataset(dod.mockRandomAudioData(100, 256));    	
    	try {
    		this.peakFilter.Process(data);
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