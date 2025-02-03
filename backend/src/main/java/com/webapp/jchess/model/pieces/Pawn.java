package com.webapp.jchess.model.pieces;

import java.util.ArrayList;
import java.util.Arrays;

import com.webapp.jchess.model.AbstractGame;
import com.webapp.jchess.model.Player;
import com.webapp.jchess.model.gamestate.Square;
import com.webapp.jchess.model.gamestate.squareVector;
import com.webapp.jchess.model.gamestate.triangleVector;

public class Pawn extends Piece
{
    public Pawn(AbstractGame game, Player player)
    {
        super(game, player,Piece.typesOfPieces.Pawn);
        this.symbol = "P";

        // this.movement = new PawnMovement();
        //Forward Movement - Strike left and right diagonal forward
        this.movementOptions.add(new squareVector(0,1,1,squareVector.takeEnemyPiece.IMPOSSIBLE));
        this.movementOptions.add(new squareVector(-1,1,1,squareVector.takeEnemyPiece.REQUIRED));
        this.movementOptions.add(new squareVector(1,1,1,squareVector.takeEnemyPiece.REQUIRED));

        //Backward Movement - Strike left and right diagonal backwards
        this.movementOptions.add(new squareVector(0,-1,1,squareVector.takeEnemyPiece.IMPOSSIBLE));
        this.movementOptions.add(new squareVector(-1,-1,1,squareVector.takeEnemyPiece.REQUIRED));
        this.movementOptions.add(new squareVector(1,-1,1,squareVector.takeEnemyPiece.REQUIRED));

        /*
         * Triangle Movement -  Movement across the Edges, Striking along Corners - Depending on Orientation they are either added or subtracted
         */

        //X-Axis
        this.movementOptions.add(new triangleVector(1,0,0,-1,0,0,1,squareVector.takeEnemyPiece.IMPOSSIBLE));
        // this.movementOptions.add(new triangleVector(-1, 1, 1, 1, squareVector.takeEnemyPiece.REQUIRED));
        //Y-Axis
        this.movementOptions.add(new triangleVector(0,1,0,0,-1,0,1,squareVector.takeEnemyPiece.IMPOSSIBLE));
        // this.movementOptions.add(new triangleVector(1, -1, 1, 1, squareVector.takeEnemyPiece.REQUIRED));
        //Z-Axis
        this.movementOptions.add(new triangleVector(0,0,1,0,0,-1,1,squareVector.takeEnemyPiece.IMPOSSIBLE));
        // this.movementOptions.add(new triangleVector(1, 1, -1, 1, squareVector.takeEnemyPiece.REQUIRED));



        

        //ADD Special moves
        // Add En Passant

        // Maybe remove pawns ability to move backwards. 
        // That would make it impossible to move between enemy chessboards
    }

    
    /*
    @Override
    public void specialMoves(ArrayList<AbstractField> validMoves){
        if(!(this.field instanceof Square)) return;
        Square curPos = (Square) this.field;
        if(!this.wasMoved){
            int[] coords;
            if(curPos.boardid == -1){
                coords = new int[]{curPos.coords[0],curPos.coords[1]+1};
            }else {
                coords = new int[]{curPos.boardid, curPos.coords[0],curPos.coords[1]+1};
            }
            if(this.game.getField(coords).piece==null){
                movementVector specialMove = new movementVector(0,2,1,movementVector.takeEnemyPiece.IMPOSSIBLE);
                recursiveDirectionCheck(validMoves, this, this.field, specialMove,0);
            }
            int[] coords2;
            if(curPos.boardid == -1){
                coords2 = new int[]{curPos.coords[0],curPos.coords[1]-1};
            }else {
                coords2 = new int[]{curPos.boardid, curPos.coords[0],curPos.coords[1]-1};
            }
            if(this.game.getField(coords2).piece==null){
                movementVector specialMove = new movementVector(0,-2,1,movementVector.takeEnemyPiece.IMPOSSIBLE);
                recursiveDirectionCheck(validMoves, this, this.field, specialMove,0);
            }
        }
    }
    */

    public void promote(String newPiece,Square square)
    {
        Piece piece;
        switch (newPiece) {
            case "Queen":
                piece = new Queen(this.game, square.piece.player);
                break;
            case "Rook":
                piece = new Rook(this.game, square.piece.player);
                break;
            case "Bishop":
                piece = new Bishop(this.game, square.piece.player);
                break;
            default:
                piece = new Knight(this.game, square.piece.player);
                break;
        }
        this.game.activePieces.remove(this);
        piece.game = square.piece.game;
        piece.player = square.piece.player;
        piece.field = square.piece.field;
        square.piece = piece;
        this.game.activePieces.add(piece);
    }
}
