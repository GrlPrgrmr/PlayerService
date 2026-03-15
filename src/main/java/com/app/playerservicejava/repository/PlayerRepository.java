package com.app.playerservicejava.repository;
import com.app.playerservicejava.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface PlayerRepository extends JpaRepository<Player, String> {
    List<Player> findByLastName(String lastName);
    List<Player> findByBirthCountry(String birthCountry);
    List<Player> findByBirthCountryIn(List<String> countries);
    Optional<Player> findTopByOrderByHeightDesc();
}
