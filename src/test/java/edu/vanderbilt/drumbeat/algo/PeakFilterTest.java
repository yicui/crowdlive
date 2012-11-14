package edu.vanderbilt.drumbeat.algo;

import org.junit.Test;

import edu.vanderbilt.drumbeat.domain.AudioDataOnDemand;
import edu.vanderbilt.drumbeat.domain.TransposableData;

/* @author Yi Cui */
public class PeakFilterTest {

	private PeakFilter peakFilter = new PeakFilter();

    @Test
    public void Process() {
    	AudioDataOnDemand dod = new AudioDataOnDemand();
    	TransposableData data = new TransposableData();
    	data.setDataset(dod.mockRandomAudioData(100, 256));
		int[] frame = (int[])data.getDataset().get(0);    		    	    	    	
    	int oldDataframesize = frame.length;       	   	
    	// whether the filter returns dataset with the expected framesize 
    	this.peakFilter.setMaxPeakWidth(8);
    	this.peakFilter.setMinPeakWidth(0);
    	this.peakFilter.setMinPeakVolume(10000);
    	this.peakFilter.setMaxPeakVolume(100000);
    	try {
    		this.peakFilter.Process(data);
    		frame = (int[])data.getDataset().get(0);
    	}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
   		org.junit.Assert.assertTrue(frame.length == oldDataframesize);

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
    	for (int i = 0; i < data.getDataset().size(); i ++) {
			frame = (int[])data.getDataset().get(i);
    		for (int index = 0; index < frame.length; index ++)
    			if (frame[index] != 0)
    				org.junit.Assert.fail("Peaks are found when maxPeakWidth is even smaller than minPeakWidth");
    	}
    }
}