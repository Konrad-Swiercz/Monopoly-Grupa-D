package com.zzaip.monopoly.communication.outbound;

import com.zzaip.monopoly.communication.connection.PlayerConnection;
import com.zzaip.monopoly.communication.dto.JoinGameDTO;
import com.zzaip.monopoly.communication.dto.PlayerConnectionDTO;
import com.zzaip.monopoly.dto.GameDTO;
import com.zzaip.monopoly.communication.inbound.ConnectionCheckResponse;
import com.zzaip.monopoly.communication.inbound.JoinGameRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class ExternalApiConsumer {
    private String baseURL;
    private static final String RECEIVE_GAME_UPDATE_ENDPOINT = "/game";
    private static final String ADD_PLAYER_ENDPOINT = "/player";
    private static final String CONNECTION_CHECK_ENDPOINT = "/connection";

    private static final String RECEIVE_PLAYER_CONNECTION_UPDATE = "/connection-update";

    public void callReceiveGameUpdate(GameDTO gameDTO) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<GameDTO> request = new HttpEntity<>(gameDTO, headers);
        restTemplate.put(
                makeURL(RECEIVE_GAME_UPDATE_ENDPOINT),
                request
        );
    }

    public void callReceivePlayerConnection(PlayerConnectionDTO playerConnectionDTO) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PlayerConnectionDTO> request = new HttpEntity<>(playerConnectionDTO, headers);
        restTemplate.put(
                makeURL(RECEIVE_PLAYER_CONNECTION_UPDATE),
                request
        );
    }

    public JoinGameDTO callAddPlayer(JoinGameRequest joinGameRequest) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<JoinGameRequest> request = new HttpEntity<>(joinGameRequest, headers);
        return restTemplate.postForObject(
                makeURL(ADD_PLAYER_ENDPOINT),
                request,
                JoinGameDTO.class
        );
    }

    public boolean callConnectionCheck(String myURL) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("url", myURL);
        RestTemplate restTemplate = new RestTemplate();
        ConnectionCheckResponse result = restTemplate.getForObject(
                makeURL(CONNECTION_CHECK_ENDPOINT),
                ConnectionCheckResponse.class,
                params
        );
        assert result != null;
        return result.isConnectedToActiveGame();
    }

    private String makeURL(String endpoint) {
        return this.baseURL + endpoint;
    }

}
