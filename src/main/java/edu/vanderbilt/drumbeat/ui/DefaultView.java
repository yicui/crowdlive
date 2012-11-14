package edu.vanderbilt.drumbeat.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class DefaultView extends JComponent implements View {
	private static final long serialVersionUID = 1L;	

	@Value("40")
	@Min(1L)
	private int cellHeight;
	@Autowired
	private Drawer drawer;
	@Autowired
	private DataVisualizer dataVisualizer;

    private transient BufferedImage canvas = new BufferedImage(10, 10, BufferedImage.TYPE_3BYTE_BGR);

	@Override
	public void paint(Graphics g) {  
    	g.drawImage(this.canvas, 0, 0, null);
    }
	/**
	 * @see edu.vanderbilt.drumbeat.ui.View#drawDataVisualizer()
	 */
    public void drawDataVisualizer() {
    	DataVisualizerBoundingBox boundingBox = this.dataVisualizer.getBoundingBox();
    	this.canvas = new BufferedImage(boundingBox.frameSize, boundingBox.sliceSize*this.cellHeight, 
    			BufferedImage.TYPE_3BYTE_BGR);
        this.drawer.setCanvas(this.canvas);
        this.dataVisualizer.draw(this.drawer);
		this.repaint(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
    }
}