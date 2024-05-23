package com.zzaip.monopoly.game_logic.field.parser;

import com.zzaip.monopoly.game_logic.field.Field;
import com.zzaip.monopoly.game_logic.field.FieldType;
import com.zzaip.monopoly.game_logic.field.jail.JailField;
import com.zzaip.monopoly.game_logic.field.neutral.NeutralField;
import com.zzaip.monopoly.game_logic.field.property.PropertyField;
import com.zzaip.monopoly.game_logic.field.start.StartField;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FieldParserImpl implements FieldParser {

    @Override
    public List<Field> parseFieldsFromConfig() {
        List<Field> fields = new ArrayList<>();
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("game/board.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);
            document.getDocumentElement().normalize();

            NodeList nodeList = document.getElementsByTagName("Field");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    fields.add(parseFieldElement(element));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse fields from config", e);
        }
        return fields;
    }

    private Field parseFieldElement(Element element) {
        int fieldNumber = Integer.parseInt(element.getAttribute("fieldNumber"));
        String label = element.getAttribute("label");
        FieldType fieldType = FieldType.valueOf(element.getAttribute("fieldType"));

        switch (fieldType) {
            case START:
                int bonus = Integer.parseInt(getElementValue(element, "startFieldDetails", "bonus"));
                return new StartField(fieldNumber, label, fieldType, bonus);
            case PROPERTY:
                int color = Integer.parseInt(getElementValue(element, "propertyDetails", "color"));
                float price = Float.parseFloat(getElementValue(element, "propertyDetails", "price"));
                float baseRent = Float.parseFloat(getElementValue(element, "propertyDetails", "baseRent"));
                int houseLimit = Integer.parseInt(getElementValue(element, "propertyDetails", "houseLimit"));
                float housePrice = Float.parseFloat(getElementValue(element, "propertyDetails", "housePrice"));
                float rentMultiplier = Float.parseFloat(getElementValue(element, "propertyDetails", "rentMultiplier"));
                return new PropertyField(fieldNumber, label, fieldType, color, price, houseLimit, housePrice, rentMultiplier, baseRent);
            case JAIL:
                int jailFieldNumber = Integer.parseInt(getElementValue(element, "jailDetails", "jailFieldId"));
                int jailTurns = Integer.parseInt(getElementValue(element, "jailDetails", "jailTurns"));
                return new JailField(fieldNumber, label, fieldType, jailFieldNumber, jailTurns);
            case NEUTRAL:
                return new NeutralField(fieldNumber, label, fieldType);
            default:
                throw new RuntimeException("Unknown field type: " + fieldType);
        }
    }

    private String getElementValue(Element parentElement, String childElementName, String subChildElementName) {
        NodeList nodeList = parentElement.getElementsByTagName(childElementName);
        if (nodeList.getLength() > 0) {
            Element childElement = (Element) nodeList.item(0);
            NodeList subNodeList = childElement.getElementsByTagName(subChildElementName);
            if (subNodeList.getLength() > 0) {
                return subNodeList.item(0).getTextContent();
            }
        }
        throw new RuntimeException("Missing element: " + childElementName + " -> " + subChildElementName);
    }
}
