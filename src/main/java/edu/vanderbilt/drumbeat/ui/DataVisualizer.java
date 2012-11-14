package edu.vanderbilt.drumbeat.ui;

import java.awt.Color;

import edu.vanderbilt.drumbeat.domain.Data;
/**
 * @author yicui
 *
 */

public interface DataVisualizer extends Data {
	public DataVisualizerBoundingBox getBoundingBox();
	public Color getColor();
	public void draw(Drawer drawer);
}