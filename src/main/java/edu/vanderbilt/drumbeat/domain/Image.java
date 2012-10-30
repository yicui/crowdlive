package edu.vanderbilt.drumbeat.domain;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Image {

    @NotNull
    private String title;

    @NotNull
    @Column(unique = true)
    private String pathurl;

    @ManyToOne
    private Person uploader;
}
