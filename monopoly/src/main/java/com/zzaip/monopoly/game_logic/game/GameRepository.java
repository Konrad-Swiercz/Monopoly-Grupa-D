package com.zzaip.monopoly.game_logic.game;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {
    List<Game> findGamesByStatus(GameStatus gameStatus);
}
