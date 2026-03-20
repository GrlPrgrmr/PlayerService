package com.app.playerservicejava.contract;

import com.app.playerservicejava.model.Player;

public class PlayerResponse {

    private String id;
    private String fullName;
    private String country;
    private String height;


    public PlayerResponse(String id, String firstName, String country, String height) {
        this.id = id;
        this.fullName = firstName;
        this.country = country;
        this.height = height;
    }

    public String getId() { return id; }
    public String getCountry() { return country; }

    public String getFullName() { return fullName;}

    public String getHeight() { return height;}
}