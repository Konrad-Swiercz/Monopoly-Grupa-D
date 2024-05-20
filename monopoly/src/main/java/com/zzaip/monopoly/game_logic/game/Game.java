package com.zzaip.monopoly.game_logic.game;

import com.zzaip.monopoly.game_logic.player.Player;
import com.zzaip.monopoly.game_logic.field.Field;
import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class Game {
    private int gameId;
    private String status;
    private int roundCount;
    private List<Player> players;
    private List<String> playersQueue;
    private Player currentPlayer;
    private List<Field> board;
}