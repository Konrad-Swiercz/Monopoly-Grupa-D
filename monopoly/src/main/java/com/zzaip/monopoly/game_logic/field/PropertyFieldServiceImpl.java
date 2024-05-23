package com.zzaip.monopoly.game_logic.field;

import com.zzaip.monopoly.game_logic.game.Game;
import com.zzaip.monopoly.game_logic.player.Player;
import com.zzaip.monopoly.game_logic.player.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PropertyFieldServiceImpl extends GeneralFieldService implements PropertyFieldService {
    private final PlayerService playerService;

    /**
     * Pay the rent to the owner if the PropertyField is owned by a different player
     * @param field the field the current user stood on
     * @param game the actual game state
     * @return the updated game state
     */
    @Override
    public Game onStand(Field field, Game game) {
        PropertyField propertyField;
        Player currentPlayer = game.getCurrentPlayer();
        try {
            // make sure the field is actually a Property field by casting it
            propertyField = (PropertyField) field;
        } catch (ClassCastException e) {
            throw new RuntimeException("Called PropertyFieldService for a Field of different type than Property Field");
        }

        // make sure the player actually stood on the field
        if (currentPlayer.getPlayerPosition() == propertyField.getFieldNumber()) {
            Player owner = propertyField.getOwner();
            if (owner != null && !owner.equals(currentPlayer)) {
                float rent = calculateRent(propertyField);
                currentPlayer.setPlayerBalance(currentPlayer.getPlayerBalance() - rent);
                if (currentPlayer.getPlayerBalance() < 0) {
                    currentPlayer.setHasLost(true);
                    rent += currentPlayer.getPlayerBalance(); // rent is reduced by the negative balance
                }
                owner.setPlayerBalance(owner.getPlayerBalance() + rent);
                playerService.updatePlayer(owner);
                playerService.updatePlayer(currentPlayer);
            }
        }
        return game;
    }

    @Override
    public float calculateTotalWorth(PropertyField propertyField) {
        return propertyField.getPrice() +
                propertyField.getHouseCount() * propertyField.getHousePrice();
    }

    /**
     * change the property owner if it was not owned before and take money from player's wallet
     * @param propertyField the property to be bought
     * @param game the actual game state
     * @return the updated game state
     */
    @Override
    public Game buyProperty(PropertyField propertyField, Game game) {
        Player buyer = game.getCurrentPlayer();

        // check if the player position is the propertyField number
        if (buyer.getPlayerPosition() == propertyField.getFieldNumber()) {
            // check if the property is not owned by anyone else
            if (propertyField.getOwner() == null) {
                // check if the player can afford to buy the property
                if (buyer.getPlayerBalance() >= propertyField.getPrice()) {
                    buyer.setPlayerBalance(buyer.getPlayerBalance() - propertyField.getPrice());
                    propertyField.setOwner(buyer);
                    updateField(propertyField);
                    playerService.updatePlayer(buyer);
                }
            }
        }
        return game;
    }

    /**
     * increase the house count if possible and take money from player's wallet
     * @param propertyField the property where the house is to be built
     * @param game the actual game state
     * @return the updated game state
     */
    @Override
    public Game buyHouse(PropertyField propertyField, Game game) {
        Player buyer = game.getCurrentPlayer();

        // check if the player position is the propertyField number
        if (buyer.getPlayerPosition() == propertyField.getFieldNumber()) {
            // check if the player owns all properties of the propertyField color group
            boolean ownsAllProperties = true;
            for (Field field : game.getBoard()) {
                if (field instanceof PropertyField) {
                    PropertyField propField = (PropertyField) field;
                    if (propField.getColor() == propertyField.getColor() && !propField.getOwner().equals(buyer)) {
                        ownsAllProperties = false;
                        break;
                    }
                }
            }

            if (ownsAllProperties && buyer.getPlayerBalance() >= propertyField.getHousePrice()
                    && propertyField.getHouseCount() < propertyField.getHouseLimit()) {
                buyer.setPlayerBalance(buyer.getPlayerBalance() - propertyField.getHousePrice());
                propertyField.setHouseCount(propertyField.getHouseCount() + 1);
                updateField(propertyField);
                playerService.updatePlayer(buyer);
            }
        }
        return game;
    }

    private float calculateRent(PropertyField propertyField) {
        return propertyField.getBaseRent() *
                (1 + propertyField.getRentMultiplier() * propertyField.getHouseCount());
    }
}
