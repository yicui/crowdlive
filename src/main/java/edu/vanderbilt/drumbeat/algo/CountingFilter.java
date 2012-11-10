package edu.vanderbilt.drumbeat.algo;

import java.util.ArrayList;

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
public class CountingFilter implements Filter {
	private static final long serialVersionUID = 1L;	
	
	@Value("1")
	private int minThreshold;
	@Value("1<<31-1")
	private int maxThreshold;

	/* This filter counts within each frame datapoints whose values are between the given thresholds.     
	 * The result is a single-item array containing the counter.   
	*/	
	public void Process(Data data) {
		ArrayList<int[]> dataset = data.getDataset();
		ArrayList<int[]> processed_dataset = new ArrayList<int[]>();

		for (int i = 0; i < dataset.size(); i ++) {
			int[] counter = new int[1];
			counter[0] = 0;
			for (int index = 0; index < dataset.get(i).length; index ++)
				if (dataset.get(i)[index] >= this.minThreshold && dataset.get(i)[index] <= this.maxThreshold)
					counter[0] ++;
			processed_dataset.add(counter);
		}		
		data.setDataset(processed_dataset);
	}
}
