package com.zzaip.monopoly;

import com.zzaip.monopoly.game_logic.player.Player;
import com.zzaip.monopoly.game_logic.player.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//public class MonopolyApplication implements CommandLineRunner {
public class MonopolyApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonopolyApplication.class, args);
	}

//	@Autowired
//	private PlayerService playerService;
//	@Override
//	public void run(String... args) throws Exception {
//		Player newPlayer = new Player("Konrad");
//		playerService.createPlayer(newPlayer);
//		System.out.println(newPlayer.toString() + "has been added to the game");
//		int turns = 15;
//		while(turns > 0) {
//			int move = playerService.RollDice();
//			System.out.println("Player has rolled, the move is: " + move);
//			int posBefore = playerService.findById(1).getPlayerPosition();
//			String playerName = "Konrad";
//			playerService.movePlayer(playerName, move);
//			System.out.println("Player has moved to position: " + playerService.findById(1).getPlayerPosition());
//			if (playerService.moveToJail(playerName, 1) == true) {
//				System.out.println("Player has been moved to jail");
//			}
//
//			int posAfter = playerService.findById(1).getPlayerPosition();
//			if (posAfter < posBefore) {
//				playerService.modifyBalance(playerName, 200);
//				System.out.println("Player has went through the start, his account balance is now: " + playerService.findById(1).getPlayerBalance());
//			}
//			turns--;
//		}
//	}
}
