package com.zzaip.monopoly.communication.inbound;
import com.zzaip.monopoly.communication.dto.JoinGameDTO;
import com.zzaip.monopoly.communication.dto.PlayerConnectionDTO;
import com.zzaip.monopoly.dto.GameDTO;
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
    public void receiveGameUpdate(@RequestBody GameDTO gameState) {
        inboundCommunicationService.receiveGameUpdate(gameState);
    }

    @ResponseBody
    @PostMapping("/player")
    public JoinGameDTO addPlayer(@RequestBody JoinGameRequest joinGameRequest) {
        return inboundCommunicationService.addPlayerToCurrentGame(
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

    @PutMapping("/connection-update")
    public void receivePlayerConnectionUpdate(@RequestBody PlayerConnectionDTO playerConnectionDTO) {
        inboundCommunicationService.receivePlayerConnection(playerConnectionDTO);
    }
}
