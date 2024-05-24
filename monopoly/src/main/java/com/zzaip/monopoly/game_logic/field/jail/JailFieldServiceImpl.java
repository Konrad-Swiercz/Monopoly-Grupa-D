package com.zzaip.monopoly.game_logic.field.jail;

import com.zzaip.monopoly.game_logic.field.BaseFieldServiceImpl;
import com.zzaip.monopoly.game_logic.field.Field;
import com.zzaip.monopoly.game_logic.field.FieldRepository;
import com.zzaip.monopoly.game_logic.game.Game;
import com.zzaip.monopoly.game_logic.player.Player;
import com.zzaip.monopoly.game_logic.player.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class JailFieldServiceImpl extends BaseFieldServiceImpl implements JailFieldService{
    private final PlayerService playerService;

    @Autowired
    public JailFieldServiceImpl(FieldRepository fieldRepository, PlayerService playerService) {
        super(fieldRepository);
        this.playerService = playerService;
    }


    /**
     * If the current player stands on the "Go To Jail" field move the player to Jail field
     * and set rounds in Jail field of the current user.
     *
     * @param landingField the field the player stood on
     * @param initialField
     * @param game         the game object
     * @return updated game object
     */
    @Override
    public Game onStand(Field landingField, Field initialField, Game game) {
        JailField goToJailField;
        Player currentPlayer = game.getCurrentPlayer();
        try {
            // make sure the field is actually a Jail field by casting it
            goToJailField = (JailField) landingField;
        } catch (ClassCastException e) {
            throw new RuntimeException("Called JailFieldService for a Field of different" +
                    "type than Jail Field");
        }
        // make sure the player actually stood on the field
        if (currentPlayer.getPlayerPosition() == goToJailField.getFieldNumber()) {
            handlePassThroughStartField(game, initialField, landingField);
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
