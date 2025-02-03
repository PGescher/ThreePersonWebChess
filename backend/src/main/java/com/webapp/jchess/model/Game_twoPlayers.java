
package com.webapp.jchess.model;

import java.util.Arrays;

//import com.webapp.jchess.Java_Frontend.Move_History;
import com.webapp.jchess.model.gamestate.AbstractField;
import com.webapp.jchess.model.gamestate.SquareBoard;


//TODO: IGameService Interface umsetzen
public class Game_twoPlayers extends AbstractGame
{
    
    SquareBoard squareBoard;
    //public Move_History moveHistoryDisplay=null;
    
    public Game_twoPlayers()
    {
        super(2);
        squareBoard = new SquareBoard(0, 8, 8, this);
        squareBoard.addPromotionRow(0);
        squareBoard.addPromotionRow(7);
        squareBoard.setFigures4NewGame(0, this.activePlayers.get(1));
        squareBoard.createPawnRow(1, this.activePlayers.get(1));
        squareBoard.setFigures4NewGame(7, this.activePlayers.get(0));
        squareBoard.createPawnRow(6, this.activePlayers.get(0));
        // this.moveHistoryDisplay = new Move_History(this);
    }

    @Override
    public void endGame(String message){
        System.out.println(message);
    }    

    @Override
    public boolean validCoordinates(int [] globalCoords){
        int[] localCoords = Arrays.copyOfRange(globalCoords, 1, globalCoords.length);
        return squareBoard.validCoordinates(localCoords);
    }


    @Override
    public AbstractField getField(int []globalCoords){
        int[] localCoords = Arrays.copyOfRange(globalCoords, 1, globalCoords.length);
        return squareBoard.getField(localCoords);
    }
    

    @Override
    public boolean isPromotionField(int[] coords){
        return squareBoard.isPromotionRow(coords[1]);
    }

    // public void addMovetoHistory(BasicMove move){
    //     if(this.moveHistoryDisplay!=null){
    //         moveHistoryDisplay.addMove(move., move.getTo(),move.getSpecialMove(), move.wasEnPassant(), move.getPromotedPiece());
    //     }
    // }
    
    
    
    
}
