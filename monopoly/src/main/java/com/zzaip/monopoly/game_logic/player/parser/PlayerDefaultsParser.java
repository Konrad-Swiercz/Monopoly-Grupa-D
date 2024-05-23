package com.zzaip.monopoly.game_logic.player.parser;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.InputStream;

@Component
public class PlayerDefaultsParser {

    public int parsePlayerPosition() {
        return Integer.parseInt(getElementValue("playerPosition"));
    }

    public float parsePlayerBalance() {
        return Float.parseFloat(getElementValue("playerBalance"));
    }

    private String getElementValue(String tagName) {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("game/player.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);
            document.getDocumentElement().normalize();

            NodeList nodeList = document.getElementsByTagName(tagName);
            if (nodeList.getLength() > 0) {
                return nodeList.item(0).getTextContent();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse " + tagName + " from config", e);
        }
        throw new RuntimeException("Missing element: " + tagName);
    }
}
