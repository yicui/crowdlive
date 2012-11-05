package edu.vanderbilt.drumbeat.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Audio {

    @NotNull
    private String title;

    @NotNull
    @Column(unique = true)
    private String pathurl;
    // Time length in milliseconds
    @Min(1L)
    private int duration;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Video> videos = new HashSet<Video>();

    @ManyToOne
    private Person uploader;

    private transient ArrayList<int[]> data;

    private transient ArrayList<int[]> processeddata;
    
    private transient ArrayList<Float> beat;

    private transient ArrayList<ArrayList<Float>> candidatebeat;

    public ArrayList<int[]> getData() {
        return this.data;
    }

    public void setData(ArrayList<int[]> data) {
        if (data.size() == 0)
        	throw new RuntimeException("The audio dataset is empty");
    	int framesize = data.get(0).length;

    	for (int i = 1; i < data.size(); i ++)
    		if (data.get(i).length != framesize) 
    			throw new RuntimeException("The audio framesize is not consistent");

    	this.data = data;    	
    }

    public ArrayList<int[]> getProcesseddata() {
        return this.processeddata;
    }

    public void setProcesseddata(ArrayList<int[]> processeddata) {
        if (this.data.size() != processeddata.size()) 
        	throw new RuntimeException("The processed audio dataset has a different size from the original dataset");

        for (int i = 1; i < data.size(); i ++)
    		if (this.data.get(i).length != data.get(0).length)
    			throw new RuntimeException("The processed audio framesize is not consistent");

        this.processeddata = processeddata;
    }

    public ArrayList<java.lang.Float> getBeat() {
        return this.beat;
    }

    public void setBeat(ArrayList<java.lang.Float> beat) {
        this.beat = beat;
    }

    public ArrayList<java.util.ArrayList<java.lang.Float>> getCandidateBeat() {
        return this.candidatebeat;
    }

    public void setCandidateBeat(ArrayList<java.util.ArrayList<java.lang.Float>> candidatebeat) {
        this.candidatebeat = candidatebeat;
    }
}