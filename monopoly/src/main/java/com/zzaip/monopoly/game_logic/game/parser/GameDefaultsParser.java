package com.zzaip.monopoly.game_logic.game.parser;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

@Component
public class GameDefaultsParser {

    public int parseRoundLimit() {
        int roundLimit = 1000; // default value
        try {
            NodeList nodeList = getGameConfig();
            if (nodeList.getLength() > 0) {
                Element gameElement = (Element) nodeList.item(0);
                roundLimit = Integer.parseInt(gameElement.getElementsByTagName("roundLimit").item(0).getTextContent());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse round limit from config", e);
        }
        return roundLimit;
    }

    public int parsePlayerLimit() {
        int playerLimit = 4; // default value
        try {
            NodeList nodeList = getGameConfig();
            if (nodeList.getLength() > 0) {
                Element gameElement = (Element) nodeList.item(0);
                playerLimit = Integer.parseInt(gameElement.getElementsByTagName("playerLimit").item(0).getTextContent());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse player limit from config", e);
        }
        return playerLimit;
    }

    private NodeList getGameConfig() throws ParserConfigurationException, SAXException, IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("game/game.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(inputStream);
        document.getDocumentElement().normalize();

        return document.getElementsByTagName("Game");
    }
}
