package edu.vanderbilt.drumbeat.domain;

import java.util.List;

import org.junit.Test;

/* @author Yi Cui */
public class TransposableDataTest {

	private TransposableData data = new TransposableData();

    @Test
    public void setDataset() {
    	// correct audio dataset
    	AudioDataOnDemand dod = new AudioDataOnDemand();
    	List<Object> fetched_dataset = this.data.getDataset();
    	int[] frame = new int[1];
    	try {
    		this.data.setDataset(dod.mockRandomAudioData(100, 256));
        	fetched_dataset = this.data.getDataset();
        	frame = (int[])fetched_dataset.get(0);
        }
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
    	org.junit.Assert.assertTrue(fetched_dataset.size() == 100);
    	org.junit.Assert.assertTrue(frame.length == 256);
    	
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

    @Test
    public void getTranspose() {
    	// asymptotic audio dataset
    	AudioDataOnDemand dod = new AudioDataOnDemand();
    	List<Object> fetched_dataset = this.data.getDataset();
    	int[] frame = new int[1];
    	try {
    		this.data.setDataset(dod.mockAsymptoticAudioData(100, 256));
    		fetched_dataset = this.data.getTranspose().getDataset();
        	frame = (int[])fetched_dataset.get(0);
        }
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
    	org.junit.Assert.assertTrue(fetched_dataset.size() == 256);
    	org.junit.Assert.assertTrue(frame.length == 100);
    	
    	for (int i = 0; i < fetched_dataset.size(); i ++) {
    		frame = (int[])fetched_dataset.get(i);
    		for (int j = 0; j < frame.length; j ++)
    			org.junit.Assert.assertTrue(frame[j] == j*256+i);
    	}
    }
}