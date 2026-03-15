package com.app.playerservicejava.contract;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class CreatePlayerRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String country;

    @Min(1)
    private int ranking;

    public String getName() { return name; }
    public String getCountry() { return country; }
    public int getRanking() { return ranking; }

    public void setName(String name) { this.name = name; }
    public void setCountry(String country) { this.country = country; }
    public void setRanking(int ranking) { this.ranking = ranking; }
}