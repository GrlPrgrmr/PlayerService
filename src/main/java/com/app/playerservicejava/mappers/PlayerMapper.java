package com.app.playerservicejava.mappers;

import com.app.playerservicejava.contract.CreatePlayerRequest;
import com.app.playerservicejava.contract.PlayerResponse;
import com.app.playerservicejava.model.Player;

public class PlayerMapper {

    public static Player toModel(CreatePlayerRequest request) {
        Player player = new Player();
        player.setFirstName(request.getFirstName());
        player.setLastName(request.getLastName());
        player.setBirthCountry(request.getCountry());
        return player;
    }

    public static PlayerResponse toResponse(Player player) {
        return new PlayerResponse(
                player.getPlayerId(),
                player.getFirstName()+" "+player.getLastName(),
                player.getBirthCountry(),
                player.getHeight()
        );
    }

    public static PlayerResponse toResponse(Player player, boolean isAdmin){
        if(isAdmin){
            return new PlayerResponse(
                    player.getPlayerId(),
                    player.getFirstName()+" "+player.getLastName(),
                    player.getBirthCountry(),
                    player.getHeight()
            );
        }else{
            return new PlayerResponse(
                    player.getPlayerId(),
                    player.getFirstName(),
                    player.getBirthCountry(),
                    player.getHeight()
            );
        }
    }
}
