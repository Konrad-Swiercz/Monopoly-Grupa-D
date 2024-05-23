package com.zzaip.monopoly.game_logic.field.start;

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
@RequiredArgsConstructor
@Entity
@Table(name = "start_field")
public class StartField extends Field {

    private float bonus;

    public StartField(int fieldNumber, String label, FieldType fieldType, float bonus) {
        super(fieldNumber, label, fieldType);
        this.bonus = bonus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        StartField that = (StartField) o;
        return Objects.equals(getFieldId(), that.getFieldId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}