package com.zzaip.monopoly.game_logic.field;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "start_field")
public class StartField extends Field {

    private float bonus;
}