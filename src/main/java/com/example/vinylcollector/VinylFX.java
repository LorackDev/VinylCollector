package com.example.vinylcollector;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VinylFX extends Vinyl {
    public VinylFX(String title, String artist, int year) {
        super(title, artist, year);
    }

    public StringProperty titleProperty() {
        return new SimpleStringProperty(getTitle());
    }

    public StringProperty artistProperty() {
        return new SimpleStringProperty(getArtist());
    }

    public SimpleIntegerProperty yearProperty() {
        return new SimpleIntegerProperty(getYear());
    }
}
