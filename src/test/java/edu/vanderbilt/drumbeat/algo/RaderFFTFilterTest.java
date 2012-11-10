package edu.vanderbilt.drumbeat.algo;

import org.junit.Test;

import edu.vanderbilt.drumbeat.domain.AudioDataOnDemand;
import edu.vanderbilt.drumbeat.domain.Data;

/* @author Yi Cui */
public class RaderFFTFilterTest {
	
    private RaderFFTFilter raderFFTFilter = new RaderFFTFilter();

    @Test
    public void Process() {
    	AudioDataOnDemand dod = new AudioDataOnDemand();
    	Data data = new Data();
    	data.setDataset(dod.mockRandomAudioData(100, 256));
    	int oldDataframesize = data.getDataset().get(0).length;
    	// correct audio dataset    	
    	try {
    		this.raderFFTFilter.Process(data);
    	}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
   		org.junit.Assert.assertTrue(data.getDataset().get(0).length*2 == oldDataframesize);

    	// audio dataset whose framesize is not power of 2
    	data.setDataset(dod.mockRandomAudioData(100, 255));
		try {
    		this.raderFFTFilter.Process(data);
    	}
		catch (Exception e) {
			org.junit.Assert.assertTrue("The framesize is not power of 2" == e.getMessage());
		}
    }
}