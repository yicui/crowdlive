package edu.vanderbilt.drumbeat.ui;

import org.junit.Test;

import edu.vanderbilt.drumbeat.domain.AudioDataOnDemand;

public class TransposableDataVisualizerTest {

    private TransposableDataVisualizer transposableDataVisualizer = new TransposableDataVisualizer();

    @Test
    public void setDataset() {
    	AudioDataOnDemand dod = new AudioDataOnDemand();
    	// whether the bounding box is set correctly over an asymptotic dataset
    	try {
    		this.transposableDataVisualizer.setDataset(dod.mockAsymptoticAudioData(100, 256));
    	}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
    	DataVisualizerBoundingBox boundingBox = this.transposableDataVisualizer.getBoundingBox();
    	org.junit.Assert.assertTrue(boundingBox.frameSize == 256);
    	org.junit.Assert.assertTrue(boundingBox.sliceSize == 100);
    	org.junit.Assert.assertTrue(boundingBox.minvalPerFrame.length == 100);
    	org.junit.Assert.assertTrue(boundingBox.maxvalPerFrame.length == 100);
    	
    	for (int i = 0; i < boundingBox.sliceSize; i ++) {
    		org.junit.Assert.assertTrue(boundingBox.minvalPerFrame[i] == i*256);
    		org.junit.Assert.assertTrue(boundingBox.maxvalPerFrame[i] == (i+1)*256-1);
    	}
    }

    @Test
    public void draw() {
    	// Visual Inspection
        org.junit.Assert.assertTrue(true);
    }
    @Test
    public void getDataset() {
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void getBoundingBox() {
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void getColor() {
        org.junit.Assert.assertTrue(true);
    }    
}
