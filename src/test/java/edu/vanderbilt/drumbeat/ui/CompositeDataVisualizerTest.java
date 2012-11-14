package edu.vanderbilt.drumbeat.ui;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.vanderbilt.drumbeat.domain.AudioDataOnDemand;

public class CompositeDataVisualizerTest {
	private CompositeDataVisualizer compositeDataVisualizer = new CompositeDataVisualizer();

    private void fillDataVisualizerList(List<Object> transposableDataVisualizerList) {
    	AudioDataOnDemand dod = new AudioDataOnDemand();
    	TransposableDataVisualizer transposableDataVisualizer = new TransposableDataVisualizer();
    	transposableDataVisualizer.setDataset(dod.mockAsymptoticAudioData(100, 256));
    	transposableDataVisualizerList.add(transposableDataVisualizer);
    	transposableDataVisualizer = new TransposableDataVisualizer();
    	transposableDataVisualizer.setDataset(dod.mockAsymptoticAudioData(200, 128));
    	transposableDataVisualizerList.add(transposableDataVisualizer);
    	transposableDataVisualizer = new TransposableDataVisualizer();
    	transposableDataVisualizer.setDataset(dod.mockAsymptoticAudioData(50, 300));
    	transposableDataVisualizerList.add(transposableDataVisualizer);
    }
    @Test
    public void setDataset() {
    	this.compositeDataVisualizer.clear();
    	List<Object> transposableDataVisualizerList = new ArrayList<Object>();
    	// empty DataVisualizerList
    	try {
    		this.compositeDataVisualizer.setDataset(transposableDataVisualizerList);
    	}
		catch (Exception e) {
			org.junit.Assert.assertTrue("The dataset is empty" == e.getMessage());
		}

    	// whether the bounding box is set correctly over a list of TransposableDataVisualizers
    	fillDataVisualizerList(transposableDataVisualizerList);
    	try {
    		this.compositeDataVisualizer.setDataset(transposableDataVisualizerList);
    	}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
    	DataVisualizerBoundingBox boundingBox = this.compositeDataVisualizer.getBoundingBox();
    	org.junit.Assert.assertTrue(boundingBox.frameSize == 300);
    	org.junit.Assert.assertTrue(boundingBox.sliceSize == 200);
    	org.junit.Assert.assertTrue(boundingBox.minvalPerFrame.length == 200);
    	org.junit.Assert.assertTrue(boundingBox.maxvalPerFrame.length == 200);
    	
    	for (int i = 0; i < 50; i ++) {
    		org.junit.Assert.assertTrue(boundingBox.minvalPerFrame[i] == i*128);
    		org.junit.Assert.assertTrue(boundingBox.maxvalPerFrame[i] == (i+1)*300-1);
    	}
    	for (int i = 50; i < 100; i ++) {
    		org.junit.Assert.assertTrue(boundingBox.minvalPerFrame[i] == i*128);
    		org.junit.Assert.assertTrue(boundingBox.maxvalPerFrame[i] == (i+1)*256-1);
    	}
    	for (int i = 100; i < 200; i ++) {
    		org.junit.Assert.assertTrue(boundingBox.minvalPerFrame[i] == i*128);
    		org.junit.Assert.assertTrue(boundingBox.maxvalPerFrame[i] == (i+1)*128-1);
    	}
    }

    @Test
    public void addDataVisualizer() {
    	this.compositeDataVisualizer.clear();   	
    	// add the first DataVisualizerList
    	AudioDataOnDemand dod = new AudioDataOnDemand();
    	TransposableDataVisualizer transposableDataVisualizer = new TransposableDataVisualizer();
    	transposableDataVisualizer.setDataset(dod.mockAsymptoticAudioData(100, 256));
    	try {
    		this.compositeDataVisualizer.addDataVisualizer(transposableDataVisualizer);
    	}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
    	DataVisualizerBoundingBox boundingBox = this.compositeDataVisualizer.getBoundingBox();
    	org.junit.Assert.assertTrue(boundingBox.frameSize == 256);
    	org.junit.Assert.assertTrue(boundingBox.sliceSize == 100);
    	org.junit.Assert.assertTrue(boundingBox.minvalPerFrame.length == 100);
    	org.junit.Assert.assertTrue(boundingBox.maxvalPerFrame.length == 100);
    	for (int i = 0; i < 100; i ++) {
    		org.junit.Assert.assertTrue(boundingBox.minvalPerFrame[i] == i*256);
    		org.junit.Assert.assertTrue(boundingBox.maxvalPerFrame[i] == (i+1)*256-1);
    	}
    	
    	// add the second DataVisualizerList    	
    	transposableDataVisualizer = new TransposableDataVisualizer();
    	transposableDataVisualizer.setDataset(dod.mockAsymptoticAudioData(50, 512));
    	try {
    		this.compositeDataVisualizer.addDataVisualizer(transposableDataVisualizer);
    	}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
    	boundingBox = this.compositeDataVisualizer.getBoundingBox();
    	org.junit.Assert.assertTrue(boundingBox.frameSize == 512);
    	org.junit.Assert.assertTrue(boundingBox.sliceSize == 100);
    	org.junit.Assert.assertTrue(boundingBox.minvalPerFrame.length == 100);
    	org.junit.Assert.assertTrue(boundingBox.maxvalPerFrame.length == 100);
    	for (int i = 0; i < 50; i ++) {
    		org.junit.Assert.assertTrue(boundingBox.minvalPerFrame[i] == i*256);
    		org.junit.Assert.assertTrue(boundingBox.maxvalPerFrame[i] == (i+1)*512-1);
    	}
    	for (int i = 50; i < 100; i ++) {
    		org.junit.Assert.assertTrue(boundingBox.minvalPerFrame[i] == i*256);
    		org.junit.Assert.assertTrue(boundingBox.maxvalPerFrame[i] == (i+1)*256-1);
    	}    	
    }

    @Test
    public void removeDataVisualizer() {
    	this.compositeDataVisualizer.clear();
    	// First fill the composite with a list of DataVisualizers 
    	List<Object> transposableDataVisualizerList = new ArrayList<Object>();
    	fillDataVisualizerList(transposableDataVisualizerList);
    	this.compositeDataVisualizer.setDataset(transposableDataVisualizerList);
    	// remove one from the DataVisualizerList
    	try {
    		this.compositeDataVisualizer.removeDataVisualizer((DataVisualizer)transposableDataVisualizerList.get(1));
    	}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
    	DataVisualizerBoundingBox boundingBox = this.compositeDataVisualizer.getBoundingBox();
    	org.junit.Assert.assertTrue(boundingBox.frameSize == 300);
    	org.junit.Assert.assertTrue(boundingBox.sliceSize == 100);
    	org.junit.Assert.assertTrue(boundingBox.minvalPerFrame.length == 100);
    	org.junit.Assert.assertTrue(boundingBox.maxvalPerFrame.length == 100);
    	for (int i = 0; i < 50; i ++) {
    		org.junit.Assert.assertTrue(boundingBox.minvalPerFrame[i] == i*256);
    		org.junit.Assert.assertTrue(boundingBox.maxvalPerFrame[i] == (i+1)*300-1);
    	}
    	for (int i = 50; i < 100; i ++) {
    		org.junit.Assert.assertTrue(boundingBox.minvalPerFrame[i] == i*256);
    		org.junit.Assert.assertTrue(boundingBox.maxvalPerFrame[i] == (i+1)*256-1);
    	}
    	
    	// then remove another one
    	try {
    		this.compositeDataVisualizer.removeDataVisualizer((DataVisualizer)transposableDataVisualizerList.get(0));    	
    	}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
    	boundingBox = this.compositeDataVisualizer.getBoundingBox();
    	org.junit.Assert.assertTrue(boundingBox.frameSize == 300);
    	org.junit.Assert.assertTrue(boundingBox.sliceSize == 50);
    	org.junit.Assert.assertTrue(boundingBox.minvalPerFrame.length == 50);
    	org.junit.Assert.assertTrue(boundingBox.maxvalPerFrame.length == 50);
    	for (int i = 0; i < 50; i ++) {
    		org.junit.Assert.assertTrue(boundingBox.minvalPerFrame[i] == i*300);
    		org.junit.Assert.assertTrue(boundingBox.maxvalPerFrame[i] == (i+1)*300-1);
    	}
    	
    	// finally remove the last one    	
    	try {
    		this.compositeDataVisualizer.removeDataVisualizer((DataVisualizer)transposableDataVisualizerList.get(0));
    	}
		catch (Exception e) {
			org.junit.Assert.fail("Unexpected exception thrown " + e.getMessage());
		}
    	boundingBox = this.compositeDataVisualizer.getBoundingBox();
    	org.junit.Assert.assertTrue(boundingBox.frameSize == 1);
    	org.junit.Assert.assertTrue(boundingBox.sliceSize == 1);
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

    @Test
    public void clear() {
        org.junit.Assert.assertTrue(true);
    }
}