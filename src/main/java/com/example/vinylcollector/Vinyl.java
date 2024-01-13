package com.example.vinylcollector;

import javafx.scene.image.Image;

public class Vinyl {
    private String title;
    private String artist;
    private int year;
    private String genre;
    private String spotifyLink;
    private String imagePath;
    private Image albumCover;

    public Vinyl(String title, String artist, int year, String genre, String spotifyLink, String imagePath) {
        this.title = title;
        this.artist = artist;
        this.year = year;
        this.genre = genre;
        this.spotifyLink = spotifyLink;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public int getYear() {
        return year;
    }

    public Image getAlbumCover() {
        return albumCover;
    }

    public void setAlbumCover(Image albumCover) {
        this.albumCover = albumCover;
    }
}
