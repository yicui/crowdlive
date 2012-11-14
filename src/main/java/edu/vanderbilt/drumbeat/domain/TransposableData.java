package edu.vanderbilt.drumbeat.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

import edu.vanderbilt.drumbeat.algo.Filter;

/**
 * @author yicui
 *
 * The dataset of a TransposableData object has all its frames to have the same size,
 * i.e., a perfect matrix, therefore can be transposed.
 * The transpose operation can be useful for visual display and some filter processing. 
 * If the original dataset is a MxN matrix, then the transposed matrix will be NxM. 
 */

@RooJavaBean
@RooToString
@RooSerializable
public class TransposableData implements Data {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Since the dataset is for in-memory computing and of massive volume,
	 * it shouldn't be persisted, therefore declared transient   
	 */
	private transient List<Object> dataset = new ArrayList<Object>();
	/**
	 * The transposed matrix is made transient because   
	 * (1) there is no need to persist it
	 * (2) since it strictly depends on the dataset, it must not have a public setter method 
	 */
	private transient TransposableData transpose = null;

	@Autowired
	private Filter generatingFilter;
	
	/**
	 * @see edu.vanderbilt.drumbeat.domain.Data#setDataset(java.util.List)
	 */
	public void setDataset(List<Object> dataset) {
        if (dataset.size() == 0)
        	throw new RuntimeException("The dataset is empty");

        int[] frame = (int[])dataset.get(0);
    	int framesize = frame.length;
    	for (int i = 1; i < dataset.size(); i ++) {
    		frame = (int[])dataset.get(i);    		
    		if (frame.length != framesize) 
    			throw new RuntimeException("The dataset framesize is not consistent");
    	}

		this.dataset = dataset;
		/**
		 * We follow the lazy approach, i.e., do not create the transposed matrix,
		 * which costs computing & memory, unless explicitly asked to.
		 */		
		this.transpose = null;
	}
	
	/**
	 * @see edu.vanderbilt.drumbeat.domain.Data#List<Object> getDataset()
	 */
	public List<Object> getDataset() {
		return this.dataset;
	}
	
	/**
	 * get the transpose dataset 
	 */
	public TransposableData getTranspose() {
		if (this.transpose != null) 
			return this.transpose;
		
		this.transpose = new TransposableData();
		List<Object> transpose_dataset = new ArrayList<Object>();
		// If the dataset is empty, return an empty transposed dataset 
		if (this.dataset.isEmpty())
			return this.transpose;

		// Transpose operation
		int[] frame = (int[])this.dataset.get(0);
		for (int i = 0; i < frame.length; i ++) {
			int[] transpose_frame = new int[this.dataset.size()];
			transpose_dataset.add(transpose_frame);
		}
		for (int i = 0; i < frame.length; i ++)
			for (int j = 0; j < this.dataset.size(); j ++) {
				frame = (int[])this.dataset.get(j);
				int[] transpose_frame = (int[])transpose_dataset.get(i);
				transpose_frame[j] = frame[i];
			}
		this.transpose.setDataset(transpose_dataset);

		// Transpose operation is symmetric
		this.transpose.transpose = this;
		return this.transpose;
	}

	/**
	 * Since the dataset property is transient, equals method must be rewritten to avoid
	 * two TransposableData objects made equal even when they carry different datasets
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof TransposableData))
			return false;
		TransposableData reference = (TransposableData)obj;
		if (this.dataset.equals(reference.dataset))
			return false;
		return true;
	}
}