package com.app.playerservicejava;

import com.app.playerservicejava.contract.PlayerResponse;
import com.app.playerservicejava.model.Player;
import com.app.playerservicejava.repository.PlayerRepository;
import com.app.playerservicejava.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerService playerService;

    private Player buildPlayer(String firstName, String lastName, String country) {
        Player p = new Player();
        p.setPlayerId("player1");
        p.setFirstName(firstName);
        p.setLastName(lastName);
        p.setBirthCountry(country);
        return p;
    }

    @Test
    void getPlayerById_returns_expected_player(){
        Player player = buildPlayer("Aaron", "Judge", "USA");
        when(playerRepository.findById("player1")).thenReturn(Optional.of(player));

        Optional<Player> response = playerService.getPlayerById("player1");

        assertTrue(response.isPresent());

        assertEquals("Aaron", response.get().getFirstName());
        assertEquals("Judge", response.get().getLastName());
    }

    @Test
    void getPlayerById_notFound_shouldReturnEmpty(){

        when(playerRepository.findById("player1"))
                .thenReturn(Optional.empty());

        Optional<Player> response =
                playerService.getPlayerById("player1");

        assertTrue(response.isEmpty());
    }

    @Test
    void getPlayersByCountry_admin_shouldReturnFullNames() {
        Player player = buildPlayer("Aaron", "Judge", "USA");

        Page<Player> page = new PageImpl<>(List.of(player));
        Pageable pageable = PageRequest.of(0, 5);

        when(playerRepository.findByBirthCountry("USA",pageable)).thenReturn(page);

        Page<PlayerResponse> response = playerService.getPlayersByCountry("USA",true,pageable);

        assertEquals(1,response.getTotalElements());

        PlayerResponse playerResponse = response.getContent().get(0);

        assertEquals("Aaron Judge", playerResponse.getFullName());
    }

    @Test
    void getPlayersByCountry_nonAdmin_shouldHideLastName() {
        Player player = buildPlayer("Aaron", "Judge", "USA");

        Page<Player> page = new PageImpl<>(List.of(player));
        Pageable pageable = PageRequest.of(0, 5);

        when(playerRepository.findByBirthCountry("USA",pageable)).thenReturn(page);

        Page<PlayerResponse> response = playerService.getPlayersByCountry("USA",false,pageable);

        PlayerResponse playerResponse = response.getContent().get(0);
        assertEquals("Aaron", playerResponse.getFullName());

    }

    }
