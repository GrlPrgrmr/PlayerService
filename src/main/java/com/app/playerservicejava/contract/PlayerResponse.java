package com.app.playerservicejava.contract;

public class PlayerResponse {

    private String id;
    private String firstName;
    private String lastName;
    private String country;


    public PlayerResponse(String id, String firstName,String lastName, String country) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;

    }

    public String getId() { return id; }
    public String getCountry() { return country; }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}