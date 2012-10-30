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
    // Number of frames
    @Min(1L)
    private int frames;
    // number of samples per frame
    @Min(2L)
    private int framesize;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Video> videos = new HashSet<Video>();

    @ManyToOne
    private Person uploader;

    private transient ArrayList<int[]> data;

    private transient ArrayList<Float> beat;

    private transient ArrayList<ArrayList<Float>> candidatebeat;

    public ArrayList<int[]> getData() {
        return this.data;
    }

    public void setData(ArrayList<int[]> data) {
        this.data = data;
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
