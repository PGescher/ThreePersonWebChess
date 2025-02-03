package com.webapp.jchess.services;

import java.lang.reflect.Array;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.webapp.jchess.model.gamestate.SquareBoard;
import com.webapp.jchess.model.gamestate.TriangleBoard;
import com.webapp.jchess.model.AbstractGame;
import com.webapp.jchess.model.Game_threePlayerGame;
import com.webapp.jchess.model.Game_twoPlayers;
import com.webapp.jchess.model.gamestate.Square;
import com.webapp.jchess.model.gamestate.SquareBoard;
import com.webapp.jchess.model.gamestate.TriangleBoard;
import com.webapp.jchess.model.AbstractGame;
import com.webapp.jchess.model.Game_threePlayerGame;
import com.webapp.jchess.model.Game_twoPlayers;
import com.webapp.jchess.model.gamestate.Square;

/*
 * Manages the Game
 * Connecting the Business Logic with the API Communication
 * Do we have three seperate Chessboards?
 */

@Service
public class GameService {

    private AbstractGame game;

    /*
     * Check if active game exists
     */
    public boolean isRunning(){
        if(game == null){
            return false;
        }
        else{
            return true;
        }
    }

    /*
     * Create a new Game object and initialize the Game
     */
    public int newGame(int playerAmount) {
        
        //Abstract Game adds players to a playerlist but does not require names.
        if(playerAmount > 2){
            game = new Game_threePlayerGame();
            return 3;
        }else {
            game = new Game_twoPlayers();
            return 2;
        }
    }

    /*
     * Get the Piece Coordinates
     */
    public void getPieces(ArrayList<int[]> coordList, ArrayList<String> pieceList, ArrayList<String> playerList) {
        game.getDrawablePieces(coordList, pieceList, playerList);
    }

    /*
     * Get the Field for a Coord
     * This should turn a Coord into a Field index of x, y.
     */
    public void getField(int[] coord){
        game.getField(coord);
    }

    /*
     * Select a Field
     */
    public int selectField(int[] coords) {
        System.out.println("Received coordinates in GameService: " + Arrays.toString(coords));
        return game.selectField(coords);
    }

    /*
     * Get the valid Moves / highligthed fields
     * Missing Return type
     */
    public void getHighlightedFields(ArrayList<int[]> coordList, ArrayList<String> fieldType){
        System.out.println("Request Received in GameService!");
        //This function does not return anything...
        /*
        * Dummy Data and Test
        
        coordList.add(new int[]{0,1,3});
        fieldType.add(new String("a"));
        coordList.add(new int[] {0,3,3});
        fieldType.add(new String("p"));
        */
        game.getHighlightedFields(coordList, fieldType);
    }

    public boolean undo_move() {
        //System.out.println("Undo Move!");
        game.undo();
        return true;
    }

    public boolean redo_move(){
        //System.out.println("Redo Move!");
        game.redo();
        return true;
    }
}
