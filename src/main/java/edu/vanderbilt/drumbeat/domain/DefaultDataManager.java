package edu.vanderbilt.drumbeat.domain;

import java.util.List;
import java.util.Stack;

import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * @author yicui
 *
 */

@RooJavaBean
@RooToString
@RooEquals
@RooSerializable
public class DefaultDataManager implements DataManager {
	private static final long serialVersionUID = 1L;
	private Stack<Data> datasets = new Stack<Data>();
	
	public void push(Data data) {
    	if (!this.datasets.empty()) {
    		List<Object> predecessor_dataset = this.datasets.peek().getDataset();
    		if (predecessor_dataset.equals(data.getDataset()))
    			throw new RuntimeException("The dataset and its predecessor dataset point to the same place in memory");
            if (data.getDataset().size() != predecessor_dataset.size()) 
            	throw new RuntimeException("The dataset has a different size from its predecessor dataset");
    	}

    	this.datasets.push(data);
	}
	public void update(Data data) {
		if (this.datasets.empty())
			throw new RuntimeException("There is no dataset to update with");
		
		List<Object> predecessor_dataset = this.datasets.peek().getDataset();

   		if (data.getDataset().size() != predecessor_dataset.size()) 
   			throw new RuntimeException("The dataset has a different size from its predecessor dataset");

    	this.datasets.pop();
    	this.datasets.push(data);
	}
	public void pop() {
		this.datasets.pop();
	}
	public Data peek() {
		return this.datasets.peek();
	}
	public void clear() {
		this.datasets.clear();
	}
	public int size() {
		return this.datasets.size();
	}
}