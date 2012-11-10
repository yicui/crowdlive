package edu.vanderbilt.drumbeat.algo;

import org.junit.Test;

import edu.vanderbilt.drumbeat.domain.AudioDataOnDemand;
import edu.vanderbilt.drumbeat.domain.Data;

public class TrespassCountingFilterTest {

    private TrespassCountingFilter trespassCountingFilter = new TrespassCountingFilter();

    @Test
    public void Process() {
    	this.trespassCountingFilter.setZeroLine(0);
    	AudioDataOnDemand dod = new AudioDataOnDemand();
    	Data data = new Data();
    	data.setDataset(dod.mockRandomAudioData(100, 256));
    	int oldDataframesize = data.getDataset().get(0).length;    	
    	// correct audio dataset
    	try {
    		this.trespassCountingFilter.Process(data);
    	}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
   		org.junit.Assert.assertTrue(data.getDataset().get(0).length == oldDataframesize);
    }
}
