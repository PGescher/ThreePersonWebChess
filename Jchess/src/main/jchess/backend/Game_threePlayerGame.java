package jchess.backend;

import java.util.Arrays;

import jchess.backend.gamestate.AbstractField;
import jchess.backend.gamestate.SquareBoard;
import jchess.backend.gamestate.TriangleBoard;

public class Game_threePlayerGame extends AbstractGame{

    final int numSquareBoards = 3;
    SquareBoard[] squareBoards;
    TriangleBoard triBoard;

    public Game_threePlayerGame (){
        super(3);
        squareBoards = new SquareBoard[numSquareBoards];
        for(int i = 0;i<numSquareBoards;i++){
            squareBoards[i] = new SquareBoard(i,8, 4, this);
            squareBoards[i].addPromotionRow(0);
            squareBoards[i].setFigures4NewGame(0, this.allPlayers.get(i));
            squareBoards[i].createPawnRow(1, this.allPlayers.get(i));
        }

        triBoard = new TriangleBoard(3,8,this);
    }

    @Override
    public AbstractField getField(int[] globalCoords) {
        //For Squareboards
        if(globalCoords[0]<0){
            return null;
        } else if(globalCoords[0]<3){
            if(globalCoords.length!=3)return null;
            int[] localCoords = Arrays.copyOfRange(globalCoords, 1, globalCoords.length);
            squareBoards[globalCoords[0]].getSquare(localCoords);
        } else if(globalCoords[0]==3){
            if(globalCoords.length!=4)return null;
            int[] localCoords = Arrays.copyOfRange(globalCoords, 1, globalCoords.length);
            triBoard.getTriangle(localCoords);
        }
        return null;
    }

    @Override
    public void endGame(String str) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'endGame'");
    }

    @Override
    public boolean validCoordinates(int[] coords) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validCoordinates'");
    }

    @Override
    public boolean isPromotionField(int[] coords) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isPromotionField'");
    }
    
}
