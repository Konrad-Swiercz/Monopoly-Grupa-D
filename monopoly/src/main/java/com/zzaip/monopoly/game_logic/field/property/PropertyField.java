package com.zzaip.monopoly.game_logic.field.property;

import com.zzaip.monopoly.game_logic.field.Field;
import com.zzaip.monopoly.game_logic.player.Player;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "property_field")
public class PropertyField extends Field {

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Player owner;

    private int color;
    private float price;
    private int houseCount;
    private int houseLimit;
    private float housePrice;
    private float rentMultiplier;
    private float baseRent;
}
