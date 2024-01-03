package com.example.weatherly;

public class SavedLocationsModel {//class used to assist the locations page saved locations

    //declare variables; each savedCity object contains a name and a location icon
    String savedCity;
    int image;

    //constructor
    public SavedLocationsModel(String savedCity, int locationImage) {
        this.savedCity = savedCity;
        this.image = image;
    }


    //getters
    public String getSavedCity() {
        return savedCity;
    }

    public int getImage() {
        return image;
    }
}



