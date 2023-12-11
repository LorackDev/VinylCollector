package com.example.vinylcollector;

import javafx.scene.image.Image;

public class Vinyl {
    private String title;
    private String artist;
    private int year;
    private Image albumCover;

    private int test;

    public Vinyl(String title, String artist, int year) {
        this.title = title;
        this.artist = artist;
        this.year = year;
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
