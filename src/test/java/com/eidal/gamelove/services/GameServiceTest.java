package com.eidal.gamelove.services;

import com.eidal.gamelove.exceptions.GameException;
import com.eidal.gamelove.models.Game;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameServiceTest {

    @Autowired
    GameService gameService;

    @Test
    public void createGameServiceTest() {
        String name="Game Service 1";
        Game game=new Game(name);
        gameService.createGame(game);
        Game game1=gameService.getGame(game.getId());
        Game gameName=gameService.getGameByName(name);
        assertEquals(game.getId(),game1.getId());
        assertEquals(gameName.getName(),name);
        assertEquals(game1.getId(),gameName.getId());
    }

    @Test
    public void updateGameServiceTest() {
        String name="Game Service 2";
        Game game=new Game(name);
        gameService.createGame(game);
        Map<String,Object> data=new HashMap<String,Object>();
        data.put("name","Game00");
        Game game2=gameService.updateGame(data,game.getId());
        assertEquals(game.getId(),game2.getId());
        assertEquals(data.get("name"),game2.getName());
    }

    @Test(expected= GameException.class)
    public void deleteGameServiceTest() {
        String name="Game Service 3";
        Game game=new Game(name);
        Long idGame = gameService.createGame(game).getId();
        Game game2 = gameService.getGame(idGame);
        gameService.deleteGame(idGame);
        game2 = gameService.getGame(idGame);
    }

    @Test
    public void gameWithNumLovesTest(){
        String name="Game Service 4";
        Game game = new Game(name);
        Game game2=gameService.createGame(game);
        List<Game> games = gameService.getGamesNumLoves(1);
        game2.incNumLoves();
        gameService.updateGame(game2);
        List<Game> games2 = gameService.getGamesNumLoves(1);
        assertTrue(games.size()<games2.size());
    }
}
