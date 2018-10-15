package com.eidal.gamelove.repository;

import com.eidal.gamelove.models.Game;
import com.eidal.gamelove.models.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transaction;
import java.util.List;
import java.util.stream.Stream;

public interface GameRepository extends JpaRepository<Game,Long> {

    Game findByName(String name);
    List<Game> findBynumLoves(int NumLoves);
}
