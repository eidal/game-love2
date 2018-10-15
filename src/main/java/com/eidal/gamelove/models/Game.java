package com.eidal.gamelove.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ComparisonChain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name="games")
public class Game implements Comparable<Game> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @NotNull
    @Column(unique=true)
    String name;

    int numLoves;

    @JsonIgnore
    @ManyToMany(mappedBy = "games",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Player> players = new HashSet<>();

    public Game(){}

    public Game(@NotNull String name) {

        this.name = name;
        this.numLoves=0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumLoves() {
        return numLoves;
    }

    public void setNumLoves(int numLoves) {
        this.numLoves = numLoves;
    }

    public void incNumLoves(){
        this.numLoves = ++numLoves;
    }

    public void decNumLoves(){
        this.numLoves = numLoves>0 ? --numLoves : 0;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(id, game.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", numLoves=" + numLoves +
                '}';
    }

    @Override
    public int compareTo(Game otherGame){
        return ComparisonChain.start()
                    .compare(numLoves, otherGame.numLoves)
                    .compare(name, otherGame.name)
                    .result();
    }
}
