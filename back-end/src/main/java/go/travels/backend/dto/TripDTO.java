package go.travels.backend.dto;

public class TripDTO {
    private String id;
    private String latMatch;
    private String lngMatch;
    private String latDestiny;
    private String lngDestiny;
    private String destiny;
    private String idUser;

    public TripDTO(){}

    public TripDTO(String id, String latMatch, String lngMatch, String latDestiny, String lngDestiny, String destiny, String idUser) {
        this.id = id;
        this.latMatch = latMatch;
        this.lngMatch = lngMatch;
        this.latDestiny = latDestiny;
        this.lngDestiny = lngDestiny;
        this.destiny = destiny;
        this.idUser = idUser;
    }

    public String getLatMatch() {
        return latMatch;
    }

    public void setLatMatch(String latMatch) {
        this.latMatch = latMatch;
    }

    public String getLngMatch() {
        return lngMatch;
    }

    public void setLngMatch(String lngMatch) {
        this.lngMatch = lngMatch;
    }

    public String getLatDestiny() {
        return latDestiny;
    }

    public void setLatDestiny(String latDestiny) {
        this.latDestiny = latDestiny;
    }

    public String getLngDestiny() {
        return lngDestiny;
    }

    public void setLngDestiny(String lngDestiny) {
        this.lngDestiny = lngDestiny;
    }

    public String getDestiny() {
        return destiny;
    }

    public void setDestiny(String destiny) {
        this.destiny = destiny;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
