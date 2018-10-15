package com.eidal.gamelove.repository;

import com.eidal.gamelove.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player,Long> {

    Player findByName(String name);
}
