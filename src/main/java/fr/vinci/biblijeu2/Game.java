package fr.vinci.biblijeu2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Game {
    private int id;
    private String name;

    private String category = "A faire";

    @JsonProperty("released")
    private String releaseDate;

    private double rating;

    @JsonProperty("background_image")
    private String backgroundImage;

    public Game() {}
    public Game(int id, String name, String releaseDate, double rating) {
        this.id = id;
        this.name = name;
        this.releaseDate = releaseDate;
        this.rating = rating;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getReleaseDate() { return releaseDate; }
    public double getRating() { return rating; }
    public String getBackgroundImage() { return backgroundImage; }

    @Override
    public String toString() {
        return name + " (" + (releaseDate != null ? releaseDate : "N/A") + ") - Note: " + rating + "/5";
    }
}