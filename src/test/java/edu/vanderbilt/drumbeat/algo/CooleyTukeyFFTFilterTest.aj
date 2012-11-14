package edu.vanderbilt.drumbeat.algo;

import org.junit.Test;

import edu.vanderbilt.drumbeat.domain.AudioDataOnDemand;
import edu.vanderbilt.drumbeat.domain.TransposableData;

/* @author Yi Cui */
public class CooleyTukeyFFTFilterTest {

    private CooleyTukeyFFTFilter cooleyTukeyFFTFilter = new CooleyTukeyFFTFilter();

    @Test
    public void Process() {
    	AudioDataOnDemand dod = new AudioDataOnDemand();
    	TransposableData data = new TransposableData();
    	data.setDataset(dod.mockRandomAudioData(100, 256));
    	int[] frame = (int[])data.getDataset().get(0);
    	int oldDataframesize = frame.length;
    	// correct audio dataset
    	try {
    		this.cooleyTukeyFFTFilter.Process(data);
    		frame = (int[])data.getDataset().get(0);
    	}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
   		org.junit.Assert.assertTrue(frame.length*2 == oldDataframesize);

    	// audio dataset whose framesize is not power of 2
    	data.setDataset(dod.mockRandomAudioData(100, 255));
		try {
    		this.cooleyTukeyFFTFilter.Process(data);
    	}
		catch (Exception e) {
			org.junit.Assert.assertTrue("The framesize is not power of 2" == e.getMessage());
		}
    }
}
