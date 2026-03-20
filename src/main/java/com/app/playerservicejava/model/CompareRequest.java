package com.app.playerservicejava.model;

import java.util.List;

public class CompareRequest {

    private List<String> playerIds;

    public CompareRequest() {}

    public List<String> getPlayerIds(){ return playerIds;}
    public void setPlayerIds(List<String> playerIds) {this.playerIds = playerIds;}
}
