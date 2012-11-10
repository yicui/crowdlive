package edu.vanderbilt.drumbeat.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

/* @author Yi Cui */
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Video {

    @NotNull
    private String title;

    @NotNull
    @Column(unique = true)
    private String pathurl;

    @ManyToOne
    private Audio audio;

    @ManyToOne
    private Person uploader;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Image> images = new HashSet<Image>();
}
