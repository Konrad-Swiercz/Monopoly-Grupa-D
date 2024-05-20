package com.zzaip.monopoly.game_logic.field;

import org.springframework.stereotype.Component;
import java.util.EnumMap;
import java.util.Map;

@Component
public class FieldServiceRegistry {

    private final Map<FieldType, FieldService> serviceMap = new EnumMap<>(FieldType.class);

    public void register(FieldType fieldType, FieldService fieldService) {
        serviceMap.put(fieldType, fieldService);
    }

    public FieldService getService(FieldType fieldType) {
        return serviceMap.get(fieldType);
    }
}
