package com.webapp.jchess.model.pieces;

import com.webapp.jchess.model.AbstractGame;
import com.webapp.jchess.model.Player;

public class Bishop extends Piece 
{

    public Bishop(AbstractGame game, Player player)
    {
        super(game, player, Piece.typesOfPieces.Bishop);      //call initializer of super type: Piece
        this.symbol = "B";

        this.movementOptions.add(new movementVector(1,1,7,movementVector.takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new movementVector(1,-1,7,movementVector.takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new movementVector(-1,1,7,movementVector.takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new movementVector(-1,-1,7,movementVector.takeEnemyPiece.POSSIBLE));
    }
}
