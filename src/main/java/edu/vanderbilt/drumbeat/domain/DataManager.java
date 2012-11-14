package edu.vanderbilt.drumbeat.domain;

/**
 * @author yicui
 *
 */
public interface DataManager {
	public void push(Data data);
	public void pop();
	public Data peek();
	public void update(Data data);	
	public void clear();
	public int size();
}