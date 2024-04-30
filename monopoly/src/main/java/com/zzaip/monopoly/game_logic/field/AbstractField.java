package com.zzaip.monopoly.game_logic.field;

import jakarta.persistence.*;

@Entity
@Table(name="AbstractField")
public abstract class AbstractField {

    @Id
    @GeneratedValue
    @Column(name="field_id")
    private int field_id;

    @Column(name="field_number")
    private int field_number;

    @Column(name="field_type")
    private String field_type;
}

