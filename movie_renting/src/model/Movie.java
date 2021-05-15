package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Movie is the dvd disc that will be rented for a customer. 
 * @carlos 
 */
public class Movie implements Model <Movie> {
    
    private Integer id;
    private String title; 
    private String description; 
    private String thumbnail;
    private Integer available;
    
    /***
     * Default Movie's constructor initializes the movie as available.
     */
    public Movie(){
        this.available = 1;
    }
    
    /**
     * Movie's constructor initializes the movie with an id, title, description
     * and thumbnail. 
     * @param id - disc id
     * @param title - movie title
     * @param description - movie short description
     * @param thumbnail - uri of the image
     */
    public Movie(Integer id, String title, String description, String thumbnail){
        setId(id);
        setTitle(title); 
        setDescription(description);
        setThumbnail(thumbnail);
        this.available = 1;
    }
    
    /**
     * Sets the id if its greater than zero. 
     * @param id movie disc code
     */
    public void setId(Integer id){
        if(id > 0){
            this.id = id; 
        } else {
            this.id = -1; 
        }
    }
    
    /**
     * Returns the movie id. 
     * @return movie id
     */
    public Integer getId(){
        return this.id;
    }
    
    /**
     * Sets Movie's title if the movie length is greater than 3 caracteres.
     * @param title movie title
     */
    public void setTitle(String title){
        title = title.trim();
        if(title.length() > 3){
            title = title.toUpperCase();
            this.title = title; 
        } else {
            this.title = "NONE";
        }
    }
    
    /**
     * Returns the movie's title.
     * @return Movie's title.
     */
    public String getTitle(){
        return this.title; 
    }
    
    
    /**
     * Sets the description if its length is greater than 10 and less than 255.
     * @param description short movie description
     */
    public void setDescription(String description){
        description = description.trim();
        if(description.length() > 10 && description.length() <= 255){
            this.description = description;
        }else{
            this.description = "No description available";
        }
    }
    
    /**
     * Returns the short movie's description
     * @return Movie's description
     */
    public String getDescription(){
        return this.description;
    }
    
    /**
     * Sets the thumbnail uri if its length is greater 7 characteres and contains http. 
     * @param thumbnailURI uri
     */
    public void setThumbnail(String thumbnailURI){
        thumbnailURI = thumbnailURI.trim();
        if(thumbnailURI.length() > 7 && thumbnailURI.startsWith("http")){
            this.thumbnail = thumbnailURI;
        }else{
            this.thumbnail = "No thumbnail uri available";
        }
    }
    
    /**
     * Returns thumbnail uri
     * @return uri
     */
    public String getThumbnail(){
        return this.thumbnail;
    }
    
    /**
     * Returns if the movie is available.
     * @return  true if available
     * false otherwise
     */
    public boolean isAvailable(){
        return this.available == 1; 
    }
    
    /**
     * Sets if the movie is available
     * @param available 
     */
    public void setAvailable(boolean available){
        if(available)
            this.available = 1;
        else
            this.available = 0;
    }

    /**
     * Saves Movie disc informations. 
     */
    @Override
    public void save() {
        
        String insert = String.format("insert into movie (title, description, "
                + "thumbnail, available) values ('%s', '%s', '%s', '%d')", 
                this.title, this.description, this.thumbnail, this.available);
        try {
            
            DbConnection dbConnection = DbConnection.getDbConnection();
            dbConnection.execute(insert);
        } catch (SQLException ex) {
            Logger.getLogger(Movie.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Updates Movie disc informations.
     */
    @Override
    public void update() {
        String updateSql = String.format("update movie set title = '%s', description = '%s', "
                + "thumbnail = '%s', available = '%d' where movie_id = '%d'", 
                this.title, this.description, this.thumbnail, this.available, this.id);
        try {
            DbConnection dbConnection = DbConnection.getDbConnection();
            dbConnection.execute(updateSql);
        } catch (SQLException ex) {
            Logger.getLogger(Movie.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /***
     * Returns a Movie object if the condition property = value is true. 
     * @param property - any customer field
     * @param value - a correspondent customer field value
     * @return a Movie object with its rentals and associated payments. 
     */
    @Override
    public Movie get(String property, String value) {
        
        String query = String.format("select movie_id, title, description, thumbnail,"
                + " available from movie where %s = '%s'", property, value);
        Movie movie = null; 
        
        try {
            
            DbConnection dbConnection = DbConnection.getDbConnection();
            ResultSet rs = dbConnection.query(query);
           
            while (rs!=  null && rs.next()) {
                String mId = String.valueOf( rs.getInt("movie_id"));
                String mTitle = rs.getString("title");
                String mDescription = rs.getString("description");
                String mThumbnail = rs.getString("thumbnail");
                Integer mAvailable = rs.getInt("available");
                movie = new Movie(Integer.valueOf(mId), mTitle, mDescription, mThumbnail); 
                movie.setAvailable(mAvailable == 1);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Movie.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return movie;
    }

    /**
      *Returns a list of movies if the condition property = value is true. 
     * @param property - any customer field
     * @param value - a correspondent customer field value
     * @return a Customer object with its rentals and associated payments. 
     */
    @Override
    public List<Movie> list(String property, String value) {
        
        String query = String.format("select movie_id, title, description, thumbnail,"
                + " available from movie where %s = '%s'", property, value);
        List<Movie> movies = new ArrayList<>(); 
        
        try {
            
            DbConnection dbConnection = DbConnection.getDbConnection();
            ResultSet rs = dbConnection.query(query);
           
            while (rs!=  null && rs.next()) {
                String mId = String.valueOf( rs.getInt("movie_id"));
                String mTitle = rs.getString("title");
                String mDescription = rs.getString("description");
                String mThumbnail = rs.getString("thumbnail");
                Integer mAvailable = rs.getInt("available");
                Movie movie = new Movie(Integer.valueOf(mId), mTitle, mDescription, mThumbnail); 
                movie.setAvailable(mAvailable == 1);
                movies.add(movie);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Movie.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return movies;
    }

    /**
         * Returns hashcode using the movie attributes.
     * @return an integer hash code. 
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
        hash = 53 * hash + Objects.hashCode(this.title);
        hash = 53 * hash + Objects.hashCode(this.description);
        hash = 53 * hash + Objects.hashCode(this.thumbnail);
        hash = 53 * hash + Objects.hashCode(this.available);
        return hash;
    }

    /**
     *  Returns if this object is equals to obj.
     * @param obj an object
     * @return if this object has the same state of obj 
     */
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

    /***
     * 
     * @return 
     */
    @Override
    public String toString() {
        return "Movie{" + "id=" + id + ", title=" + title + ", description=" + description + 
                ", thumbnail=" + thumbnail + ", available=" + available + '}';
    }
    
    
    
}
