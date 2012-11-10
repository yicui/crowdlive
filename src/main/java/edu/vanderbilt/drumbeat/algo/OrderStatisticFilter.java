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
public class OrderStatisticFilter implements Filter {
	private static final long serialVersionUID = 1L;	
	
	@Value("8")
	@Min(1L)
	private int window;
	@Value("4")
	@Min(1L)	
	private int kthLargest;

	/* This is the implementation of order statistics filter, which replaces each datapoint with the kth-largest value in its surrounding window.   
	 * So, min, max, and median filters are all its special cases. 
	 * You might wonder why I want to implement such a commonly used functionality in-house. 
	 * This is mainly because the code might end up running in a smart phone, which must be real-time, the least computing-intensive,  
	 * and easily portable for both Android (Java) and iOS (Objctive C) platforms.  
	*/
	public void Process(Data data) {
		/* Normally we do not interfere with users who throw in silly parameters that don't make sense.
		 * We trust the user of this software to be smart people. They might do such things just for fun or curiosity.
		 * We throw the following exception because if uncaught, it will cause a null pointer exception which is hard to trace.
		 */
		if (this.kthLargest > this.window)
			throw new RuntimeException("k must not be bigger than the window size");
		
		ArrayList<int[]> dataset = data.getDataset();		
		ArrayList<int[]> processed_dataset = new ArrayList<int[]>();

		@SuppressWarnings("unchecked")
		DoublyLinkedList<Integer>[] filterRange = new DoublyLinkedList[this.window];
    	for (int index = 0; index < this.window; index ++) 
    		filterRange[index] = new DoublyLinkedList<Integer>();
    	DoublyLinkedList<Integer> kthLargestNode = null;
    	DoublyLinkedList<Integer> p, q;

    	// Fill the initial filter this.window with value 0
    	q = null;
		for (int index = 0; index < this.window; index ++) {
			filterRange[index].value = 0;
			filterRange[index].prev = q; filterRange[index].next = null;  
			if (q != null) 
				q.next = filterRange[index];
			q = filterRange[index];
		}
		kthLargestNode = filterRange[0];	
		for (int index = 1; index < this.kthLargest; index ++)
			kthLargestNode = kthLargestNode.next;
    	
    	int framesize = 0;
		for (int i = 0; i < dataset.size(); i ++) {
			if (dataset.get(i).length != framesize) 
				framesize = dataset.get(i).length;
			int[] filtered = new int[framesize];
			
			for (int index = 0; index < framesize; index ++) {
    			int direction = 0;
    			// remove the old node
    			if (filterRange[index%this.window].value < kthLargestNode.value)	
    				direction = 1;
    			else {
    				direction = -1;
    				if (filterRange[index%this.window].value == kthLargestNode.value)
    					kthLargestNode = kthLargestNode.next;
    			}
    			if (filterRange[index%this.window].prev != null)
    				filterRange[index%this.window].prev.next = filterRange[index%this.window].next;
    			if (filterRange[index%this.window].next != null)
    				filterRange[index%this.window].next.prev = filterRange[index%this.window].prev;

    			int counter1 = 0;    			p = kthLargestNode;
    			while (p != null) { p = p.next; counter1 ++; }
    			int counter2 = 0;    			p = kthLargestNode;
    			while (p != null) { p = p.prev; counter2 ++; }
    			if ((counter1+counter2) != this.window)
    				counter1 = 0;

    			// insert the new node
    			filterRange[index%this.window].value = dataset.get(i)[index];
    			if (filterRange[index%this.window].value < kthLargestNode.value) {
    				p = kthLargestNode; q = null;
    				while ((p != null) && (p.value > filterRange[index%this.window].value)) {
    					q = p; p = p.prev;
    				}
    				filterRange[index%this.window].next = q; filterRange[index%this.window].prev = p;
    				if (q != null) q.prev = filterRange[index%this.window];
    				if (p != null) p.next = filterRange[index%this.window];
    				
    				if (direction == -1)
    					kthLargestNode = kthLargestNode.prev; // move up
    			}
    			else {
    				p = kthLargestNode; q = null;
    				while ((p != null) && (p.value <= filterRange[index%this.window].value)) {
    					q = p; p = p.next;
    				}
    				filterRange[index%this.window].prev = q; filterRange[index%this.window].next = p;
    				if (q != null) q.next = filterRange[index%this.window];
    				if (p != null) p.prev = filterRange[index%this.window];
    				
    				if (direction == 1) kthLargestNode = kthLargestNode.next; // move down
    			}
    			counter1 = 0;    			p = kthLargestNode;
    			while (p != null) { p = p.next; counter1 ++; }
    			counter2 = 0;    			p = kthLargestNode;
    			while (p != null) { p = p.prev; counter2 ++; }
    			if ((counter1+counter2) != (this.window+1))
    				counter1 = 0;

    			/* The last window/2 datapoints of the last array will leave empty,
    			 * since there is not enough datapoints to put them into the center of the window 
    			 */
    			if (index < this.window/2) {
    				if (i > 0)
    					processed_dataset.get(i-1)[processed_dataset.get(i-1).length-this.window/2+index] = kthLargestNode.value;
    			}
    			else
    				filtered[index-this.window/2] = kthLargestNode.value;				
			}
			processed_dataset.add(filtered);
		}
	}
}