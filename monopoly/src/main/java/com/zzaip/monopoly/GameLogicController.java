package com.zzaip.monopoly;

import com.zzaip.monopoly.game_logic.logic.GameLogicService;
import com.zzaip.monopoly.game_logic.player.Player;
import com.zzaip.monopoly.game_logic.player.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@Controller
public class GameLogicController {

    private final PlayerService playerService;
    private final GameLogicService gameLogicService;

    @Autowired
    public GameLogicController(PlayerService playerService, GameLogicService gameLogicService) {
        this.playerService = playerService;
        this.gameLogicService = gameLogicService;
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

    // TODO: fetch player name from frontend and pass it for hostGame
//    @GetMapping("/hostRoom")
//    public String hostRoom(Model theModel){
//        gameLogicService.hostGame();
//        List<Player> thePlayerList = playerService.getPlayers();
//        theModel.addAttribute("playerList",thePlayerList);
//
//        return "host_room";
//    }

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
