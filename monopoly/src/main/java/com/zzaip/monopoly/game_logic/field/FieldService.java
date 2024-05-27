package com.zzaip.monopoly.game_logic.field;

import com.zzaip.monopoly.game_logic.game.Game;

public interface FieldService extends BaseFieldService {
    /**
     * Update the Game object depending on the field type of the Field the player stood on.
     *
     * @param landingField field the player stood on
     * @param initialField
     * @param game         the actual game status
     * @return updated game status
     */
    Game onStand(Field landingField, Field initialField, Game game);

}
