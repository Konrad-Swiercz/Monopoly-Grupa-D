package com.zzaip.monopoly.game_logic.game.parser;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.InputStream;

@Component
public class GameDefaultsParser {

    public int parseRoundLimit() {
        int roundLimit = 1000; // default value
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("game/game.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);
            document.getDocumentElement().normalize();

            NodeList nodeList = document.getElementsByTagName("Game");
            if (nodeList.getLength() > 0) {
                Element gameElement = (Element) nodeList.item(0);
                roundLimit = Integer.parseInt(gameElement.getElementsByTagName("roundLimit").item(0).getTextContent());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse round limit from config", e);
        }
        return roundLimit;
    }
}