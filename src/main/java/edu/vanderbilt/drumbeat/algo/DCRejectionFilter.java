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
 * This filter removes DC component of the signal. 
 */
@RooJavaBean
@RooToString
@RooEquals
@RooSerializable
public class DCRejectionFilter implements Filter {
	private static final long serialVersionUID = 1L;	

	@Value("(short)((float)(1<<15)*kDefaultPoleDist)")
	private short filterGain;

	public void Process(Data data) {
		short signalGain = (short)((this.filterGain >> 1) + (1<<14));
		
		List<Object> dataset = data.getDataset();
		List<Object> processed_dataset = new ArrayList<Object>();	

    	int framesize = 0;
	    int original = 0, filtered = 0;
	    
		for (int i = 0; i < dataset.size(); i ++) {
			int[] frame = (int[])dataset.get(i);
			if (frame.length != framesize) 
				framesize = frame.length; 

			int[] dcFiltered = new int[framesize];

			for (int index = 0; index < framesize; index ++) {
				dcFiltered[index] = ((int)(((long)(frame[index] - original) * (long)signalGain) >> 16) + 
						(int)(((long)filtered * (long)this.filterGain) >> 16)) << 1;
		        original = frame[index];
				filtered = dcFiltered[index];
			}
			processed_dataset.add(dcFiltered);
		}
		data.setDataset(processed_dataset);		
	}
}