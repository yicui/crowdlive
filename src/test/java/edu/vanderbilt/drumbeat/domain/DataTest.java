package edu.vanderbilt.drumbeat.domain;

import java.util.ArrayList;

import org.junit.Test;

public class DataTest {

	private Data data = new Data();

    @Test
    public void setDataset() {
    	// correct audio dataset
    	AudioDataOnDemand dod = new AudioDataOnDemand();    	
    	try {
    		this.data.setDataset(dod.mockRandomAudioData(100, 256));
		}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
    	ArrayList<int[]> fetched_dataset = this.data.getDataset();
    	org.junit.Assert.assertTrue(fetched_dataset.size() == 100);
    	org.junit.Assert.assertTrue(fetched_dataset.get(0).length == 256);
    	
    	// framesize is not consistent
		for (int i = 0; i < fetched_dataset.size()/2; i ++)
			fetched_dataset.set(i, new int[64]);
		try {
    		this.data.setDataset(fetched_dataset);
		}
		catch (Exception e) {
	    	org.junit.Assert.assertTrue("The dataset framesize is not consistent" == e.getMessage());
		}  	
		
		// empty dataset
		fetched_dataset.clear();
		try {
    		this.data.setDataset(fetched_dataset);
		}
		catch (Exception e) {
			org.junit.Assert.assertTrue("The dataset is empty" == e.getMessage());
		}	
    }
}