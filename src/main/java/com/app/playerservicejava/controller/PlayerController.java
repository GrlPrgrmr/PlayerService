package com.app.playerservicejava.controller;

import com.app.playerservicejava.contract.CreatePlayerRequest;
import com.app.playerservicejava.contract.PagedPlayerResponse;
import com.app.playerservicejava.contract.PlayerResponse;
import com.app.playerservicejava.mappers.PlayerMapper;
import com.app.playerservicejava.model.Player;
import com.app.playerservicejava.model.Players;
import com.app.playerservicejava.service.PlayerService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(value = "v1/players", produces = { MediaType.APPLICATION_JSON_VALUE })
public class PlayerController {
    @Resource
    private PlayerService playerService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Players> getPlayers() {
        Players players = playerService.getPlayers();
        return ok(players);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable("id") String id) {
        Optional<Player> player = playerService.getPlayerById(id);

        if (player.isPresent()) {
            return new ResponseEntity<>(player.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Players> findByLastName(@RequestParam String lastName) {
        return ResponseEntity.ok(playerService.getPlayersByLastName(lastName));
    }

    @GetMapping("/tallest")
    public ResponseEntity<Player> getTallestPlayer(){
        Player player = playerService.getTallestPlayer();
        return ResponseEntity.ok(player);
    }

    /**
     * This returns a Player object which is not ideal for production system
     * future schema changes break clients, security concerns
     * "Returning entities directly couples the API to the persistence model.
     *  Using DTOs lets us control the API contract and avoid leaking internal fields."
     *
     * @param request
     * @return
     */
    @PostMapping("/api/players")
    public ResponseEntity<PlayerResponse> createPlayer(
            @Valid @RequestBody CreatePlayerRequest request) {

        PlayerResponse player = playerService.createPlayer(request);
        URI location = URI.create("/players/" + player.getId());
        return ResponseEntity.created(location).body(player);
    }

    /***
     * Return players from a given country with pagination.
     * GET /api/players?country=USA&page=0&size=10
     * curl "http://localhost:8080/v1/players/searchByCountry/USA?page=0&size=5"
     */
    @GetMapping("/searchByCountry/{country}")
    public ResponseEntity<Page<PlayerResponse>> getPlayersByCountry(
            @PathVariable String country,
            @RequestParam(defaultValue = "false") boolean isAdmin,
            Pageable pageable
    ) {
        System.out.println("HIT CONTROLLER: country=" + country + " isAdmin=" + isAdmin);
        return ResponseEntity.ok(playerService.getPlayersByCountry(country,isAdmin,pageable));
    }

}
