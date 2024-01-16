package com.example.vinylcollector;

import javafx.scene.image.Image;

public class Vinyl {
    private String id;
    private String title;
    private String artist;
    private String year;
    private String genre;
    private String spotifyLink;
    private String imagePath;
    private Image albumCover;

    public Vinyl(String id, String title, String artist, String year, String genre, String spotifyLink, String imagePath) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.year = year;
        this.genre = genre;
        this.spotifyLink = spotifyLink;
        this.imagePath = imagePath;
    }

    public Vinyl(String id, String title, String artist, String year, String genre, String spotifyLink) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.year = year;
        this.genre = genre;
        this.spotifyLink = spotifyLink;
    }

    public String getID() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getArtist() {
        return artist;
    }
    public String getYear() {
        return year;
    }
    public String getGenre() {
        return genre;
    }
    public String getSpotifyLink() {return spotifyLink;}
    public Image getAlbumCover() {
        return albumCover;
    }
    public void setAlbumCover(Image albumCover) {
        this.albumCover = albumCover;
    }
}
