package edu.vanderbilt.drumbeat.algo;

import org.junit.Test;

import edu.vanderbilt.drumbeat.domain.AudioDataOnDemand;
import edu.vanderbilt.drumbeat.domain.Data;

public class OrderStatisticFilterTest {

    private OrderStatisticFilter orderStatisticFilter = new OrderStatisticFilter();

    @Test
    public void Process() {
    	this.orderStatisticFilter.setWindow(8);
    	this.orderStatisticFilter.setKthLargest(4);

    	AudioDataOnDemand dod = new AudioDataOnDemand();
    	Data data = new Data();
    	data.setDataset(dod.mockRandomAudioData(100, 256));
    	int oldDataframesize = data.getDataset().get(0).length;    	
    	// whether the filter returns dataset with the expected framesize
    	try {
    		this.orderStatisticFilter.Process(data);
    	}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
    	org.junit.Assert.assertTrue(data.getDataset().get(0).length == oldDataframesize);

    	// In case of asymptotic audio dataset, median filter should return the same dataset
    	data.setDataset(dod.mockAsymptoticAudioData(100, 256));
    	try {
    		this.orderStatisticFilter.Process(data);
    	}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
    	for (int i = 0; i < data.getDataset().size()-1; i ++)
    		for (int index = 0; index < data.getDataset().get(i).length; index ++)
    			if (data.getDataset().get(i)[index] != i*data.getDataset().get(i).length+index)
    				org.junit.Assert.fail("Median filter fails to return the median value");
    	
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
