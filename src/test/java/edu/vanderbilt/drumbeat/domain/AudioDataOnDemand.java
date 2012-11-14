package edu.vanderbilt.drumbeat.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.roo.addon.dod.RooDataOnDemand;

/* @author Yi Cui */
@RooDataOnDemand(entity = Audio.class)
public class AudioDataOnDemand {
	public List<Object> mockRandomAudioData(int datasetsize, int framesize) {
    	List<Object> data = new ArrayList<Object>();
		for (int i = 0; i < datasetsize; i ++) {
			int result[] = new int[framesize];
			for (int j = 0; j < framesize; j ++)
				result[j] = (int)(Math.random()*1000);
			data.add(result);
		}
		return data;
	}
	
	public List<Object> mockAsymptoticAudioData(int datasetsize, int framesize) {
		List<Object> data = new ArrayList<Object>();
		for (int i = 0; i < datasetsize; i ++) {
			int result[] = new int[framesize];
			for (int j = 0; j < framesize; j ++)
				result[j] = i*framesize+j;
			data.add(result);
		}
		return data;
	}

	public void setDatamanager(Audio obj, List<Object> dataset) {
		DefaultDataManager dataManager = new DefaultDataManager();
		TransposableData data = new TransposableData();
		data.setDataset(dataset);
		dataManager.push(data);
		obj.setDatamanager(dataManager);
	}
}