package go.travels.backend.dto;

import go.travels.backend.document.Trip;

import java.util.Optional;

public class PostDTO {
    private String id;
    private String title;
    private String description;
    private Integer likes;
    private String date;
    private String userId;
    private Optional<Trip> trip;

    public PostDTO(){}

    public PostDTO(String id, String title, String description, Integer likes, Optional<Trip> trip, String date, String userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.likes = likes;
        this.trip = trip;
        this.date = date;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Optional<Trip> getTrip() { return trip;
    }

    public void setTrip(Optional<Trip> tripId) {
        this.trip = trip;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
