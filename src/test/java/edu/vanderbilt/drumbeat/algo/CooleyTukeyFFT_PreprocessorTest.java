package edu.vanderbilt.drumbeat.algo;

import java.util.ArrayList;

import org.junit.Test;

import edu.vanderbilt.drumbeat.domain.Audio;
import edu.vanderbilt.drumbeat.domain.AudioDataOnDemand;

public class CooleyTukeyFFT_PreprocessorTest {

    private CooleyTukeyFFT_Preprocessor cooleyTukeyFFT_Preprocessor = new CooleyTukeyFFT_Preprocessor();

    @Test
    public void Process() {
    	AudioDataOnDemand dod = new AudioDataOnDemand();
    	Audio audio = dod.getNewTransientAudio(5);
    	cooleyTukeyFFT_Preprocessor.setAudio(audio);

    	// correct audio dataset
    	try {
    		audio.setDuration(3000);
    		dod.setData(audio, 100, 256);
    		cooleyTukeyFFT_Preprocessor.Process();
    	}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
    	ArrayList<int[]> fetched_processeddata = audio.getProcesseddata();
    	ArrayList<int[]> fetched_data = audio.getData();
    	org.junit.Assert.assertTrue(3000 == audio.getDuration());
    	org.junit.Assert.assertTrue(fetched_processeddata.size() == fetched_data.size());
    	for (int i = 0; i < fetched_processeddata.size(); i ++)
    		org.junit.Assert.assertTrue(fetched_processeddata.get(i).length*2 == fetched_data.get(i).length);

    	// audio dataset whose framesize is not power of 2
    	try {
    		dod.setData(audio, 100, 255);
    		cooleyTukeyFFT_Preprocessor.Process();
    	}
		catch (Exception e) {
			org.junit.Assert.assertTrue("The framesize is not power of 2" == e.getMessage());
		}
    }
    
    @Test
    public void getAudio() {
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void setAudio() {
        org.junit.Assert.assertTrue(true);
    }
}
