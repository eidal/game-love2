package com.eidal.gamelove.controllers;

import com.eidal.gamelove.models.Game;
import com.eidal.gamelove.services.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/games")
public class GameController {

    GameService gameService;

    public GameController(GameService gameService) {
            this.gameService=gameService;
        }

    /**
     *  GET - Return Game by Id
     */
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Game getGameId(@PathVariable("id") Long id) {
        return gameService.getGame(id);
        }

    /**
     *  GET - Return list of Games with same name
     */
    @RequestMapping(value="/name/{name}",method=RequestMethod.GET)
    @ResponseBody
    public Game getGameName(@PathVariable("name") String name) {
        return gameService.getGameByName(name);
    }

    /**
      *  GET - Return all Games
      */
    @RequestMapping(method= RequestMethod.GET)
    public List<Game> getGames(){
        return gameService.getGames();
     }

    /**
      *  POST - Create new Game
      */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Game createGame(@RequestBody Game game) {
        return gameService.createGame(game);
    }

    /**
      *  PUT - Modify existent Game
      */
    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    @ResponseBody
    public Game updateGame(@PathVariable("id") Long id,@RequestBody Map<String,Object> parameters) {
        return gameService.updateGame(parameters,id);
    }

    /**
      * DELETE - Erase existent Game
      */
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteGame(@PathVariable("id") Long id) {
        gameService.deleteGame(id);
    }

    /**
     * GET - List of top X loved games
     */
    @RequestMapping(value="/top/{numGames}", method=RequestMethod.GET)
    @ResponseBody
    public List<Game> getTopGames(@PathVariable("numGames") int numGames){
        return gameService.getTopGames(numGames);
    }
}
