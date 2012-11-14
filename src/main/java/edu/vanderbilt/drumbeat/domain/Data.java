package edu.vanderbilt.drumbeat.domain;

import java.util.List;

/**
 * @author yicui
 *
 */

public interface Data {
	public void setDataset(List<Object> dataset);
	public List<Object> getDataset();
}