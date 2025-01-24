
package jchess.backend;

import java.util.Arrays;

import jchess.Java_Frontend.Move_History;
import jchess.backend.gamestate.AbstractField;
import jchess.backend.gamestate.SquareBoard;


//TODO: IGameService Interface umsetzen
public class Game_twoPlayers extends AbstractGame
{
    
    SquareBoard squareBoard;
    public Move_History moveHistoryDisplay=null;
    
    public Game_twoPlayers()
    {
        super(2);
        squareBoard = new SquareBoard(0,8, 8,this);
        squareBoard.addPromotionRow(0);
        squareBoard.addPromotionRow(7);
        squareBoard.setFigures4NewGame(0, this.allPlayers.get(1));
        squareBoard.createPawnRow(1, this.allPlayers.get(1));
        squareBoard.setFigures4NewGame(7, this.allPlayers.get(0));
        squareBoard.createPawnRow(6, this.allPlayers.get(0));
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
        return squareBoard.getSquare(localCoords);
    }

    @Override
    public boolean isPromotionField(int[] globalCoords){
        return squareBoard.isPromotionRow(globalCoords[2]);
    }

    // public void addMovetoHistory(BasicMove move){
    //     if(this.moveHistoryDisplay!=null){
    //         moveHistoryDisplay.addMove(move., move.getTo(),move.getSpecialMove(), move.wasEnPassant(), move.getPromotedPiece());
    //     }
    // }
    
    
    
    
}
