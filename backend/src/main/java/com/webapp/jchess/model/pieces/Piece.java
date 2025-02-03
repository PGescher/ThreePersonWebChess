package com.webapp.jchess.model.pieces;
import java.util.ArrayList;
import java.util.Arrays;

import com.webapp.jchess.model.AbstractGame;
import com.webapp.jchess.model.Player;
import com.webapp.jchess.model.gamestate.*;



/** 
 * Abstract Piece Class
 * 
 * - Provides the central interaface other backend logic interacts with
 * - Uses the movement directions and special moves given by specific pieces to calculate their possible moves.
 * 
*/

public abstract class Piece
{
    static boolean debugOutput = false;

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
    protected ArrayList<abstractVector> movementOptions;

    public Piece(AbstractGame game, Player player, typesOfPieces _pieceType)
    {
        this.game = game;
        this.player = player;
        this.pieceType = _pieceType;

        movementOptions = new ArrayList<>();
    }

    public void specialMoves(ArrayList<AbstractField> validMoves){};

    public ArrayList<AbstractField> possibleMoves(){
        ArrayList<AbstractField> allMoves = new ArrayList<AbstractField>();
        
        for(abstractVector moveVec: movementOptions){
            recursiveDirectionCheck(allMoves, this, this.field, moveVec,0);
        }

        this.specialMoves(allMoves);

        return allMoves;
    }

    public int [] getGlobalCoords(){
        return this.field.getGlobalCoords();
    }
    public ArrayList<AbstractField> validMoves()
    {
        debugOutput = true;
        ArrayList<AbstractField> allMoves = possibleMoves();
        debugOutput = false;

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
    AbstractField _lastField,abstractVector moveVec,int stepCounter) {

        boolean continueRecursion = false;

        //if no steps are left
        if(moveVec.maxSteps<=stepCounter) return;

        // if(debugOutput) {System.out.println("##### Recursion");}

        // if(debugOutput) {System.out.println("From: "+ Arrays.toString(_lastField.getGlobalCoords()));}
        int[] globalCoords = _lastField.addVec(moveVec);
        if(globalCoords==null){
            // Incompatible field and vector
            // if(debugOutput) {System.out.println("Coords are null");}
            return;
        }
        // if(debugOutput) {System.out.println("Target: "+ Arrays.toString(globalCoords));}

        AbstractGame.debugOutput = debugOutput;
        AbstractField targetField = piece.game.getField(globalCoords);
        AbstractGame.debugOutput = false;
        //Field doesnt exist.
        if(targetField==null) {
            // if(debugOutput) {System.out.println("Field doesnt exist");}
            return;
        }
        
 
        //Collision with own figure
        if (targetField.piece != null && targetField.piece.player == piece.player){return;}
    
        //If Field is empty or contains enemy piece
        if(targetField.piece == null){
            if(moveVec.takeEnemy!=squareVector.takeEnemyPiece.REQUIRED){
                validMoves.add(targetField);
            }
            continueRecursion = true;
            
        }else if (targetField.piece.player != piece.player) {
            // check if enemy piece can be taken
            if(moveVec.takeEnemy!=squareVector.takeEnemyPiece.IMPOSSIBLE){
                validMoves.add(targetField);
            }
        }

        if (continueRecursion) {
            
            if (_lastField.getGlobalCoords()[0] == targetField.getGlobalCoords()[0]) {
                //We stay on the same board
                recursiveDirectionCheck(validMoves, piece, targetField,moveVec,stepCounter+1);
            }else{
                // for(abstractVector testVec: piece.movementOptions){
                //     int[] tmpCoords = targetField.addVec(testVec);
                //     if(tmpCoords==null)continue;
                //     if(tmpCoords[0] != _lastField.getGlobalCoords()[0]){
                //         //if the vector doesn immediatly move us back to the original board
                //         recursiveDirectionCheck(validMoves, piece, targetField,testVec,stepCounter+1);
                //     }
                // }
            }
            
        }


    }
}
