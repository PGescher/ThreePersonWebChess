package com.webapp.jchess.model;

import java.util.Arrays;
import java.util.stream.IntStream;

import com.webapp.jchess.model.gamestate.AbstractBoard;
import com.webapp.jchess.model.gamestate.AbstractField;
import com.webapp.jchess.model.gamestate.SquareBoard;
import com.webapp.jchess.model.gamestate.TriangleBoard;
import com.webapp.jchess.model.pieces.Piece;

public class Game_threePlayerGame extends AbstractGame{
    final int numSquareBoards = 3;

    AbstractBoard[] boards;

    public Game_threePlayerGame (){
        super(3);
        boards = new AbstractBoard[4];

        for(int i = 0;i<numSquareBoards;i++){
            boards[i] = new SquareBoard(i, 8, 4, this);
            ((SquareBoard)boards[i]).addPromotionRow(0);
            ((SquareBoard)boards[i]).setFigures4NewGame(0, this.activePlayers.get(i));
            ((SquareBoard)boards[i]).createPawnRow(1, this.activePlayers.get(i));
        }

        TriangleBoard triBoard = new TriangleBoard(3, 8,this);

        //triBoard.setPawn(0, this.allPlayers.get(0));
        //triBoard.setPawn(14, this.allPlayers.get(1));
        //triBoard.setPawn(63, this.allPlayers.get(2));

        // triBoard.setPawn(32, this.allPlayers.get(0));
        // triBoard.setPawn(45, this.allPlayers.get(1));
        // triBoard.setPawn(50, this.allPlayers.get(2));

        boards[3] = triBoard;
    }


    @Override
    public AbstractField getField(int[] globalCoords) {
        if(globalCoords==null)return null;

        //For Squareboards
        if(globalCoords[0]<0){
            return null;
        } else if(globalCoords[0]<3){
            if(globalCoords.length!=3){ return null;} 
            SquareBoard sqBoard = (SquareBoard)boards[globalCoords[0]];
            int[] localCoords = Arrays.copyOfRange(globalCoords, 1, globalCoords.length);

            
            if(sqBoard.validCoordinates(localCoords)){
                //Inside the Squareboard
                return sqBoard.getField(localCoords);
            }else if(localCoords[1]>=sqBoard.getdim_y()&&localCoords[0]<sqBoard.getdim_x()&&localCoords[0]>=0){
                // System.out.println("Out of Squareboard" + Arrays.toString(globalCoords) );
                //Moving out of the squareboard
                int[] newCoords = new int[4];
                newCoords[0] = 3;
                newCoords[1+(3-globalCoords[0])%3] = localCoords[0];
                newCoords[1+(4-globalCoords[0])%3] = 0;
                newCoords[1+(5-globalCoords[0])%3] = sqBoard.getdim_x()-localCoords[0]-1;
                // System.out.println("New Cooords" + Arrays.toString(newCoords) );
                return this.getField(newCoords);
            }else{
                //error
                return null;
            }
            
        } else if(globalCoords[0]==3){
            if(globalCoords.length!=4)return null;
            int[] localCoords = Arrays.copyOfRange(globalCoords, 1, globalCoords.length);
            if(boards[3].validCoordinates(localCoords)){
                //Inside triangle
                return boards[3].getField(localCoords);
            }

            //Check if we are outside the triangle and can move to a squareboard
            int idx = IntStream.range(0, localCoords.length).filter(i -> localCoords[i] < 0).findFirst().orElse(-1);
            if(idx != -1 && localCoords[(idx+1)%3]>=0 && localCoords[(idx+1)%3] < ((SquareBoard)boards[0]).getdim_x()){
                // System.out.println("Out of TriBoard" + Arrays.toString(globalCoords) );
                int[] newCoords = new int[3];
                newCoords[0]=(4-idx)%3;
                newCoords[1]=((SquareBoard)boards[2]).getdim_x()-1-localCoords[(idx+1)%3];
                newCoords[2]=((SquareBoard)boards[2]).getdim_y()+localCoords[idx];
                // System.out.println("New Cooords" + Arrays.toString(newCoords) );
                return this.getField(newCoords);
            }
            
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
            int [] localCoords = Arrays.copyOfRange(globalCoords, 1, globalCoords.length);
            return boards[3].validCoordinates(localCoords);
        } else if(globalCoords.length == 3){
            // Square Board
            int[] localCoords = Arrays.copyOfRange(globalCoords, 1, globalCoords.length);
            return ((SquareBoard)boards[globalCoords[0]]).validCoordinates(localCoords);
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


    /*
     * Check if clicked Field is a promotion field to request more information from user.
     */
    @Override
    public boolean isPromotionField(int[] globalCoords) {
        
        if(globalCoords[0]<3) {
            if(((SquareBoard)boards[globalCoords[0]]).isPromotionRow(globalCoords[2])) {
                switch (this.getActivePlayer().playerID) {
                    case PID1:
                        if(globalCoords[0] != 1)return true;
                        break;
                    case PID2:
                        if(globalCoords[0] != 2)return true;
                        break;
                    case PID3:
                        if(globalCoords[0] != 3)return true;
                        break;
                    default:
                        break;
                }
                return false;
            }
        }
        return false;
    }
    
}
