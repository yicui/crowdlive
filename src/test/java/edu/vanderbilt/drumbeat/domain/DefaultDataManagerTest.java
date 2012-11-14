package edu.vanderbilt.drumbeat.domain;

import java.util.List;

import org.junit.Test;

/* @author Yi Cui */
public class DefaultDataManagerTest {

    private DefaultDataManager dataManager = new DefaultDataManager();
	private AudioDataOnDemand dod = new AudioDataOnDemand();
	
	private void pushNewdata(List<Object> dataset) {
		TransposableData data = new TransposableData();
		data.setDataset(dataset);
		this.dataManager.push(data);		
	}

	private void updateData(List<Object> dataset) {
		TransposableData data = new TransposableData();
		data.setDataset(dataset);
		this.dataManager.update(data);		
	}

	@Test
    public void push() {
		// Push a dataset to the empty stack of dataManager
		this.dataManager.clear();
    	try {
    		pushNewdata(this.dod.mockRandomAudioData(100, 256));
		}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
    	List<Object> fetched_dataset = this.dataManager.peek().getDataset();

    	// two datasets point to the same place in memory
		try {
    		pushNewdata(fetched_dataset);
    	}
		catch (Exception e) {
	    	org.junit.Assert.assertTrue("The dataset and its predecessor dataset point to the same place in memory" == e.getMessage());
		}
    	
		// dataset has different size from its predecessor 
		List<Object> newdataset = this.dod.mockRandomAudioData(50, 256);
		try {
    		pushNewdata(newdataset);    		
		}
		catch (Exception e) {
	    	org.junit.Assert.assertTrue("The dataset has a different size from its predecessor dataset" == e.getMessage());
		}
		
    	// A correct dataset with diffrent framesize to its predecessor dataset 
		int oldsize = this.dataManager.size();
		int[] frame = new int[1];
    	try {
    		pushNewdata(this.dod.mockRandomAudioData(100, 255));
        	fetched_dataset = this.dataManager.peek().getDataset();
        	frame = (int[])fetched_dataset.get(0);
		}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
    	org.junit.Assert.assertTrue(fetched_dataset.size() == 100);
    	org.junit.Assert.assertTrue(frame.length == 255);
    	org.junit.Assert.assertTrue(this.dataManager.size() == oldsize+1);    	
    }

    @Test
    public void update() {
    	// Update to an empty dataManager
		this.dataManager.clear();    	
		List<Object> newdataset = this.dod.mockRandomAudioData(100, 256); 
    	try {
    		updateData(newdataset);
    	}
		catch (Exception e) {
			org.junit.Assert.assertTrue("There is no dataset to update with" == e.getMessage());
		}
    	// Push a dataset to the empty stack of dataManager
    	try {
    		pushNewdata(newdataset);
    	}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
		List<Object> fetched_dataset = this.dataManager.peek().getDataset();
		// update the dataset with different size
		try {
			updateData(this.dod.mockRandomAudioData(50, 256));
		}
		catch (Exception e) {
	    	org.junit.Assert.assertTrue("The dataset has a different size from its predecessor dataset" == e.getMessage());
		}
    	// Update the current dataset with diffrent framesize
		for (int i = 0; i < newdataset.size(); i ++)
			newdataset.set(i, new int[64]);		
		int oldsize = this.dataManager.size();
		int[] frame = new int[1];
    	try {
    		updateData(this.dod.mockRandomAudioData(100, 255));
        	fetched_dataset = this.dataManager.peek().getDataset();
        	frame = (int[])fetched_dataset.get(0);
		}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
    	org.junit.Assert.assertTrue(fetched_dataset.size() == 100);
    	org.junit.Assert.assertTrue(frame.length == 255);
    	org.junit.Assert.assertTrue(this.dataManager.size() == oldsize);    	
    }
    
    @Test
    public void pop() {
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void peek() {
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void getFilters() {
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void setFilters() {
        org.junit.Assert.assertTrue(true);
    }
}