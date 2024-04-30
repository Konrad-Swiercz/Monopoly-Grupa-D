package com.zzaip.monopoly.game_logic.field;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="StartField")
public class StartField extends AbstractField{

    private float bonus;
}
