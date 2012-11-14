package edu.vanderbilt.drumbeat.ui;

/**
 * @author yicui
 * 
 * A DataVisualizer object must have a bounding box in order to get its own dataset drawn properly.
 * The calculation of DataVisualizerBoundingBox properties are left to specific implementations of
 * the DataVisualizer, since TransposableDataVisualizer and CompositeDataVisualizer have completely
 * different ways of calculation.
 * 
 * The bounding box is initialized to its minimal size
 */

public class DataVisualizerBoundingBox {
	/**
	 * Size of the maximum frame, i.e., width of the DataVisualizer
	 */
	public int frameSize = 1;
	/**
	 * Size of the maximum dataset size, i.e., height of the DataVisualizer	
	 */
	public int sliceSize = 1;
	/**
	 * The array carrying maximum value of each frame
	 */
	public int[] maxvalPerFrame = new int[1];
	/**
	 * The array carrying minimum value of each frame	
	 */
	public int[] minvalPerFrame = new int[1];
}
