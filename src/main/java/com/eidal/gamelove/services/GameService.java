package com.eidal.gamelove.services;

import com.eidal.gamelove.exceptions.GameException;
import com.eidal.gamelove.models.Game;
import com.eidal.gamelove.models.Player;
import com.eidal.gamelove.repository.GameRepository;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.criterion.Order;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transaction;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class GameService {

    private GameRepository gameRepository;

    public GameService(GameRepository gameRepository){
        this.gameRepository=gameRepository;
    }

    public Game getGame(Long id){
        return checkGetGame(id).get();
    }

    public List<Game> getGames(){
        return gameRepository.findAll();
    }

    public Game createGame(Game game){
        try {
            return gameRepository.save(game);
        } catch (DataIntegrityViolationException | ConstraintViolationException e){
            throw new GameException("Error! The name of game already exist!");
        }
    }

    public void deleteGame(Long idGame){
        if(checkGetGame(idGame).isPresent()) {
            gameRepository.deleteById(idGame);
        }

    }

    public Game getGameByName(String name){
        return gameRepository.findByName(name);
    }

    public Game updateGame(Game game){
        Game gameAux=checkGetGame(game.getId()).get();
        gameAux.setName(game.getName());
        gameAux.setNumLoves(game.getNumLoves());
        return gameRepository.saveAndFlush(gameAux);
    }

    public Game updateGame(Map<String,Object> datos, Long idGame){
        Game game=checkGetGame(idGame).get();

        try {
            BeanUtils.populate(game, datos);
            return gameRepository.saveAndFlush(game);
        } catch (DataIntegrityViolationException e){
            throw new GameException("Error! The name of game already exist!");
        } catch (Exception e) {
            throw new GameException("Error! Problem update Game");
        }
    }

    public List<Game> getGamesNumLoves(int numLoves){
        return gameRepository.findBynumLoves(numLoves);
    }

    public List<Game> getTopGames(int numGames){
        //int limited = numGames;
        //Pageable pageable = new PageRequest(0,limited);
        return gameRepository.findAll().stream().sorted(Comparator.comparing(Game::getNumLoves).reversed())
                                                                .limit(Long.valueOf(numGames))
                                                                .collect(Collectors.toList());

    }

    private Optional<Game> checkGetGame(Long id) throws GameException{
        if(id!=null) {
            if (gameRepository.existsById(id)) {
                return gameRepository.findById(id);
            } else {
                throw new GameException("Error! This Game not exist");
            }
        }
        else
            throw new GameException("Error! Id Game can't be null");
    }

}