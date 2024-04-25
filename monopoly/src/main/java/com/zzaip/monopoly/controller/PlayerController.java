package com.zzaip.monopoly.controller;

import com.zzaip.monopoly.model.Player;
import com.zzaip.monopoly.service.PlayerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PlayerController {
    public PlayerService playerService;

    public PlayerController(PlayerService thePlayerService){ playerService = thePlayerService;}


    @GetMapping("/")
    public String startPage(){

        return "start";
    }

}

