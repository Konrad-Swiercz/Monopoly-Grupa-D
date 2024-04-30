package com.zzaip.monopoly;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;

@Controller
public class GameLogicController {


    @GetMapping("/start")
    public String start(){
        return "start";
    }

    @GetMapping("/gameRules")
    public String gameRules(){
        return "game_rules";
    }

    @GetMapping("/hostRoom")
    public String hostRoom(Model theModel){

        PlayerList thePlayerList = new PlayerList(Arrays.asList("Alice", "Bob", "Charlie"));

        theModel.addAttribute("playerList",thePlayerList);

        return "host_room";
    }

    @GetMapping("/gameRoom")
    public String gameRoom(Model theModel){

        return "monopoly-game";
    }
}
