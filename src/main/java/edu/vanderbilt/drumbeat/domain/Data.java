package edu.vanderbilt.drumbeat.domain;

import java.util.ArrayList;

import edu.vanderbilt.drumbeat.algo.Filter;

public class Data {
	private ArrayList<int[]> dataset;
	private Filter generatingFilter;
	
	public void setDataset(ArrayList<int[]> dataset) {
        if (dataset.size() == 0)
        	throw new RuntimeException("The dataset is empty");
		
    	int framesize = dataset.get(0).length;
    	for (int i = 1; i < dataset.size(); i ++)
    		if (dataset.get(i).length != framesize) 
    			throw new RuntimeException("The dataset framesize is not consistent");

		this.dataset = dataset;
	}
	
	public ArrayList<int[]> getDataset() {
		return this.dataset;
	}

	public void setGeneratingFilter(Filter generatingfilter) {
		this.generatingFilter = generatingfilter;
	}
	
	public Filter setGeneratingFilter() {
		return this.generatingFilter;
	}
}