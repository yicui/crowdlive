package edu.vanderbilt.drumbeat.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

/** 
 * @author yicui
 * 
 */

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Person {

    @NotNull
    @Column(unique = true)
    private String email;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Audio> audios = new HashSet<Audio>();

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Video> videos = new HashSet<Video>();

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Image> images = new HashSet<Image>();
}
