package com.bartproject.app.model;

public class FavoriteStation {

    // Dummy constructor for creating fake FavoriteStations (testing purposes)
    public FavoriteStation(String label, Station station) {
        this.label = label;
        this.station = station;
    }

    private Station station;
    private String label;

    public Station getStation() {
        return station;
    }

    public String getLabel() {
        return label;
    }
}
