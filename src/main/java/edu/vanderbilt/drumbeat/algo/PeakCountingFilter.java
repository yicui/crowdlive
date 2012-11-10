package edu.vanderbilt.drumbeat.algo;

import java.util.ArrayList;

import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

import edu.vanderbilt.drumbeat.domain.Data;

/* @author Yi Cui */
@RooJavaBean
@RooToString
@RooSerializable
public class PeakCountingFilter implements Filter {
	private static final long serialVersionUID = 1L;	

	@Value("8")
    @Min(1L)
	private int maxPeakWidth;
	@Value("0")
    @Min(0L)
	private int minPeakWidth;
	@Value("10000")
    @Min(1L)
	private int minPeakVolume;
	@Value("1000000")
    @Min(1L)
	private int maxPeakVolume;

	/* This filter searches in the dataset for peaks fitting the width & volume thresholds defined above.
	 * Since the dataset can contain positive & negative values, it searches for both directions (summit & anti_summit).
	 * As a result, all data points are reset to zero except those carrying the summit values of the found peaks.   
	*/
	public void Process(Data data) {
		ArrayList<int[]> dataset = data.getDataset();		
		ArrayList<int[]> processed_dataset = new ArrayList<int[]>();		

    	// fill the initial search range
		int searchRange[] = new int[this.maxPeakWidth];
    	for (int i = 0; i < this.maxPeakWidth; i ++) 
    		searchRange[i] = 0;

    	int valley = 0;
    	int summit_value = -1;
    	int anti_valley = 0;
    	int anti_summit_value = 1;

    	int framesize = 0;
		for (int i = 0; i < dataset.size(); i ++) {			
			if (dataset.get(i).length != framesize) 
				framesize = dataset.get(i).length; 

			int[] peaks = new int[framesize];

			for (int index = 0; index < framesize; index ++) {
				peaks[index] = 0;
				searchRange[index%maxPeakWidth] = dataset.get(i)[index];
    			
    			// see if the old valley has expired 
    			if (index%maxPeakWidth == valley) 
    				valley = -1;
				if ((searchRange[(index-1+maxPeakWidth)%maxPeakWidth] < searchRange[(index-2+maxPeakWidth)%maxPeakWidth]) && 
   						(searchRange[(index-1+maxPeakWidth)%maxPeakWidth] < searchRange[index%maxPeakWidth])) {
					// if concluding valley, then a confirmed peak
					if (valley != -1 && (index-1-valley+maxPeakWidth)%maxPeakWidth >= minPeakWidth) {
						int temp = Math.max(searchRange[valley], searchRange[(index-1+maxPeakWidth)%maxPeakWidth]);
						if ((summit_value > 0) && (summit_value-temp >= minPeakVolume) && ((summit_value-temp <= maxPeakVolume)))
							peaks[index] = summit_value-temp;
					}
					// set the beginning of the valley
   					valley = (index-1+maxPeakWidth)%maxPeakWidth;
					summit_value = searchRange[index%maxPeakWidth];
   				}
				else if ((valley != -1) && (searchRange[index%maxPeakWidth] > summit_value)) {
					summit_value = searchRange[index%maxPeakWidth];
				}
				
    			// see if the old anti valley has expired
    			if (index%maxPeakWidth == anti_valley) 
    				anti_valley = -1;		
				if ((searchRange[(index-1+maxPeakWidth)%maxPeakWidth] > searchRange[(index-2+maxPeakWidth)%maxPeakWidth]) && 
   						(searchRange[(index-1+maxPeakWidth)%maxPeakWidth] > searchRange[index%maxPeakWidth])) {
					// if concluding anti valley, then a confirmed peak
					if ((anti_valley != -1) && ((index-1-anti_valley+maxPeakWidth)%maxPeakWidth >= minPeakWidth)) {
						int temp = Math.min(searchRange[anti_valley], searchRange[(index-1+maxPeakWidth)%maxPeakWidth]);
						if ((anti_summit_value < 0) && (temp-anti_summit_value >= minPeakVolume) && (temp-anti_summit_value <= maxPeakVolume))
							peaks[index] = anti_summit_value-temp;
					}
					// set the beginning of the anti valley
   					anti_valley = (index-1+maxPeakWidth)%maxPeakWidth;
					anti_summit_value = searchRange[index%maxPeakWidth];
   				}
				else if ((anti_valley != -1) && (searchRange[index%maxPeakWidth] < anti_summit_value)) {
					anti_summit_value = searchRange[index%maxPeakWidth];
				}
			}
			processed_dataset.add(peaks);
		}
		data.setDataset(processed_dataset);
	}
}