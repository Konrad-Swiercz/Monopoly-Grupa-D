package com.zzaip.monopoly;

import com.zzaip.monopoly.game_logic.player.Player;
import com.zzaip.monopoly.game_logic.player.PlayerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@Controller
public class GameLogicController {

    public PlayerService playerService;

    public GameLogicController(PlayerService thePlayerService) {
        playerService = thePlayerService;
    }
    @GetMapping("/addPlayers")
    public String addPlayers(Model theModel){
        Player firstPlayer = new Player("Andrzej");
        playerService.createPlayer(firstPlayer);
        return "start";
    }

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

        List<Player> thePlayerList = playerService.getPlayers();
        theModel.addAttribute("playerList",thePlayerList);

        return "host_room";
    }

    @GetMapping("/gameRoom")
    public String gameRoom(Model theModel){
        Player thePlayer = playerService.findById(1);
        theModel.addAttribute("player1",thePlayer);
        return "monopoly-game";
    }

    @GetMapping("/movePlayer")
    public String movePlayer(Model theModel){
        Player thePlayer = playerService.findById(1);
        int posBefore = playerService.findById(1).getPlayerPosition();
        int move = playerService.RollDice();
        playerService.movePlayer(thePlayer.getPlayerName(), move);
        int posAfter = playerService.findById(1).getPlayerPosition();
        if (posAfter < posBefore) {
            playerService.modifyBalance(thePlayer.getPlayerName(), 200);
        }
        playerService.updatePlayer(thePlayer);
        theModel.addAttribute("player1",thePlayer);
        return "monopoly-game";
    }
}
