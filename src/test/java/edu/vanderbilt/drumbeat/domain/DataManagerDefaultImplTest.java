package edu.vanderbilt.drumbeat.domain;

import java.util.ArrayList;

import org.junit.Test;

public class DataManagerDefaultImplTest {

    private DataManagerDefaultImpl dataManagerDefaultImpl = new DataManagerDefaultImpl();
	private AudioDataOnDemand dod = new AudioDataOnDemand();
	
	private void pushNewdata(ArrayList<int[]> dataset) {
		Data data = new Data();
		data.setDataset(dataset);
		this.dataManagerDefaultImpl.push(data);		
	}

	private void updateData(ArrayList<int[]> dataset) {
		Data data = new Data();
		data.setDataset(dataset);
		this.dataManagerDefaultImpl.update(data);		
	}

	@Test
    public void push() {
		// Push a dataset to the empty stack of dataManager
		this.dataManagerDefaultImpl.clear();
    	try {
    		pushNewdata(this.dod.mockRandomAudioData(100, 256));
		}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
    	ArrayList<int[]> fetched_dataset = this.dataManagerDefaultImpl.peek().getDataset();

    	// two datasets point to the same place in memory
		try {
    		pushNewdata(fetched_dataset);
    	}
		catch (Exception e) {
	    	org.junit.Assert.assertTrue("The dataset and its predecessor dataset point to the same place in memory" == e.getMessage());
		}
    	
		// dataset has different size from its predecessor 
		ArrayList<int[]> newdataset = this.dod.mockRandomAudioData(50, 256);
		try {
    		pushNewdata(newdataset);    		
		}
		catch (Exception e) {
	    	org.junit.Assert.assertTrue("The dataset has a different size from its predecessor dataset" == e.getMessage());
		}
		
    	// A correct dataset with diffrent framesize to its predecessor dataset 
		int oldsize = this.dataManagerDefaultImpl.size();
    	try {
    		pushNewdata(this.dod.mockRandomAudioData(100, 255));
		}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
    	fetched_dataset = this.dataManagerDefaultImpl.peek().getDataset();
    	org.junit.Assert.assertTrue(fetched_dataset.size() == 100);
    	org.junit.Assert.assertTrue(fetched_dataset.get(0).length == 255);
    	org.junit.Assert.assertTrue(this.dataManagerDefaultImpl.size() == oldsize+1);    	
    }

    @Test
    public void update() {
    	// Update to an empty dataManager
		this.dataManagerDefaultImpl.clear();    	
    	ArrayList<int[]> newdataset = this.dod.mockRandomAudioData(100, 256); 
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
    	ArrayList<int[]> fetched_dataset = this.dataManagerDefaultImpl.peek().getDataset();
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
		int oldsize = this.dataManagerDefaultImpl.size();
    	try {
    		updateData(this.dod.mockRandomAudioData(100, 255));
		}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
    	fetched_dataset = this.dataManagerDefaultImpl.peek().getDataset();
    	org.junit.Assert.assertTrue(fetched_dataset.size() == 100);
    	org.junit.Assert.assertTrue(fetched_dataset.get(0).length == 255);
    	org.junit.Assert.assertTrue(this.dataManagerDefaultImpl.size() == oldsize);    	
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
