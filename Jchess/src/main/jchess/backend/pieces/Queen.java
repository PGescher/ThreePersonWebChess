package jchess.backend.pieces;

import jchess.backend.AbstractGame;
import jchess.backend.Player;
import jchess.backend.pieces.movementVector.takeEnemyPiece;

public class Queen extends Piece
{
    public Queen(AbstractGame game, Player player)
    {
        super(game, player,Piece.typesOfPieces.Queen);//call initializer of super type: Piece
        this.symbol = "Q";

        //Dont couple this with rook or bishop.
        //Otherwise changing the classes becomes more complicated
        this.movementOptions.add(new movementVector(1,1,7,takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new movementVector(1,-1,7,takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new movementVector(-1,1,7,takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new movementVector(-1,-1,7,takeEnemyPiece.POSSIBLE));

        this.movementOptions.add(new movementVector(1,0,7,takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new movementVector(-1,0,7,takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new movementVector(0,1,7,takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new movementVector(0,-1,7,takeEnemyPiece.POSSIBLE));
    }
}