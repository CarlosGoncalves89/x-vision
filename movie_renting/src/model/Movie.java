/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Objects;

/**
 * 
 * 
 * 
 * @author user
 */
public class Movie {
    
   private Integer id;
    private String title; 
    private String description; 
    private String thumbnail;
    private Boolean available;
    
    public Movie(Integer id, String title, String description, String thumbnail){
        setId(id);
        setTitle(title); 
        setDescription(description);
        setThumbnail(thumbnail);
        this.available = true;
    }
    
    public void setId(Integer id){
        if(id > 0){
            this.id = id; 
        } else {
            this.id = -1; 
        }
    }
    
    public Integer getId(){
        return this.id;
    }
    
    public void setTitle(String title){
        title = title.trim();
        if(title.length() > 3){
            title = title.toUpperCase();
            this.title = title; 
        } else {
            this.title = "NONE";
        }
    }
    
    public String getTitle(){
        return this.title; 
    }
    
    
    public void setDescription(String description){
        description = description.trim();
        if(description.length() > 10 && description.length() < 500){
            this.description = description;
        }else{
            this.description = "No description available";
        }
    }
    
    public String getDescription(){
        return this.description;
    }
    
    public void setThumbnail(String thumbnailURI){
        thumbnailURI = thumbnailURI.trim();
        if(thumbnailURI.length() > 7 && thumbnailURI.startsWith("http")){
            this.thumbnail = thumbnailURI;
        }else{
            this.thumbnail = "No thumbnail uri available";
        }
    }
    
    public String getThumbnail(){
        return this.thumbnail;
    }
    
    public boolean isAvailable(){
        return this.available; 
    }
    
    public void setAvailable(Boolean available){
        this.available = available;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.id);
        hash = 47 * hash + Objects.hashCode(this.title);
        hash = 47 * hash + Objects.hashCode(this.description);
        hash = 47 * hash + Objects.hashCode(this.thumbnail);
        hash = 47 * hash + Objects.hashCode(this.available);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Movie other = (Movie) obj;
        return true;
    }
    

    @Override
    public String toString() {
        return "Movie{" + "id=" + id + ", title=" + title + ", description=" + 
                description + ", thumbnail=" + thumbnail + ", available=" + available + '}';
    }
}

