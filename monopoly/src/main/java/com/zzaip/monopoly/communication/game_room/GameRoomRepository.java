package com.zzaip.monopoly.communication.game_room;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRoomRepository extends CrudRepository<GameRoom, Long> {
}
