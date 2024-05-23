package com.zzaip.monopoly.game_logic.field.property;

import com.zzaip.monopoly.game_logic.field.Field;
import com.zzaip.monopoly.game_logic.field.FieldType;
import com.zzaip.monopoly.game_logic.player.Player;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
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

    public PropertyField(
            int fieldNumber,
            String label,
            FieldType fieldType,
            int color,
            float price,
            int houseLimit,
            float housePrice,
            float rentMultiplier,
            float baseRent) {
        super(fieldNumber, label, fieldType);
        this.owner = null;
        this.houseCount = 0;
        this.color = color;
        this.price = price;
        this.houseLimit = houseLimit;
        this.housePrice = housePrice;
        this.rentMultiplier = rentMultiplier;
        this.baseRent = baseRent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PropertyField that = (PropertyField) o;
        return Objects.equals(getFieldId(), that.getFieldId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
