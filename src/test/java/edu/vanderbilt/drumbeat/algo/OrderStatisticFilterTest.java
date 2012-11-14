package edu.vanderbilt.drumbeat.algo;

import org.junit.Test;

import edu.vanderbilt.drumbeat.domain.AudioDataOnDemand;
import edu.vanderbilt.drumbeat.domain.TransposableData;

/* @author Yi Cui */
public class OrderStatisticFilterTest {

    private OrderStatisticFilter orderStatisticFilter = new OrderStatisticFilter();

    @Test
    public void Process() {
    	this.orderStatisticFilter.setWindow(8);
    	this.orderStatisticFilter.setKthLargest(4);

    	AudioDataOnDemand dod = new AudioDataOnDemand();
    	TransposableData data = new TransposableData();
    	data.setDataset(dod.mockRandomAudioData(100, 256));
		int[] frame = (int[])data.getDataset().get(0);
    	int oldDataframesize = frame.length;
    	// whether the filter returns dataset with the expected framesize
    	try {
    		this.orderStatisticFilter.Process(data);
    		frame = (int[])data.getDataset().get(0);    		    		
    	}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
    	org.junit.Assert.assertTrue(frame.length == oldDataframesize);

    	// In case of asymptotic audio dataset, median filter should return the same dataset
    	data.setDataset(dod.mockAsymptoticAudioData(100, 256));
    	try {
    		this.orderStatisticFilter.Process(data);
    	}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
    	for (int i = 0; i < data.getDataset().size()-1; i ++) {
			frame = (int[])data.getDataset().get(i);    		
    		for (int index = 0; index < frame.length; index ++)
   				org.junit.Assert.assertTrue(frame[index] == i*frame.length+index);
    	}
    	// conflicting parameters
    	this.orderStatisticFilter.setKthLargest(9);
    	data.setDataset(dod.mockRandomAudioData(100, 256));    	
		try {
			this.orderStatisticFilter.Process(data);
		}
		catch (Exception e) {
	    	org.junit.Assert.assertTrue("k must not be bigger than the window size" == e.getMessage());
		}
    }
}
