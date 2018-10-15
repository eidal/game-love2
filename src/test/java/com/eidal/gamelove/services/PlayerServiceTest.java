package com.eidal.gamelove.services;

import com.eidal.gamelove.exceptions.PlayerException;
import com.eidal.gamelove.models.Player;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlayerServiceTest {

    @Autowired
    PlayerService playerService;

    @Test
    public void createPlayerServiceTest() {
        String nombre="Player Service 1";
        Player player=new Player(nombre);
        playerService.createPlayer(player);
        Player player1=playerService.getPlayer(player.getId());
        List<Player> playerName=playerService.getPlayerByName(nombre);
        assertEquals(player.getId(),player1.getId());
        assertTrue(playerName.size()>0);
    }

    @Test
    public void updatePlayerServiceTest() {
        String nombre="player Service 2";
        Player player=new Player(nombre);
        playerService.createPlayer(player);
        Map<String,Object> datos=new HashMap<String,Object>();
        datos.put("name","player00");
        Player player2=playerService.updatePlayer(datos,player.getId());
        assertEquals(player.getId(),player2.getId());
        assertEquals(datos.get("name"),player2.getName());
    }

    @Test(expected= PlayerException.class)
    public void deletePlayerServiceTest() {
        String nombre="player Service 3";
        Player player=new Player(nombre);
        Long idPlayer = playerService.createPlayer(player).getId();
        Player player2 = playerService.getPlayer(idPlayer);
        playerService.deletePlayer(idPlayer);
        player2 = playerService.getPlayer(idPlayer);
    }
}
