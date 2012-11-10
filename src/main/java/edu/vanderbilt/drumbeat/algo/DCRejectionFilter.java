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
public class DCRejectionFilter implements Filter {
	private static final long serialVersionUID = 1L;	

	@Value("(short)((float)(1<<15)*kDefaultPoleDist)")
	private short filterGain;

	/* This filter removes DC component of the signal. 
	*/
	public void Process(Data data) {
		short signalGain = (short)((this.filterGain >> 1) + (1<<14));
		
		ArrayList<int[]> dataset = data.getDataset();		
		ArrayList<int[]> processed_dataset = new ArrayList<int[]>();		

    	int framesize = 0;
	    int original = 0, filtered = 0;
	    
		for (int i = 0; i < dataset.size(); i ++) {
			if (dataset.get(i).length != framesize) 
				framesize = dataset.get(i).length; 

			int[] dcFiltered = new int[framesize];

			for (int index = 0; index < framesize; index ++) {
				dcFiltered[index] = ((int)(((long)(dataset.get(i)[index] - original) * (long)signalGain) >> 16) + 
						(int)(((long)filtered * (long)this.filterGain) >> 16)) << 1;
		        original = dataset.get(i)[index];
				filtered = dcFiltered[index];
			}
			processed_dataset.add(dcFiltered);
		}
		data.setDataset(processed_dataset);		
	}
}