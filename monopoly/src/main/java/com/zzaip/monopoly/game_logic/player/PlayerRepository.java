package com.zzaip.monopoly.game_logic.player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PlayerRepository extends JpaRepository<Player, Integer> {

    Player findByPlayerName(String playerName);
}
