package com.zzaip.monopoly.game_logic.field.neutral;

import com.zzaip.monopoly.game_logic.field.Field;
import com.zzaip.monopoly.game_logic.field.FieldType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "neutral_field")
public class NeutralField extends Field {
    public NeutralField(int fieldNumber, String label, FieldType fieldType) {
        super(fieldNumber, label, fieldType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        NeutralField that = (NeutralField) o;
        return Objects.equals(getFieldId(), that.getFieldId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}