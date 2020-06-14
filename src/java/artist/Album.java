/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package artist;

/**
 *
 * @author tomaszkoltun
 */
public class Album {
    
    String artistId;
    String name;
    int year;
    String labelUrl;

    public Album(String artistId, String name, int year, String labelUrl) {
        this.artistId = artistId;
        this.name = name;
        this.year = year;
        this.labelUrl = labelUrl;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getLabelUrl() {
        return labelUrl;
    }

    public void setLabelUrl(String labelUrl) {
        this.labelUrl = labelUrl;
    }
    
    public Album(Album a){
        setArtistId(a.getArtistId());
        setLabelUrl(a.getLabelUrl());
        setName(a.getName());
        setYear(a.getYear());
    }
    
    public Album(){
        
    }
    
}
