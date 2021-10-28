package go.travels.backend.dto;


public class FilterDTO {
    private String localName;
    private String latitude;
    private String longitude;
    private String tripId;
    private String id;
    private String url;

    public FilterDTO(){}

    public FilterDTO(String localName, String latitude, String longitude, String tripId, String id, String url) {
        this.id = id;
        this.localName = localName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.tripId = tripId;
        this.url = url;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
