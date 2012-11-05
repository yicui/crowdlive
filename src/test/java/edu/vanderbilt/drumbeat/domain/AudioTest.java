package edu.vanderbilt.drumbeat.domain;

import java.util.ArrayList;

import org.junit.Test;

public class AudioTest {

    private Audio audio = new Audio();

    @Test
    public void setData() {
    	// A correct dataset
    	AudioDataOnDemand dod = new AudioDataOnDemand();    	
    	try {
    		dod.setData(this.audio, 100, 256);
		}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
    	ArrayList<int[]> fetched_data = this.audio.getData();
    	org.junit.Assert.assertTrue(fetched_data.size() == 100);
    	org.junit.Assert.assertTrue(fetched_data.get(0).length == 256);
		
		// framesize is not consistent
		for (int i = 0; i < fetched_data.size()/2; i ++)
			fetched_data.set(i, new int[64]);
		try {
			this.audio.setData(fetched_data);
		}
		catch (Exception e) {
	    	org.junit.Assert.assertTrue("The audio framesize is not consistent" == e.getMessage());
		}
		
		// empty dataset
		fetched_data.clear();
		try {
			this.audio.setData(fetched_data);
		}
		catch (Exception e) {
			org.junit.Assert.assertTrue("The audio dataset is empty" == e.getMessage());
		}
    }

    @Test
    public void setProcesseddata() {
    	// A correct processed dataset
    	AudioDataOnDemand dod = new AudioDataOnDemand();    	
		try {
			dod.setData(this.audio, 100, 256);
			dod.setProcesseddata(this.audio, 100, 128);
		}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
    	ArrayList<int[]> fetched_processeddata = this.audio.getProcesseddata();
    	org.junit.Assert.assertTrue(fetched_processeddata.size() == 100);
    	org.junit.Assert.assertTrue(fetched_processeddata.get(0).length == 128);
		
		// framesize is not consistent
    	fetched_processeddata.set(0, new int[120]);
		try {
			this.audio.setProcesseddata(fetched_processeddata);
		}
		catch (Exception e) {
			org.junit.Assert.assertTrue("The processed audio framesize is not consistent" == e.getMessage());
		}

		// dataset size is different from the original dataset
		fetched_processeddata.remove(0);
		try {
			this.audio.setProcesseddata(fetched_processeddata);
		}
		catch (Exception e) {
			org.junit.Assert.assertTrue("The processed audio dataset has a different size from the original dataset" == e.getMessage());
		}
    }

    @Test
    public void getTitle() {
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void setTitle() {
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void getPathurl() {
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void setPathurl() {
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void getDuration() {
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void setDuration() {
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void getVideos() {
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void setVideos() {
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void getUploader() {
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void setUploader() {
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void entityManager() {
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void countAudios() {
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void findAllAudios() {
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void findAudio() {
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void findAudioEntries() {
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void persist() {
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void remove() {
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void flush() {
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void clear() {
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void merge() {
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void getId() {
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void setId() {
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void getVersion() {
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void setVersion() {
        org.junit.Assert.assertTrue(true);
    }
}
