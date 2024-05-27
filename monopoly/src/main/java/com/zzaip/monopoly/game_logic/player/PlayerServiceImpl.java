package com.zzaip.monopoly.game_logic.player;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService{
    private PlayerRepository playerRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository thePlayerRepository){  playerRepository = thePlayerRepository; }

    @Override
    public int RollDice(){
        Random random = new Random();
        return (random.nextInt(12 - 2 + 1) + 2);
    };

    @Override
    public List<Player> getPlayers(){
        return playerRepository.findAll();
    }
    @Override
    public Player createPlayer(Player player){
        return playerRepository.save(player);
    }

    @Override
    public void deleteAllPlayers() {
        playerRepository.deleteAll();
    }

    @Override
    public Player updatePlayer(Player player){
        playerRepository.save(player);
        return player;
    };

    @Override
    public Player findById(int id){
        Optional<Player> result = playerRepository.findById(id);
        Player thePlayer = null;
        if (result.isPresent()) {
            thePlayer = result.get();
        }
        else {
            throw new RuntimeException("Did not find player id - " + id);
        }
        return thePlayer;
    }

    @Override
    public Player findByName(String name) {
        Optional<Player> result = Optional.ofNullable(playerRepository.findByPlayerName(name));
        Player thePlayer = null;
        if (result.isPresent()) {
            thePlayer = result.get();
        }
        return thePlayer;
    }

    @Override
    public void movePlayer(String playerName, int position){
        Player player = playerRepository.findByPlayerName(playerName);
        if (player.getPlayerPosition() + position <=28){
            player.setPlayerPosition(player.getPlayerPosition() + position);
        }
        else{
            player.setPlayerPosition(player.getPlayerPosition() + position - 28);
        }

        playerRepository.save(player);
    };
    @Override
    public void modifyBalance(String playerName, float balanceChange){
        Player player = playerRepository.findByPlayerName(playerName);
        float newBalance = player.getPlayerBalance() + balanceChange;
        player.setPlayerBalance(newBalance);
        playerRepository.save(player);
    };
    @Override
    public boolean moveToJail(String playerName, int jailTurns){
        Player player = playerRepository.findByPlayerName(playerName);
        if (player.getPlayerPosition() == 8){
            player.setPlayerPosition(22);
            player.setJailTurns(jailTurns);
            playerRepository.save(player);
            return true;
        };
        return false;

    };
    @Override
    public void updatePlayerIfLost(Player player){
        if (player.getPlayerBalance() < 0 ){
            player.setHasLost(true);
            playerRepository.save(player);
        }
    };
}
