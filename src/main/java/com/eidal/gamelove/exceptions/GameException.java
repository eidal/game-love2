package com.eidal.gamelove.exceptions;

public class GameException extends RuntimeException {

    public GameException(){}

    public GameException(String message){
        super(message);
    }
}
