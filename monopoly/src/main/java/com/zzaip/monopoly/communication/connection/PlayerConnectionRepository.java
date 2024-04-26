package com.zzaip.monopoly.communication.connection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerConnectionRepository extends CrudRepository<PlayerConnection, Long> {
}
