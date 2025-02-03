package com.webapp.jchess.model.pieces;

import com.webapp.jchess.model.AbstractGame;
import com.webapp.jchess.model.Player;

public class Rook extends Piece
{

    public Rook(AbstractGame game, Player player)
    {
        super(game, player,Piece.typesOfPieces.Rook);//call initializer of super type: Piece
        this.symbol = "R";

        this.movementOptions.add(new movementVector(1,0,7,movementVector.takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new movementVector(-1,0,7,movementVector.takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new movementVector(0,1,7,movementVector.takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new movementVector(0,-1,7,movementVector.takeEnemyPiece.POSSIBLE));
    }

}
