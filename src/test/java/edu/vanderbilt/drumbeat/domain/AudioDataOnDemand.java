package edu.vanderbilt.drumbeat.domain;

import java.util.ArrayList;

import org.springframework.roo.addon.dod.RooDataOnDemand;

@RooDataOnDemand(entity = Audio.class)
public class AudioDataOnDemand {
	private ArrayList<int[]> mockAudioData(int datasetsize, int framesize) {
    	ArrayList<int[]> data = new ArrayList<int[]>();
		for (int i = 0; i < datasetsize; i ++) {
			int result[] = new int[framesize];
			for (int j = 0; j < framesize; j ++)
				result[j] = (int)(Math.random()*1000);
			data.add(result);
		}
		return data;
	}
    public void setData(Audio obj, int datasetsize, int framesize) {
		obj.setData(mockAudioData(datasetsize, framesize));
    }	
    public void setProcesseddata(Audio obj, int datasetsize, int framesize) {
		obj.setProcesseddata(mockAudioData(datasetsize, framesize));
    }	
}
