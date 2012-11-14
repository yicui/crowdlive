package edu.vanderbilt.drumbeat.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * @author yicui
 * This drawer lays all frames of the dataVisualizer object in a top-down fashion,
 * and draw each frame as a continuous line 
 */
@RooJavaBean
@RooToString
@RooEquals
@RooSerializable
public class LineDrawer implements Drawer {
	private static final long serialVersionUID = 1L;
	
	private BufferedImage canvas;
	/**
	 * @see edu.vanderbilt.drumbeat.ui.Drawer#setCanvas(java.awt.image.BufferedImage)
	 */
	public void setCanvas(BufferedImage canvas) {
		this.canvas = canvas;
	}
	/**
	 * @see edu.vanderbilt.drumbeat.ui.Drawer#draw(edu.vanderbilt.drumbeat.ui.DataVisualizer)
	 */
	public void draw(DataVisualizer dataVisualizer, DataVisualizerBoundingBox boundingBox) {
        Graphics canvasGraphics = this.canvas.getGraphics();
        /**
         * The color by which the data is to be visualized is retrieved by calling 
         * getColor method of the dataVisualizer object.
         */
        canvasGraphics.setColor(dataVisualizer.getColor());
        
        int yOffset = 0;
        /**
         * In order to visualize the entire dataset of the dataVisualizer, 
         * we need to stretch fit its bounding box into the drawing canvas
         */
       	int cellHeight = this.canvas.getHeight()/boundingBox.sliceSize;
       	float xScale = (float)(this.canvas.getWidth())/(float)(boundingBox.frameSize);

       	// Start drawing
		for (int i = 0; i < dataVisualizer.getDataset().size(); i ++) {
			int range = ValueRange(boundingBox.minvalPerFrame[i], boundingBox.maxvalPerFrame[i]);
			int[] frame = (int[])dataVisualizer.getDataset().get(i);
			int firstY = yOffset+(int)(cellHeight*(float)(frame[0]+boundingBox.minvalPerFrame[i])/(float)(range));

			for (int j = 1; j < frame.length; j ++) {
				int secondY = yOffset+(int)(cellHeight*(float)(frame[j]+boundingBox.minvalPerFrame[i])/(float)(range));
				canvasGraphics.drawLine((int)((j-1)*xScale), firstY, (int)(j*xScale), secondY);
				firstY = secondY;
			}
			yOffset += cellHeight;
		}
	}
	private int ValueRange(int min, int max) {
		if (min < 0) {
			if (max < 0) return -min;
			else return (max-min);
		}
		else return max;
	}
}