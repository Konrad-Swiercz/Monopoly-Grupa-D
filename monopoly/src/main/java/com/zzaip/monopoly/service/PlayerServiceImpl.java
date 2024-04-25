package com.zzaip.monopoly.service;

import com.zzaip.monopoly.model.Player;
import com.zzaip.monopoly.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
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
    public Player createPlayer(Player player){
        return playerRepository.save(player);
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
            // we didn't find the employee
            throw new RuntimeException("Did not find player id - " + id);
        }

        return thePlayer;
    }
    @Override
    public void movePlayer(String playerName, int position){
        Player player = playerRepository.findByPlayerName(playerName);
        if (player.getPlayer_position() + position <=28){
            player.setPlayer_position(player.getPlayer_position() + position);
        }
        else{
            player.setPlayer_position(player.getPlayer_position() + position - 28);
        }

        playerRepository.save(player);
    };
    @Override
    public void modifyBalance(String playerName, float balanceChange){
        Player player = playerRepository.findByPlayerName(playerName);
        float newBalance = player.getPlayer_balance() + balanceChange;
        player.setPlayer_balance(newBalance);
        playerRepository.save(player);
    };
    @Override
    public boolean moveToJail(String playerName, int jailTurns){
        Player player = playerRepository.findByPlayerName(playerName);
        if (player.getPlayer_position() == 8){
            player.setPlayer_position(22);
            player.setJail_turns(jailTurns);
            playerRepository.save(player);
            return true;
        };
        return false;

    };
    @Override
    public void playerLost(String playerName){
        Player player = playerRepository.findByPlayerName(playerName);
        if (player.getPlayer_balance() < 0 ){
            player.setHas_lost(true);
            playerRepository.save(player);
        }
    };
}
