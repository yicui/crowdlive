// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package edu.vanderbilt.drumbeat.domain;

import edu.vanderbilt.drumbeat.domain.Audio;
import edu.vanderbilt.drumbeat.domain.Image;
import edu.vanderbilt.drumbeat.domain.Person;
import edu.vanderbilt.drumbeat.domain.Video;
import java.util.Set;

privileged aspect Person_Roo_JavaBean {
    
    public String Person.getEmail() {
        return this.email;
    }
    
    public void Person.setEmail(String email) {
        this.email = email;
    }
    
    public Set<Audio> Person.getAudios() {
        return this.audios;
    }
    
    public void Person.setAudios(Set<Audio> audios) {
        this.audios = audios;
    }
    
    public Set<Video> Person.getVideos() {
        return this.videos;
    }
    
    public void Person.setVideos(Set<Video> videos) {
        this.videos = videos;
    }
    
    public Set<Image> Person.getImages() {
        return this.images;
    }
    
    public void Person.setImages(Set<Image> images) {
        this.images = images;
    }
    
}
