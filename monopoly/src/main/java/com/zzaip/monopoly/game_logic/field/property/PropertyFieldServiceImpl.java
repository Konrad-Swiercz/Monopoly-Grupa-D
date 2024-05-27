package com.zzaip.monopoly.game_logic.field.property;

import com.zzaip.monopoly.game_logic.exceptions.GameLogicException;
import com.zzaip.monopoly.game_logic.exceptions.OutOfSynchError;
import com.zzaip.monopoly.game_logic.field.BaseFieldServiceImpl;
import com.zzaip.monopoly.game_logic.field.Field;
import com.zzaip.monopoly.game_logic.field.FieldRepository;
import com.zzaip.monopoly.game_logic.field.FieldType;
import com.zzaip.monopoly.game_logic.game.Game;
import com.zzaip.monopoly.game_logic.player.Player;
import com.zzaip.monopoly.game_logic.player.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PropertyFieldServiceImpl extends BaseFieldServiceImpl implements PropertyFieldService {
    private final PlayerService playerService;

    @Autowired
    public PropertyFieldServiceImpl(FieldRepository fieldRepository, PlayerService playerService) {
        super(fieldRepository);
        this.playerService = playerService;
    }

    /**
     * Pay the rent to the owner if the PropertyField is owned by a different player
     *
     * @param landingField the field the current user stood on
     * @param initialField the field the player had stood on before making the move
     * @param game         the actual game state
     * @return the updated game state
     */
    @Override
    public Game onStand(Field landingField, Field initialField, Game game) {
        PropertyField propertyField;
        Player currentPlayer = game.getCurrentPlayer();
        try {
            // make sure the field is actually a Property field by casting it
            propertyField = (PropertyField) landingField;
        } catch (ClassCastException e) {
            throw new OutOfSynchError("Called PropertyFieldService for a Field of different type than Property Field");
        }

        // make sure the player actually stood on the field
        if (currentPlayer.getPlayerPosition() == propertyField.getFieldNumber()) {
            handlePassThroughStartField(game, initialField, landingField);
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
     * get all properties owned by the specified player
     * @param player the player whose properties are to be fetched
     * @return list of properties owned by the player
     */
    @Override
    public List<PropertyField> getPlayerProperties(Player player) {
        return getFieldsByFieldType(FieldType.PROPERTY).stream()
                .filter(field -> field instanceof PropertyField)
                .map(field -> (PropertyField) field)
                .filter(propertyField -> propertyField.getOwner().equals(player))
                .collect(Collectors.toList());
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
                } else {
                    throw new GameLogicException("Cannot buy the property. Insufficient funds.");
                }
            } else {
                throw new GameLogicException("Cannot buy the property. Property already has owner.");
            }
        } else {
            throw new OutOfSynchError("Player's position and the field number do not much");
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
        boolean ownsAllProperties = true;
        for (Field field : game.getBoard()) {
            if (field instanceof PropertyField propField) {
                boolean isMatchingColor = propField.getColor() == propertyField.getColor();
                if (isMatchingColor && !isOwnedByPlayer(propertyField, buyer)) {
                    // found a property of the same color that is not owned by the buyer
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
        } else {
            throw new GameLogicException("""
                    Cannot buy the house. The reason is either:
                    - you do not own all properties of the property color
                    - you have insufficient funds
                    - the house limit has been reached.
                    """);
        }
        return game;
    }

    private boolean isOwnedByPlayer(PropertyField propertyField, Player player) {
        if (propertyField.getOwner() == null) {
            return false;
        }
        return Objects.equals(propertyField.getOwner().getPlayerName(), player.getPlayerName());
    }


    private float calculateRent(PropertyField propertyField) {
        return propertyField.getBaseRent() *
                (1 + propertyField.getRentMultiplier() * propertyField.getHouseCount());
    }
}
