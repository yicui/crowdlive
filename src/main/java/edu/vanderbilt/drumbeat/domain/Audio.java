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

/* @author Yi Cui */
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
    
	private transient DataManager dataManager;  
 
    private transient ArrayList<Float> beat;

    private transient ArrayList<ArrayList<Float>> candidatebeat;

    public DataManager getDatamanager() {
        return this.dataManager;
    }

    public void setDatamanager(DataManager dataManager) {
        this.dataManager = dataManager;
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