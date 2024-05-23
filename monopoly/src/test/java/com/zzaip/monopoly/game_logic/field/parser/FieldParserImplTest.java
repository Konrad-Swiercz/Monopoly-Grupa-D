package com.zzaip.monopoly.game_logic.field;

import com.zzaip.monopoly.game_logic.field.parser.FieldParser;
import com.zzaip.monopoly.game_logic.field.parser.FieldParserImpl;
import com.zzaip.monopoly.game_logic.field.property.PropertyField;
import com.zzaip.monopoly.game_logic.field.start.StartField;
import com.zzaip.monopoly.game_logic.field.jail.JailField;
import com.zzaip.monopoly.game_logic.field.neutral.NeutralField;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class FieldParserImplTest {

    @Test
    void parseFieldsFromConfig() {
        FieldParser parser = new FieldParserImpl();
        List<Field> fields = parser.parseFieldsFromConfig();

        // Check the number of fields
        assertEquals(28, fields.size(), "Expected 28 fields");

        // Check the first field (Start Field)
        Field startField = fields.get(0);
        assertTrue(startField instanceof StartField, "Expected StartField instance");
        assertEquals(0, startField.getFieldNumber(), "Expected field number 0");
        assertEquals("Start", startField.getLabel(), "Expected label 'Start'");
        assertEquals(FieldType.START, startField.getFieldType(), "Expected field type START");
        assertEquals(200, ((StartField) startField).getBonus(), "Expected bonus 200");

        // Check a Property Field
        Field propertyField = fields.get(1);
        assertTrue(propertyField instanceof PropertyField, "Expected PropertyField instance");
        assertEquals(1, propertyField.getFieldNumber(), "Expected field number 1");
        assertEquals("A", propertyField.getLabel(), "Expected label 'A'");
        assertEquals(FieldType.PROPERTY, propertyField.getFieldType(), "Expected field type PROPERTY");
        assertEquals(1, ((PropertyField) propertyField).getColor(), "Expected color 1");
        assertEquals(60, ((PropertyField) propertyField).getPrice(), "Expected price 60");
        assertEquals(2, ((PropertyField) propertyField).getBaseRent(), "Expected base rent 2");
        assertEquals(3, ((PropertyField) propertyField).getHouseLimit(), "Expected house limit 3");
        assertEquals(50, ((PropertyField) propertyField).getHousePrice(), "Expected house price 50");
        assertEquals(1.5, ((PropertyField) propertyField).getRentMultiplier(), "Expected rent multiplier 1.5");

        // Check a Jail Field
        Field jailField = fields.get(7);
        assertTrue(jailField instanceof JailField, "Expected JailField instance");
        assertEquals(7, jailField.getFieldNumber(), "Expected field number 7");
        assertEquals("Go to jail", jailField.getLabel(), "Expected label 'Go to jail'");
        assertEquals(FieldType.JAIL, jailField.getFieldType(), "Expected field type JAIL");
        assertEquals(22, ((JailField) jailField).getJailFieldNumber(), "Expected jail field number 22");
        assertEquals(2, ((JailField) jailField).getJailTurns(), "Expected jail turns 2");

        // Check a Neutral Field
        Field neutralField = fields.get(14);
        assertTrue(neutralField instanceof NeutralField, "Expected NeutralField instance");
        assertEquals(14, neutralField.getFieldNumber(), "Expected field number 14");
        assertEquals("Free parking", neutralField.getLabel(), "Expected label 'Free parking'");
        assertEquals(FieldType.NEUTRAL, neutralField.getFieldType(), "Expected field type NEUTRAL");

        // Add more assertions for other fields if necessary
    }
}
