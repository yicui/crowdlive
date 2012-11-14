package edu.vanderbilt.drumbeat.algo;

import org.junit.Test;

import edu.vanderbilt.drumbeat.domain.AudioDataOnDemand;
import edu.vanderbilt.drumbeat.domain.Data;
import edu.vanderbilt.drumbeat.domain.TransposableData;

/* @author Yi Cui */
public class TrespassFilterTest {

    private TrespassFilter trespassFilter = new TrespassFilter();

    @Test
    public void Process() {
    	this.trespassFilter.setZeroLine(0);
    	AudioDataOnDemand dod = new AudioDataOnDemand();
    	TransposableData data = new TransposableData();
    	data.setDataset(dod.mockRandomAudioData(100, 256));
		int[] frame = (int[])data.getDataset().get(0);    		    	    	    	
    	int oldDataframesize = frame.length;    
    	// correct audio dataset
    	try {
    		this.trespassFilter.Process(data);
    		frame = (int[])data.getDataset().get(0);    		
    	}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
   		org.junit.Assert.assertTrue(frame.length == oldDataframesize);
    }
}