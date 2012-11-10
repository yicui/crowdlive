package edu.vanderbilt.drumbeat.algo;

import java.util.ArrayList;

import javax.validation.constraints.Min;

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
public class LocalMaximaFilter implements Filter {
	private static final long serialVersionUID = 1L;	
	
	@Value("17")
	@Min(1L)
	private int window;
	@Value("8")
	@Min(0L)	
	private int expectedMaximaPosition;
	
	/* This filter finds out whether a datapoint is the local maxima within the given window.    
	 * The variable expectedMaximaPosition controls the realtimeness of this filter. 
	 * If expectedMaximaPosition = 0, then the filter tries to know whether the maxima locates 
	 * at the end of the window, i.e. whether the latest frame has value greater than past (window-1) frames. 
	 * If expectedMaximaPosition = window/2, then the filter tries to know whether maxima locates 
	 * at the middle of the window.
	 * 
	 * Also while other filters run within each frame, this filter runs across frames, which is orthogonal.   
	*/

	public void Process(Data data) {
		ArrayList<int[]> dataset = data.getDataset();		
		ArrayList<int[]> processed_dataset = new ArrayList<int[]>();

    	// Since this filter runs across frames, we first fill in the processed_data with empty array 
		for (int i = 0; i < dataset.size(); i ++) {
			int[] result = new int[dataset.get(i).length];
			processed_dataset.add(result);		
		}
		
		@SuppressWarnings("unchecked")
		DoublyLinkedList<Integer>[] filterRange = new DoublyLinkedList[this.window];
    	for (int i = 0; i < this.window; i ++) 
    		filterRange[i] = new DoublyLinkedList<Integer>();

    	for (int index = 0; index < dataset.get(0).length; index ++) {
			// Fill the filterRange with bogus data and make each node points to itself
	    	for (int i = 0; i < this.window; i ++) {
	    		filterRange[i].value = -1;
	    		filterRange[i].next = filterRange[i].prev = filterRange[i];
	    	}
	    	DoublyLinkedList<Integer> maximaNode = null;

			for (int i = 0; i < dataset.size(); i ++) {
    			// remove the old node
    			if (filterRange[i%this.window].prev != null)
    				filterRange[i%this.window].prev.next = filterRange[i%this.window].next;
    			else maximaNode = filterRange[i%window].next;
    			if (filterRange[i%this.window].next != null)
    				filterRange[i%this.window].next.prev = filterRange[i%this.window].prev;

    			// insert the new node
    			filterRange[i%this.window].value = i;
    	    	DoublyLinkedList<Integer> p = maximaNode;
    			DoublyLinkedList<Integer> q = null;
    			while (p != null && dataset.get(p.value)[index] >= dataset.get(i)[index]) {
    				q = p;		p = p.next;
    			}
    			filterRange[i%this.window].next = p;				
    			filterRange[i%this.window].prev = q;
    			if (p != null) p.prev = filterRange[i%this.window];
    			if (q != null) q.next = filterRange[i%this.window];
    			else maximaNode = filterRange[i%this.window];
    			
    			if (i >= (this.window-1) && maximaNode.value == (i-this.expectedMaximaPosition))
    				processed_dataset.get(i-this.expectedMaximaPosition)[index] = 
    					dataset.get(i-this.expectedMaximaPosition)[index];
			}
		}
		data.setDataset(processed_dataset);    	
	}
}
