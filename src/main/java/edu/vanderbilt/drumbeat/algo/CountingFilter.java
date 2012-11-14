package edu.vanderbilt.drumbeat.algo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

import edu.vanderbilt.drumbeat.domain.Data;

/**
 * @author yicui
 * 
 * This filter counts within each frame datapoints whose values are between the given thresholds.     
 * The result is a single-item array containing the counter.   
 */	
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

	public void Process(Data data) {
		List<Object> dataset = data.getDataset();
		List<Object> processed_dataset = new ArrayList<Object>();

		for (int i = 0; i < dataset.size(); i ++) {
			int[] counter = new int[1];
			counter[0] = 0;
			int[] frame = (int[])dataset.get(i);
			for (int index = 0; index < frame.length; index ++)
				if (frame[index] >= this.minThreshold && frame[index] <= this.maxThreshold)
					counter[0] ++;
			processed_dataset.add(counter);
		}		
		data.setDataset(processed_dataset);
	}
}
