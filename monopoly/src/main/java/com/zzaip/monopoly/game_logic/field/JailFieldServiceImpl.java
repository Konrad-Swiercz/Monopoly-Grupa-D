package com.zzaip.monopoly.game_logic.field;

import com.zzaip.monopoly.game_logic.game.Game;
import com.zzaip.monopoly.game_logic.player.Player;
import com.zzaip.monopoly.game_logic.player.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class JailFieldServiceImpl extends GeneralFieldService implements JailFieldService{
    private final PlayerService playerService;

    /**
     * If the current player stands on the "Go To Jail" field move the player to Jail field
     * and set rounds in Jail field of the current user.
     * @param field the field the player stood on
     * @param game the game object
     * @return updated game object
     */
    @Override
    public Game onStand(Field field, Game game) {
        JailField goToJailField;
        Player currentPlayer = game.getCurrentPlayer();
        try {
            // make sure the field is actually a Jail field by casting it
            goToJailField = (JailField) field;
        } catch (ClassCastException e) {
            throw new RuntimeException("Called JailFieldService for a Field of different" +
                    "type than Jail Field");
        }
        // make sure the player actually stood on the field
        if (currentPlayer.getPlayerPosition() == goToJailField.getFieldNumber()) {
            Field jailField = getFieldByFieldNumber(goToJailField.getJailFieldNumber());
            // move player to the actual jail field
            currentPlayer.setPlayerPosition(jailField.getFieldNumber());
            // modify the turns in jail count
            currentPlayer.setJailTurns(goToJailField.getJailTurns());
            // update the corresponding player in player list
            for (int i = 0; i < game.getPlayers().size(); i++) {
                if (game.getPlayers().get(i).getPlayerId() == currentPlayer.getPlayerId()) {
                    game.getPlayers().set(i, currentPlayer);
                    break;
                }
            }
            playerService.updatePlayer(currentPlayer);
        }
        return game;
    }

}
