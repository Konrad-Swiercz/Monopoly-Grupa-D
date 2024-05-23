package com.zzaip.monopoly.config;

import com.zzaip.monopoly.game_logic.field.*;
import com.zzaip.monopoly.game_logic.field.jail.JailFieldServiceImplImpl;
import com.zzaip.monopoly.game_logic.field.neutral.NeutralFieldServiceImplImpl;
import com.zzaip.monopoly.game_logic.field.property.PropertyFieldServiceImplImpl;
import com.zzaip.monopoly.game_logic.field.start.StartFieldServiceImplImpl;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FieldServiceConfig {

    @Autowired
    private FieldServiceRegistry registry;

    @Autowired
    private PropertyFieldServiceImplImpl propertyFieldService;

    @Autowired
    private JailFieldServiceImplImpl jailFieldService;

    @Autowired
    private StartFieldServiceImplImpl startFieldService;

    @Autowired
    private NeutralFieldServiceImplImpl neutralFieldService;

    @PostConstruct
    public void init() {
        registry.register(FieldType.PROPERTY, propertyFieldService);
        registry.register(FieldType.JAIL, jailFieldService);
        registry.register(FieldType.START, startFieldService);
        registry.register(FieldType.NEUTRAL, neutralFieldService);
    }
}
