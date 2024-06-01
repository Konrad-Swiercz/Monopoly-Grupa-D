package com.zzaip.monopoly.game_logic.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameApiTemplates {


@GetMapping("/start")
    public String startTheGame(){
    return "start";
}
@GetMapping("/gameboard")
    public String boardNew(){
    return "gameboard";
}
@GetMapping("/rules")
    public String rules(){ return "rules"; }

@RequestMapping("/**")
    public String redirect() {
        return "redirect:/game/start";
    }
}
