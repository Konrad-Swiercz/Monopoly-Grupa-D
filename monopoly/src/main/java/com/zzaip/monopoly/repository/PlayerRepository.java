package com.zzaip.monopoly.repository;

import com.zzaip.monopoly.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PlayerRepository extends JpaRepository<Player, Integer> {

    @Query("SELECT p FROM Player p WHERE p.player_name = ?1")
    Player findByPlayerName(String playerName);
}
