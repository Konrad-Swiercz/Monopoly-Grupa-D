package com.zzaip.monopoly.game_logic.field;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "jail_field")
public class JailField extends Field {

    private int jailFieldId;

    // Additional fields and methods if necessary
}