package com.zzaip.monopoly.game_logic.field.jail;

import com.zzaip.monopoly.game_logic.field.Field;
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
@Table(name = "jail_field")
public class JailField extends Field {
    private int jailFieldNumber;
    private int jailTurns;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        JailField jailField = (JailField) o;
        return  Objects.equals(getFieldId(), jailField.getFieldId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}