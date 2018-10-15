package com.eidal.gamelove.services;

import com.eidal.gamelove.exceptions.PlayerException;
import com.eidal.gamelove.models.Game;
import com.eidal.gamelove.models.Player;
import org.hibernate.Hibernate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.hibernate.Hibernate.*;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlayerServiceTest {

    @Autowired
    PlayerService playerService;
    @Autowired
    GameService gameService;

    @Test
    public void createPlayerServiceTest() {
        String name="Player Service 1";
        Player player=new Player(name);
        playerService.createPlayer(player);
        Player player1=playerService.getPlayer(player.getId());
        List<Player> playerName=playerService.getPlayerByName(name);
        assertEquals(player.getId(),player1.getId());
        assertTrue(playerName.size()>0);
    }

    @Test
    public void updatePlayerServiceTest() {
        String name="player Service 2";
        Player player=new Player(name);
        playerService.createPlayer(player);
        Map<String,Object> data=new HashMap<String,Object>();
        data.put("name","player00");
        Player player2=playerService.updatePlayer(data,player.getId());
        assertEquals(player.getId(),player2.getId());
        assertEquals(data.get("name"),player2.getName());
    }

    @Test(expected= PlayerException.class)
    public void deletePlayerServiceTest() {
        String name="player Service 3";
        Player player=new Player(name);
        Long idPlayer = playerService.createPlayer(player).getId();
        Player player2 = playerService.getPlayer(idPlayer);
        playerService.deletePlayer(idPlayer);
        player2 = playerService.getPlayer(idPlayer);
    }

    @Test
    public void userLikeGameTest(){
        String name="player Service 4";
        Player player=new Player(name);
        Long idPlayer = playerService.createPlayer(player).getId();
        Player player2 = playerService.getPlayer(idPlayer);
        Set<Game> games = new HashSet<>();
        Game game = new Game("game from Player 4");
        Game game2 = gameService.createGame(game);
        games.add(game2);
        player2.setGames(games);
        player=playerService.updatePlayer(player2);
        assertTrue(player.getGames().size()>0);
    }

    @Test
    public void userLikeGameTest2(){
        String name="player Service 5";
        Player player=new Player(name);
        Long idPlayer = playerService.createPlayer(player).getId();
        Player player2 = playerService.getPlayer(idPlayer);
        Set<Game> games = new HashSet<>();
        Game game = new Game("game from Player 5");
        Game game2 = gameService.createGame(game);
        player=playerService.playerLoveGame(player2.getId(),game2.getId());
        assertTrue(player.getGames().size()>0);
        System.out.println(player.getGames().stream().findFirst().get().toString());
        assertTrue(player.getGames().stream().findFirst().get().getNumLoves()>0);
    }
}
