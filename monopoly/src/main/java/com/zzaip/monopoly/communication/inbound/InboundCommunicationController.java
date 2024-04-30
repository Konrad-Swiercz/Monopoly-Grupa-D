package com.zzaip.monopoly.communication.inbound;

import com.zzaip.monopoly.communication.GameState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class InboundCommunicationController {
    private final InboundCommunicationService inboundCommunicationService;

    @Autowired
    public InboundCommunicationController(InboundCommunicationService inboundCommunicationService) {
        this.inboundCommunicationService = inboundCommunicationService;
    }

    @PutMapping("/game")
    public void receiveGameUpdate(@RequestBody GameState gameState) {
        inboundCommunicationService.receiveGameUpdate(gameState);
    }

    @ResponseBody
    @PostMapping("/player")
    public GameState addPlayer(@RequestBody JoinGameRequest joinGameRequest) {
        return inboundCommunicationService.addPlayer(
                joinGameRequest.getPlayerName(),
                joinGameRequest.getPlayerURL());
    }
    @ResponseBody
    @GetMapping("/connection")
    public ConnectionCheckResponse connectionCheck(@RequestParam(name = "url") String playerURL) {
        return new ConnectionCheckResponse(
                inboundCommunicationService.connectionCheck(playerURL)
        );
    }
}