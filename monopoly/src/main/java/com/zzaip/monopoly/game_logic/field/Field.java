package com.zzaip.monopoly.game_logic.field;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
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

    public Field(int fieldNumber, String label, FieldType fieldType) {
        this.fieldId = 0;
        this.fieldNumber = fieldNumber;
        this.label = label;
        this.fieldType = fieldType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Field field = (Field) o;
        return Objects.equals(fieldId, field.fieldId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}
