package jchess.backend.pieces;

import java.util.ArrayList;

import jchess.backend.AbstractGame;
import jchess.backend.Player;
import jchess.backend.gamestate.Square;

public class Pawn extends Piece
{
    public Pawn(AbstractGame game, Player player)
    {
        super(game, player,Piece.typesOfPieces.Pawn);
        this.symbol = "P";

        // this.movement = new PawnMovement();
        this.movementOptions.add(new movementVector(0,1,1,movementVector.takeEnemyPiece.IMPOSSIBLE));
        this.movementOptions.add(new movementVector(-1,1,1,movementVector.takeEnemyPiece.REQUIRED));
        this.movementOptions.add(new movementVector(1,1,1,movementVector.takeEnemyPiece.REQUIRED));

        this.movementOptions.add(new movementVector(0,-1,1,movementVector.takeEnemyPiece.IMPOSSIBLE));
        this.movementOptions.add(new movementVector(-1,-1,1,movementVector.takeEnemyPiece.REQUIRED));
        this.movementOptions.add(new movementVector(1,-1,1,movementVector.takeEnemyPiece.REQUIRED));

        //ADD Special moves
        // Add En Passant

        // Maybe remove pawns ability to move backwards. 
        // That would make it impossible to move between enemy chessboards
    }

    

    // @Override
    // public void specialMoves(ArrayList<Square> validMoves){
    //     if(!(this.field instanceof Square)) return;
    //     Square curPos = (Square) this.field;
    //     if(!this.wasMoved){
    //         int[] coords = new int[]{curPos.coords[0],curPos.coords[1]+1};
    //         if(this.game.getField(coords).piece==null){
    //             movementVector specialMove = new movementVector(0,2,1,movementVector.takeEnemyPiece.IMPOSSIBLE);
    //             recursiveDirectionCheck(validMoves, this, this.field, specialMove,0);
    //         }
    //         int[] coords2 = new int[]{curPos.coords[0],curPos.coords[1]-1};
    //         if(this.game.getField(coords2).piece==null){
    //             movementVector specialMove = new movementVector(0,-2,1,movementVector.takeEnemyPiece.IMPOSSIBLE);
    //             recursiveDirectionCheck(validMoves, this, this.field, specialMove,0);
    //         }
    //     }
    // }

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
