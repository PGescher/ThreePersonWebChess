package com.webapp.jchess.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.webapp.jchess.model.Chessboard;
import com.webapp.jchess.model.Game;
import com.webapp.jchess.model.Square;

/*
 * Manages the Game
 * Connecting the Business Logic with the API Communication
 * Do we have three seperate Chessboards?
 */

 @Service
public class GameService {

    private Game game;

    /*
     * Create a new Game object and initialize the Game
     */
    public boolean newGame(int playerAmount) {
        
        this.game = new Game();

        return game.initGame(playerAmount);
    }

    /*
     * Fetch the Gamestate from the Game
     */
    public String getGamestate(){

        return game.getGamestate();
    }
    
    /*
     * Get the Valid Moves for a given Square
     * String square format = {Playerid, x, y}
     */
    public List<String> getvalidMoves(String square){
        return game.getvaidMoves(square);
    }

    /*
     * Post Moves to the Game
     */
    public boolean Move(String startSquare, String endSquare){
        return game.Move(startSquare, endSquare);
    }

}
