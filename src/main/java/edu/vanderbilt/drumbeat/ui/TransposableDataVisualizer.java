package edu.vanderbilt.drumbeat.ui;

import java.awt.Color;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

import edu.vanderbilt.drumbeat.domain.TransposableData;

/**
 * @author yicui
 *
 */

@RooJavaBean
@RooToString
@RooSerializable
public class TransposableDataVisualizer implements DataVisualizer {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private Color color = new Color(0, 0, 0);

	/**
	 * We name the following property dataset on purpose, in order to avoid   
	 * unnecessary setter/getter methods
	 */
	private TransposableData dataset = new TransposableData();
	/**
	 * The bounding box is made transient because   
	 * (1) there is no need to persist it
	 * (2) since it strictly depends on the dataset, it must not have a public setter method 
	 */
	private transient DataVisualizerBoundingBox boundingBox = new DataVisualizerBoundingBox();

	/**
	 * @see edu.vanderbilt.drumbeat.domain.Data#setDataset(java.util.List)
	 */
	public void setDataset(List<Object> dataset) {
		/**
		 * Input validation is passed to TransposableDat object 
		 */
		this.dataset.setDataset(dataset);
		/**
		 * Since a TransposableData is a perfect matrix, 
		 * its height (sliceSize) is the dataset size, and width (frameSize) is the frame size 
		 */ 
		this.boundingBox.sliceSize = this.dataset.getDataset().size();
		int[] frame = (int[])this.dataset.getDataset().get(0);
		this.boundingBox.frameSize = frame.length;

		// Set the maximum & minimum value of each frame
		this.boundingBox.maxvalPerFrame = new int[this.boundingBox.sliceSize];
		this.boundingBox.minvalPerFrame = new int[this.boundingBox.sliceSize];
		for (int i = 0; i < this.boundingBox.sliceSize; i ++) {
			frame = (int[])this.dataset.getDataset().get(i);
			this.boundingBox.maxvalPerFrame[i] = this.boundingBox.minvalPerFrame[i] = frame[0];
			for (int j = 1; j < frame.length; j ++) {
				if (this.boundingBox.maxvalPerFrame[i] < frame[j])
					this.boundingBox.maxvalPerFrame[i] = frame[j];
				if (this.boundingBox.minvalPerFrame[i] > frame[j])
					this.boundingBox.minvalPerFrame[i] = frame[j];				
			}
		}
	}
	/**
	 * @see edu.vanderbilt.drumbeat.domain.Data#getDataset()
	 */
	public List<Object> getDataset() {
		return this.dataset.getDataset();
	}
	/**
	 * @see edu.vanderbilt.drumbeat.ui.DataVisualizer#getBoundingBox()
	 */
	public DataVisualizerBoundingBox getBoundingBox() {
		return this.boundingBox;
	}
	/**
	 * @see edu.vanderbilt.drumbeat.ui.DataVisualizer#getColor()
	 */
	public Color getColor() {
		return this.color;
	}
	/**
	 * @see edu.vanderbilt.drumbeat.ui.DataVisualizer#draw(edu.vanderbilt.drumbeat.ui.Drawer)
	 */
	public void draw(Drawer drawer) {
		drawer.draw(this, this.boundingBox);
	}
}