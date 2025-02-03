package com.webapp.jchess.model;

import java.util.Arrays;

import com.webapp.jchess.model.gamestate.AbstractField;
import com.webapp.jchess.model.gamestate.SquareBoard;
import com.webapp.jchess.model.gamestate.TriangleBoard;

public class Game_threePlayerGame extends AbstractGame{
    final int numSquareBoards = 3;
    SquareBoard[] squareBoards;
    TriangleBoard triBoard;

    public Game_threePlayerGame (){
        super(3);
        squareBoards = new SquareBoard[numSquareBoards];
        for(int i = 0;i<numSquareBoards;i++){
            squareBoards[i] = new SquareBoard(i, 8, 4, this);
            squareBoards[i].addPromotionRow(0);
            squareBoards[i].setFigures4NewGame(0, this.allPlayers.get(i));
            squareBoards[i].createPawnRow(1, this.allPlayers.get(i));
        }

        triBoard = new TriangleBoard(3, 8,this);
        triBoard.setPawn(0, this.allPlayers.get(0));
        triBoard.setPawn(2, this.allPlayers.get(1));
        triBoard.setPawn(4, this.allPlayers.get(2));
        triBoard.setPawn(6, this.allPlayers.get(0));
        triBoard.setPawn(8, this.allPlayers.get(1));
    }

    @Override
    public AbstractField getField(int[] globalCoords) {
        //For Squareboards
        if(globalCoords[0]<0){
            return null;
        } else if(globalCoords[0]<3){
            if(globalCoords.length!=3){ return null;} 
            int[] localCoords = Arrays.copyOfRange(globalCoords, 1, globalCoords.length);
            //This needs to return this below, or not?
            return squareBoards[globalCoords[0]].getSquare(localCoords);
        } else if(globalCoords[0]==3){
            //TODO: Correct the Coordinates of the Triangles. Frontend only return Board, Row and Column
            if(globalCoords.length!=4)return null;
            int[] localCoords = Arrays.copyOfRange(globalCoords, 1, globalCoords.length);
            return triBoard.getTriangle(localCoords);
        }
        return null;
    }

    @Override
    public void endGame(String str) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'endGame'");
    }

    @Override
    public boolean validCoordinates(int[] globalCoords) {
        if(globalCoords[0] == 3) {
            //Triangle Board
            //System.out.println("Game_threePlayerGame: valid Coordinates for Triangle Board not yet implemented");
            int [] localCoords = Arrays.copyOfRange(globalCoords, 1, globalCoords.length);
            return triBoard.validCoordinates(localCoords);
        } else if(globalCoords.length == 3){
            // Square Board
            int[] localCoords = Arrays.copyOfRange(globalCoords, 1, globalCoords.length);
            return squareBoards[globalCoords[0]].validCoordinates(localCoords);
        } else {
            return false;
        }
    }
    /* 
    @Override
    public void getHighlightedFields(ArrayList<int[]> coordList, ArrayList<String> fieldType){
        System.out.println("Request Received in Abstract Game!");
        if(!pieceIsSelected()){System.out.println("No Piece is selected, returning!"); return;}
        System.out.println("A Piece is selected to get highlighted Fields!");
        coordList.add(selectedPiece.getGlobalCoordinates());
        System.out.println("Coordinates of selected piece: " + coordList.getFirst().toString());
        fieldType.add("a");
        System.out.println("Iterating Fields ...");
        for(AbstractField field: selectedPiece.validMoves()){
            System.out.println("Getting Valid Coordinates");
            coordList.add(field.getGlobalCoordinates());
            fieldType.add("p");
            System.out.println("Adding possible Move: " + coordList.getLast().toString());
        }
        System.out.println("Completed Iterating!");
        
    }
    */

    @Override
    public boolean isPromotionField(int[] coords) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'isPromotionField'");
        return false;
    }
    
}
