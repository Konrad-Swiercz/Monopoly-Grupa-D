package com.zzaip.monopoly.game_logic.field.neutral;

import com.zzaip.monopoly.game_logic.field.Field;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "neutral_field")
public class NeutralField extends Field {
}