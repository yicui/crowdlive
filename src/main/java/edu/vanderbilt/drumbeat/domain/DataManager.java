package edu.vanderbilt.drumbeat.domain;

public interface DataManager {
	public void push(Data data);
	public void pop();
	public Data peek();
	public void clear();
	public int size();
}