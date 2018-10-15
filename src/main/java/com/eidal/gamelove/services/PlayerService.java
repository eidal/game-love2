package com.eidal.gamelove.services;

import com.eidal.gamelove.exceptions.GameException;
import com.eidal.gamelove.exceptions.PlayerException;
import com.eidal.gamelove.models.Game;
import com.eidal.gamelove.models.Player;
import com.eidal.gamelove.repository.GameRepository;
import com.eidal.gamelove.repository.PlayerRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;

import javax.transaction.Transactional;

@Service
@Transactional
public class PlayerService {

    private PlayerRepository playerRepository;
    private GameRepository gameRepository;

    public PlayerService(PlayerRepository playerRepository, GameRepository gameRepository){
        this.playerRepository=playerRepository;
        this.gameRepository=gameRepository;
    }

    public Player getPlayer(Long id) {
        return checkGetPlayer(id).get();
    }

    public List<Player> getPlayers(){
        return playerRepository.findAll();
    }

    public Player createPlayer(Player player) {
        try {
            return playerRepository.save(player);
        } catch (Exception e){
            throw new PlayerException("Error! Problem create player");
        }
    }

    public void deletePlayer(Long idPlayer) {
        if(checkGetPlayer(idPlayer).isPresent()) {
            playerRepository.deleteById(idPlayer);
        }

    }

    public List<Player> getPlayerByName(String name){
        return playerRepository.findByName(name);
    }

    public Player updatePlayer(Player player){
        return playerRepository.saveAndFlush(player);
    }

    public Player updatePlayer(Map<String,Object> datos, Long idPlayer) {
        Player player=checkGetPlayer(idPlayer).get();

        try {
            BeanUtils.populate(player,datos);
            return playerRepository.saveAndFlush(player);
        } catch (Exception e) {
            throw new PlayerException("Error! Problem update player");
        }
    }

    public Player playerLoveGame(Long idPlayer, Long idGame){
        Optional<Player> player=checkGetPlayer(idPlayer);
        if(player.isPresent()){
            Optional<Game> game=gameRepository.findById(idGame);
            if(game.isPresent()){
                Player playerAux=player.get();
                Set<Game> games = playerAux.getGames();
                Game gameAux=game.get();
                if (games.contains(gameAux)){
                    gameAux.decNumLoves();
                    gameAux=gameRepository.saveAndFlush(gameAux);
                    games.remove(gameAux);
                } else {
                    gameAux.incNumLoves();
                    gameAux=gameRepository.saveAndFlush(gameAux);
                    games.add(gameAux);
                }

                return playerRepository.saveAndFlush(playerAux);

            } else {
                throw new PlayerException("Error! The game not exist");
            }
        } else {
            throw new PlayerException("Error! The player not exist");
        }
    }

    public List<Game> getGamesLovePlayer(Long idPlayer){
        Optional<Player> player = checkGetPlayer(idPlayer);
        if(player.isPresent()){
            return (List<Game>)player.get().getGames();
        } else{
            throw new GameException("Error! The game not exist");
        }
    }

    private Optional<Player> checkGetPlayer(Long id) {
        if (id != null) {
            if (playerRepository.existsById(id)) {
                return playerRepository.findById(id);
            } else {
                throw new PlayerException("Error! This player not exist");
            }
        } else
            throw new PlayerException("Error! Id player can't be null");
    }

}
