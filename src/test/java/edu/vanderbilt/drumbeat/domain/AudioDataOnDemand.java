package edu.vanderbilt.drumbeat.domain;

import java.util.ArrayList;

import org.springframework.roo.addon.dod.RooDataOnDemand;

@RooDataOnDemand(entity = Audio.class)
public class AudioDataOnDemand {
	public ArrayList<int[]> mockRandomAudioData(int datasetsize, int framesize) {
    	ArrayList<int[]> data = new ArrayList<int[]>();
		for (int i = 0; i < datasetsize; i ++) {
			int result[] = new int[framesize];
			for (int j = 0; j < framesize; j ++)
				result[j] = (int)(Math.random()*1000);
			data.add(result);
		}
		return data;
	}
	
	public ArrayList<int[]> mockAsymptoticAudioData(int datasetsize, int framesize) {
    	ArrayList<int[]> data = new ArrayList<int[]>();
		for (int i = 0; i < datasetsize; i ++) {
			int result[] = new int[framesize];
			for (int j = 0; j < framesize; j ++)
				result[j] = i*framesize+j;
			data.add(result);
		}
		return data;
	}

	public void setDatamanager(Audio obj, ArrayList<int[]> dataset) {
		DataManagerDefaultImpl dataManager = new DataManagerDefaultImpl();
		Data data = new Data();
		data.setDataset(dataset);
		dataManager.push(data);
		obj.setDatamanager(dataManager);
	}
}