package edu.vanderbilt.drumbeat.ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * @author yicui
 *
 */
@RooJavaBean
@RooToString
@RooEquals
@RooSerializable
public class CompositeDataVisualizer implements DataVisualizer {
	private static final long serialVersionUID = 1L;

	/**
	 * We name the following property dataset on purpose, in order to avoid   
	 * unnecessary setter/getter methods
	 */	
	private List<Object> dataset = new ArrayList<Object>();
	/**
	 * The bounding box is made transient because   
	 * (1) there is no need to persist it
	 * (2) since it strictly depends on the composite, it must not have a public setter method 
	 */
	private transient DataVisualizerBoundingBox boundingBox = new DataVisualizerBoundingBox(); 

	/**
	 * @see edu.vanderbilt.drumbeat.domain.Data#setDataset(java.util.List)
	 */
	public void setDataset(List<Object> dataset) {
		// If the external dataset is empty, initializing value bounding box will be troublesome  
        if (dataset.size() == 0)
        	throw new RuntimeException("The dataset is empty");	

		this.dataset = dataset;
		setBoundingBox();		
	}
	/**
	 * @see edu.vanderbilt.drumbeat.domain.Data#getDataset()
	 */
	public List<Object> getDataset() {
		return this.dataset;
	}
	/**
	 * @see edu.vanderbilt.drumbeat.ui.DataVisualizer#getBoundingBox()
	 */
	public DataVisualizerBoundingBox getBoundingBox() {
		return this.boundingBox;
	}
	/**
	 * @see edu.vanderbilt.drumbeat.ui.DataVisualizer#getColor()
	 * We place holder variable here since composite dataVisualizer cannot have a single color
	 */
	public Color getColor() { 
		return new Color(0, 0, 0);
	}
	/**
	 * @see edu.vanderbilt.drumbeat.ui.DataVisualizer#draw(edu.vanderbilt.drumbeat.ui.Drawer)
	 * Repeatedly call draw method of each dataVisualizer in the composite
	 */
	public void draw(Drawer drawer) {
		for (int i = 0; i < this.dataset.size(); i ++) {
			DataVisualizer dataVisualizer = (DataVisualizer)this.dataset.get(i);
			drawer.draw(dataVisualizer, this.boundingBox);
		}
	}
	/**
	 *  Add a dataVisualizer to the composite
	 */
	public void addDataVisualizer(DataVisualizer dataVisualizer) {
		this.dataset.add(dataVisualizer);
		setBoundingBox();
	}
	/**
	 * Remove a dataVisualizer from the composite
	 */
	public void removeDataVisualizer(DataVisualizer dataVisualizer) {
		this.dataset.remove(dataVisualizer);
		// Reset the value bounding box only when there is still dataVisualizer remained in the composite
		if (!this.dataset.isEmpty())
			setBoundingBox();
		// Otherwise, reinitialize the bounding box to its minimal size 
		else
			boundingBox = new DataVisualizerBoundingBox();
	}
	/**
	 * Clear the dataVisualizer composite
	 */
	public void clear() {
		this.dataset.clear();
		boundingBox = new DataVisualizerBoundingBox();
	}
	/**
	 * Set the bounding box of the entire composite
	 */
	private void setBoundingBox() {
		/**
		 * Set frameSize & sliceSize to be the maximum dataVisualizer in the composite
		 */
        DataVisualizer dataVisualizer = (DataVisualizer)this.dataset.get(0);
		this.boundingBox.sliceSize = dataVisualizer.getBoundingBox().sliceSize;
		this.boundingBox.frameSize = dataVisualizer.getBoundingBox().frameSize;    		

    	for (int i = 1; i < this.dataset.size(); i ++) {
    		dataVisualizer = (DataVisualizer)this.dataset.get(i);
    		if (dataVisualizer.getBoundingBox().sliceSize > this.boundingBox.sliceSize)
    			this.boundingBox.sliceSize = dataVisualizer.getBoundingBox().sliceSize;
    		if (dataVisualizer.getBoundingBox().frameSize > this.boundingBox.frameSize)
    			this.boundingBox.frameSize = dataVisualizer.getBoundingBox().frameSize;    		
    	}

		/**
		 * Reinitialize the maxvalPerFrame & minvalPerFrame if necessary
		 */
		if (this.boundingBox.sliceSize != this.boundingBox.maxvalPerFrame.length) {
			this.boundingBox.maxvalPerFrame = new int[this.boundingBox.sliceSize];
			this.boundingBox.minvalPerFrame = new int[this.boundingBox.sliceSize];
		}

		/**
		 * Set each item of maxvalPerFrame to be the maximum dataVisualizer in the composite
		 * Set each item of minvalPerFrame to be the minimum dataVisualizer in the composite
		 */
		for (int i = 0; i < this.boundingBox.sliceSize; i ++) {
			int j = 0;
			do {
				dataVisualizer = (DataVisualizer)this.dataset.get(j);
				j ++;
			}
			while (dataVisualizer.getBoundingBox().sliceSize <= i);
			this.boundingBox.maxvalPerFrame[i] = dataVisualizer.getBoundingBox().maxvalPerFrame[i];
			this.boundingBox.minvalPerFrame[i] = dataVisualizer.getBoundingBox().minvalPerFrame[i];
			for (; j < this.dataset.size(); j ++) {
				dataVisualizer = (DataVisualizer)this.dataset.get(j);
				if (dataVisualizer.getBoundingBox().sliceSize > i) {
					if (this.boundingBox.maxvalPerFrame[i] < dataVisualizer.getBoundingBox().maxvalPerFrame[i])
						this.boundingBox.maxvalPerFrame[i] = dataVisualizer.getBoundingBox().maxvalPerFrame[i];
					if (this.boundingBox.minvalPerFrame[i] > dataVisualizer.getBoundingBox().minvalPerFrame[i])
						this.boundingBox.minvalPerFrame[i] = dataVisualizer.getBoundingBox().minvalPerFrame[i];
				}
			}
		}
	}
}