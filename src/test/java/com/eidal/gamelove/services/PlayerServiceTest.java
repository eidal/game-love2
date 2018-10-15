package com.eidal.gamelove.services;

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
        Player playerName=playerService.getPlayerByName(nombre);
        assertEquals(player.getId(),player1.getId());
        assertEquals(player.getId(),playerName.getId());
    }

    @Test
    public void updateplayerServiceTest() {
        String nombre="player Service 2";
        Player player=new Player(nombre);
        playerService.createPlayer(player);
        Map<String,Object> datos=new HashMap<String,Object>();
        datos.put("name","BestPlayer00");
        Player player2=playerService.updatePlayer(datos,player.getId());
        assertEquals(player.getId(),player2.getId());
        assertEquals(datos.get("name"),player2.getName());
    }
}
