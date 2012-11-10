package edu.vanderbilt.drumbeat.algo;

import java.util.ArrayList;

import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

import edu.vanderbilt.drumbeat.domain.Data;

@RooJavaBean
@RooToString
@RooEquals
@RooSerializable
public class DifferentialFilter implements Filter {
	private static final long serialVersionUID = 1L;	
	
	@Value("1")
	@Min(1L)
	private int frameDistance;

	/* This filter goes through the dataset and returns the difference between the current frame and its kth predecessor frame.     
	 * The default frame distance is 1, which is the immediate predecessor frame.
	*/	
	public void Process(Data data) {
		ArrayList<int[]> dataset = data.getDataset();
		ArrayList<int[]> processed_dataset = new ArrayList<int[]>();

    	int framesize = 0;
    	// We begin from the end of the dataset 
		for (int i = dataset.size()-1; i >= this.frameDistance; i --) {
			if (dataset.get(i).length != framesize) 
				framesize = dataset.get(i).length;
			int[] differentials = new int[framesize];
			
			for (int index = 0; index < framesize; index ++)
				differentials[index] = dataset.get(i)[index] - dataset.get(i-this.frameDistance)[index]; 
			processed_dataset.add(0, differentials);
		}
		// The first frameDistance frames have no predecessor frame, hence we set its difference to zero.
		for (int i = this.frameDistance-1; i >= 0; i --) {
			if (dataset.get(i).length != framesize) 
				framesize = dataset.get(i).length;
			int[] zeros = new int[framesize];
			processed_dataset.add(0, zeros);
		}
		data.setDataset(processed_dataset);
	}
}