package com.zzaip.monopoly.config;

import com.zzaip.monopoly.game_logic.field.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FieldServiceConfig {

    @Autowired
    private FieldServiceRegistry registry;

    @Autowired
    private PropertyFieldServiceImpl propertyFieldService;

    @Autowired
    private JailFieldServiceImpl jailFieldService;

    @Autowired
    private StartFieldServiceImpl startFieldService;

    @Autowired
    private NeutralFieldServiceImpl neutralFieldService;

    @PostConstruct
    public void init() {
        registry.register(FieldType.PROPERTY, propertyFieldService);
        registry.register(FieldType.JAIL, jailFieldService);
        registry.register(FieldType.START, startFieldService);
        registry.register(FieldType.NEUTRAL, neutralFieldService);
    }
}
