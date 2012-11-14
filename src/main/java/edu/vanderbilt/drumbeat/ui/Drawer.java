package edu.vanderbilt.drumbeat.ui;

import java.awt.image.BufferedImage;

/**
 * @author yicui
 *
 */
public interface Drawer {
	/**
	 * Sets the canvas on which the drawer operates.
	 */
	public void setCanvas(BufferedImage canvas);
	/**
	 * This method visualizes the dataset of the dataVisualizer parameter.
	 * The data to be visualized is normalized against the boundingBox parameter.
	 * The boundingBox is of the composite the dataVisualizer object belongs to,
	 * since most drawers normalize against the boundingBox of the composite.
	 * 
	 * In case the drawer only normalizes against the boundingBox of the dataVisualizer,
	 * it is advised to ignore the composite boundingBox parameter and directly retrieve 
	 * the boundingBox of the dataVisualizer by calling its getBoundingBox method.
	 */
	public void draw(DataVisualizer dataVisualizer, DataVisualizerBoundingBox boundingBox);
}