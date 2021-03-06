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
 * This filter searches through the dataset and counts occurrences where the data curve crosses a horizontal zeroline.   
 * Here, the zeroline can be set at not just zero, but any value.
 * As a result, all data points are reset to zero, where the ones trespassing the zeroline are set to one.   
 */
@RooJavaBean
@RooToString
@RooEquals
@RooSerializable
public class TrespassFilter implements Filter {
	private static final long serialVersionUID = 1L;	

	@Value("0")
	private int zeroLine;

	public void Process(Data data) {
		List<Object> dataset = data.getDataset();
		List<Object> processed_dataset = new ArrayList<Object>();	
		   	
    	int framesize = 0;
		for (int i = 0; i < dataset.size(); i ++) {
			int[] frame = (int[])dataset.get(i);												
			if (frame.length != framesize) 
				framesize = frame.length; 

			int[] trespassers = new int[framesize];

			for (int index = 0; index < framesize; index ++)
				if (frame[index] == this.zeroLine)
					trespassers[index] = 1;
				else
					trespassers[index] = 0;
			processed_dataset.add(trespassers);
		}
		data.setDataset(processed_dataset);		
	}
}
