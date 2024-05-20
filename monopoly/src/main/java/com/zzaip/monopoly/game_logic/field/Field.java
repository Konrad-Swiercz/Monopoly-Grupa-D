package com.zzaip.monopoly.game_logic.field;

import com.zzaip.monopoly.game_logic.player.Player;
import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "field")
public abstract class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fieldId;

    private int fieldNumber;
    private String label;

    @Enumerated(EnumType.STRING)
    private FieldType fieldType;
}
