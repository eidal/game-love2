package com.eidal.gamelove.controllers;

import com.eidal.gamelove.models.Game;
import com.eidal.gamelove.models.Player;
import com.eidal.gamelove.services.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/players")
public class PlayerController {

    PlayerService playerService;

    public PlayerController(PlayerService playerService) {
            this.playerService=playerService;
        }

    /**
     *  GET - Return Player by Id
     */
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Player getPlayerId(@PathVariable("id") Long id) {
        return playerService.getPlayer(id);
        }

    /**
     *  GET - Return list of players with same name
     */
    @RequestMapping(value="/name/{name}",method=RequestMethod.GET)
    @ResponseBody
    public List<Player> getPlayerName(@PathVariable("name") String name) {
        return playerService.getPlayerByName(name);
    }

    /**
      *  GET - Return all players
      */
    @RequestMapping(method= RequestMethod.GET)
    public List<Player> getPlayers(){
        return playerService.getPlayers();
     }

    /**
      *  POST - Create new player
      */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Player createPlayer(@RequestBody Player player) {
        return playerService.createPlayer(player);
    }

    /**
      *  PUT - Modify existent player
      */
    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    @ResponseBody
    public Player updatePlayer(@PathVariable("id") Long id,@RequestBody Map<String,Object> parameters) {
        return playerService.updatePlayer(parameters,id);
    }

    /**
      * DELETE - Erase existent player
      */
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deletePlayer(@PathVariable("id") Long id) {
        playerService.deletePlayer(id);
    }

    /**
     * GET - List Games loved by specific Player
     */
    @RequestMapping(value="/{id}/games", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Game> getGamesLovePlayer(@PathVariable("id") Long id){
        return playerService.getGamesLovePlayer(id);
    }

    /**
     * POST - User like/unlike Game
     */
    @RequestMapping(value="{id}/games/{idGame}", method=RequestMethod.POST)
    @ResponseBody
    public Player playerLoveGame(@PathVariable("id") Long idPlayer,@PathVariable("idGame") Long idGame){
        return playerService.playerLoveGame(idPlayer,idGame);
    }
}
