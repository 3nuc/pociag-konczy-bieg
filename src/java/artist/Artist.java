/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package artist;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tomaszkoltun
 */
@XmlRootElement
public class Artist {
    
    String nazwa;
    String bio;
    String imageUrl;

    public Artist(Artist a ){
        this.setNazwa(a.getNazwa());
        this.setBio(a.getBio());
        this.setImageUrl(a.getImageUrl());
    }
    public Artist(){
        
    }
    public Artist(String nazwa, String url, String bio) {
        this.nazwa = nazwa;
        this.bio = bio;
        this.imageUrl = imageUrl;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Artist{" + "nazwa=" + nazwa + ", bio=" + bio + ", imageUrl=" + imageUrl + '}';
    }
    
    
    
}
