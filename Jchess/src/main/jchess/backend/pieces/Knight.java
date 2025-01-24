package jchess.backend.pieces;

import jchess.backend.AbstractGame;
import jchess.backend.Player;

public class Knight extends Piece
{
    public Knight(AbstractGame game, Player player)
    {
        super(game, player,Piece.typesOfPieces.Knight);//call initializer of super type: Piece
        this.symbol = "N";

        this.movementOptions.add(new movementVector(2,1,1,movementVector.takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new movementVector(2,-1,1,movementVector.takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new movementVector(-2,1,1,movementVector.takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new movementVector(-2,-1,1,movementVector.takeEnemyPiece.POSSIBLE));

        this.movementOptions.add(new movementVector(1,2,1,movementVector.takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new movementVector(1,-2,1,movementVector.takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new movementVector(-1,2,1,movementVector.takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new movementVector(-1,-2,1,movementVector.takeEnemyPiece.POSSIBLE));
    }
}
