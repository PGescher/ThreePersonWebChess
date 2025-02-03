package com.webapp.jchess.model.pieces;

import com.webapp.jchess.model.AbstractGame;
import com.webapp.jchess.model.Player;

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
