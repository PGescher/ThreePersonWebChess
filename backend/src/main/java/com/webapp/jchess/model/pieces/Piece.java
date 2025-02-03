package com.webapp.jchess.model.pieces;
import java.util.ArrayList;
import java.util.Arrays;

import com.webapp.jchess.model.AbstractGame;
import com.webapp.jchess.model.Player;
import com.webapp.jchess.model.Player.player_IDS;
import com.webapp.jchess.model.gamestate.AbstractField;
import com.webapp.jchess.model.gamestate.Square;
import com.webapp.jchess.model.gamestate.SquareBoard;
import com.webapp.jchess.model.gamestate.Triangle;


public abstract class Piece
{
    public enum typesOfPieces{
        Pawn, Rook, Knight, Bishop, King, Queen
    }

    public boolean wasMoved = false;
    public AbstractGame game; // <-- this relations isn't in class diagram, but it's necessary :/
    public AbstractField field;
    public Player player;
    public typesOfPieces pieceType;
    public String symbol;
    //public String imageName;
    protected ArrayList<movementVector> movementOptions;

    public Piece(AbstractGame game, Player player, typesOfPieces _pieceType)
    {
        this.game = game;
        this.player = player;
        this.pieceType = _pieceType;

        //Set .image
        /*
        imageName = _pieceType.name();
        if(player.playerID==player_IDS.PID1) {imageName +="-W.png"; }
        else{imageName +="-B.png"; }
        movementOptions  = new ArrayList<>();
        */

        movementOptions = new ArrayList<>();
    }

    //public void specialMoves(ArrayList<Square> validMoves){};

    public ArrayList<AbstractField> possibleMoves(){
        ArrayList<AbstractField> allMoves = new ArrayList<AbstractField>();
        
        for(movementVector moveVec: movementOptions){
            //TODO: Cast to square a good idea?
            recursiveDirectionCheck(allMoves, this, this.field, moveVec,0);
        }

        //this.specialMoves(allMoves);

        return allMoves;
    }

    public int [] getGlobalCoords(){
        return this.field.getGlobalCoords();
    }
    public ArrayList<AbstractField> validMoves()
    {
        ArrayList<AbstractField> allMoves = possibleMoves();

        //Find relevant king
        King relevantKing = game.kings.get(this.player.playerID);

        //Collect valid moves
        ArrayList<AbstractField> validMoves = new ArrayList<AbstractField>();
        for(AbstractField move: allMoves){
            if(relevantKing.willBeSafeAfterMove(this.field, move)){
                validMoves.add(move);
            }
        }
        return validMoves;
    }

    public typesOfPieces getType()
    {
        return this.pieceType;
    }

    public static void recursiveDirectionCheck(ArrayList<AbstractField> validMoves,Piece piece,
    AbstractField _lastField,movementVector moveVec,int stepCounter) {
        
        if(_lastField.getGlobalCoords().length > 3)
        {   
            //Is Triangle - Move Calculation inside Triangle.
            if(!(_lastField instanceof Triangle))return;
            Triangle lastTriangle= (Triangle)_lastField;
            
            //If TriangleMovementVectors are all 0 this is the Vector for SquareBoard Movement
            if(moveVec.TrianglemovementVector_X == 0 && moveVec.TrianglemovementVector_Y == 0 && moveVec.TrianglemovementVector_Z == 0) return;

            //TODO: Check if Dead Code
            if(lastTriangle==null){
                System.err.println("ERROR: Square should not be null!");
                return;
            }
            
            //if no steps are left
            if(moveVec.maxSteps<=stepCounter) return;
            int boardIdx = lastTriangle.getGlobalCoords()[0];

            //Get Orientation of current Field from sum of Coords
            int [] localCoords = lastTriangle.getLocalCoords();
            //if sum is 7 the Triangle points down, if it sum to 6 it points up
            int orientation = localCoords[0] + localCoords[1] + localCoords[2];

            //if it points down we multiply the Movement Vectors by -1
            int sign = 1;
            if(orientation == 7){sign = -1;};

            //We now have X Y and Z
            int newX = lastTriangle.getGlobalCoords()[1] + (sign * moveVec.TrianglemovementVector_X);
            int newY = lastTriangle.getGlobalCoords()[2] + (sign * moveVec.TrianglemovementVector_Y);
            int newZ = lastTriangle.getGlobalCoords()[3] + (sign * moveVec.TrianglemovementVector_Z);

            int[] globalCoords = new int[]{boardIdx, newX, newY, newZ};

            //If the Coordinate is invalid it might indicate movement onto Squareboard
            if(!piece.game.validCoordinates(globalCoords)) return;

            //TODO: From Triangle to SquareBoard: Sum = 7 - Triangle points down and one axis is less than 0
            if(orientation == 7 && newX < 0 || newY < 0 || newZ < 0){
                System.out.println("Possible move from Triangle");
                //What SquareBoard
                if(newX < 0){
                    System.out.println("to Top SquareBoard");
                    //Top SquareBoard
                    boardIdx = 1;
                }
                if(newY < 0){
                    System.out.println("to Left SquareBoard");
                    //Left SquareBoard
                    boardIdx = 0;
                }
                if(newZ < 0){
                    System.out.println("to Right SquareBoard");
                    //Right SquareBoard
                    boardIdx = 2;
                }
                
            }
            
            AbstractField targetField = piece.game.getField(globalCoords);
    
            //Collision with own figure
            if (targetField.piece != null && targetField.piece.player == piece.player){return;}
    
            //If Square is empty or contains enemy piece
            if(targetField.piece == null){
                if(moveVec.takeEnemy!=movementVector.takeEnemyPiece.REQUIRED){
                    validMoves.add((Triangle)targetField);
                }
                recursiveDirectionCheck(validMoves, piece, targetField,moveVec,stepCounter+1);
            }else if (targetField.piece.player != piece.player) {
                // check if enemy piece can be taken
                if(moveVec.takeEnemy!=movementVector.takeEnemyPiece.IMPOSSIBLE){
                    validMoves.add((Triangle)targetField);
                }
            }
            
        } else
        {
            //System.out.println("Piece is on Square");
            //Is Square
            if(!(_lastField instanceof Square))return;
            Square lastSquare = (Square)_lastField;
    
            //TODO: Check if Dead Code
            if(lastSquare==null){
                System.err.println("ERROR: Square should not be null!");
                return;
            }

            //If no steps are left
            if(moveVec.maxSteps<=stepCounter) return;

            int boardIdx = lastSquare.getGlobalCoords()[0];
            int newX = lastSquare.getGlobalCoords()[1]+moveVec.movementVector_X;
            int newY = lastSquare.getGlobalCoords()[2] +moveVec.movementVector_Y;
            
            int[] globalCoords = new int[]{boardIdx, newX, newY};

            int dim_y = lastSquare.getBoard().getdim_y();
            int dim_x = lastSquare.getBoard().getdim_x();

            //TODO: Edge Case:
            //when for example knight jumps from the corner of one Square board through the triangle board to another square board. 
            //This should be processed in the TriangleBoard.
            if(newY >= dim_y){
                int newZ;
                if(newX >= dim_x){
                    //System.out.println("Move jumps through Triangle.");
                }
                //Need to add a Z coordinate for the Triangle Board
                //We can use the row information and the Triangle Structure for this.
                //We know what Squareboard we jump from, thus we know what side of the Triangle.
                switch (boardIdx) {
                    case 0:
                        //Left SquareBoard
                        //Stays the same
                        //Flipped down always sum to 7 so Y is the rest to 7
                        newZ = 7 - newX;
                        //Z is 0
                        newY = 0;
                        //Top Left is is 007
                        //Bottom is 700
                        //Y is always Zero
                        break;
                    case 1:
                        //Top SquareBoard -- 033 zu 043 -- 051
                        newY = 7 - newX;
                        newZ = newX;
                        newX = 0;
                        //Top right is X0 for Square and Triangle Corner 070
                        //Top left is X7 for Square and Triangle Corner 007
                        //X is always Zero
                        break;
                    case 2:
                        //Right SquareBoard 
                        newY = newX;
                        newX = 7 - newX;
                        newZ = 0;
                        //Top right is 070
                        //Bottom is 700
                        //Z is always zero
                        break;
                    default:
                        //TODO: Add Exception
                        newZ = 0;
                        break;
                }
                //Change to TriangleBoard
                boardIdx = 3;
                
                globalCoords = new int[]{boardIdx, newX, newY, newZ};
                System.out.println("New Coords: " + Arrays.toString(globalCoords));
            }
            /*
            if(moveVec.maxSteps<=stepCounter || !piece.game.validCoordinates(globalCoords)){
                //if this is false then we need to check if the Coordinates would lead to another Board.
                //Since we know that this is a Square board we could integrate a check that checks the Squareboards dim?
                return;
            }
            */
            if(!piece.game.validCoordinates(globalCoords)) return;
            
            AbstractField targetField = piece.game.getField(globalCoords);

            //Collision with own figure
            if (targetField.piece != null && targetField.piece.player == piece.player){return;}
    
            
            //If Square is empty or contains enemy piece
            if(targetField.piece == null){
                if(moveVec.takeEnemy!=movementVector.takeEnemyPiece.REQUIRED){        
                    //Do i need to cast to Square?
                    //System.out.println("Piece: Add target Field");
                    validMoves.add(targetField);
                }
                recursiveDirectionCheck(validMoves, piece, targetField,moveVec,stepCounter+1);
            }else if (targetField.piece.player != piece.player) {
                // check if enemy piece can be taken
                if(moveVec.takeEnemy!=movementVector.takeEnemyPiece.IMPOSSIBLE){        
                    validMoves.add(targetField);
                }
            }
        }
    }
}
