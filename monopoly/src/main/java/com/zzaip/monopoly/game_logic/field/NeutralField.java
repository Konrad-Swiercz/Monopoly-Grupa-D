package com.zzaip.monopoly.game_logic.field;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "neutral_field")
public class NeutralField extends Field {
    // Additional fields and methods if necessary
}