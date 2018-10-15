package com.eidal.gamelove.services;

import com.eidal.gamelove.exceptions.PlayerException;
import com.eidal.gamelove.models.Player;
import com.eidal.gamelove.repository.PlayerRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.beanutils.BeanUtils;

@Service
public class PlayerService {

    private PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository){
        this.playerRepository=playerRepository;
    }

    public Player getPlayer(Long id) {
        return checkGetPlayer(id).get();
    }

    public List<Player> getPlayers(){
        return playerRepository.findAll();
    }

    public Player createPlayer(Player player) {
        try {
            Player playeraux= playerRepository.saveAndFlush(player);
            return playeraux;
        } catch (Exception e) {
            throw new PlayerException("Error! Problem create player");
        }
    }

    public void borraPlayer(Long idPlayer) {
        if(checkGetPlayer(idPlayer).isPresent()) {
            playerRepository.deleteById(idPlayer);
        }

    }

    public Player getPlayerByName(String name){
        return playerRepository.findByName(name);
    }

    public Player updatePlayer(Map<String,Object> datos, Long idPlayer) {
        Player player=checkGetPlayer(idPlayer).get();

        try {
            BeanUtils.populate(player,datos);
        } catch (Exception e) {
            throw new PlayerException("Error! Problem update player");
        }
        return playerRepository.saveAndFlush(player);
    }

    private Optional<Player> checkGetPlayer(Long id){
        if(id!=null) {
            if (playerRepository.existsById(id)) {
                return playerRepository.findById(id);
            } else {
                throw new PlayerException("Error! This player not exist");
            }
        }
        else
            throw new PlayerException("Error! Id player can't be null");
    }

}
