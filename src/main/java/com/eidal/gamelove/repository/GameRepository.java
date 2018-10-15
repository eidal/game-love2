package com.eidal.gamelove.repository;

import com.eidal.gamelove.models.Game;
import com.eidal.gamelove.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game,Long> {

    Game findByName(String name);
    List<Game> findBynumLoves(int NumLoves);
}
