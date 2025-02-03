package com.webapp.jchess.model.pieces;

import com.webapp.jchess.model.AbstractGame;
import com.webapp.jchess.model.Player;
import com.webapp.jchess.model.gamestate.abstractVector.takeEnemyPiece;
import com.webapp.jchess.model.gamestate.squareVector;
import com.webapp.jchess.model.gamestate.triangleVector;

public class Bishop extends Piece 
{

    public Bishop(AbstractGame game, Player player)
    {
        super(game, player, Piece.typesOfPieces.Bishop);      //call initializer of super type: Piece
        this.symbol = "B";

        this.movementOptions.add(new squareVector(1,1,7,takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new squareVector(1,-1,7,takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new squareVector(-1,1,7,takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new squareVector(-1,-1,7,takeEnemyPiece.POSSIBLE));

        triangleVector triVec = new triangleVector(-1,1,1,-1,0,0,15,takeEnemyPiece.POSSIBLE);
        this.movementOptions.add(triVec);
        this.movementOptions.add(triVec.invert());

        triVec = new triangleVector(0,0,1,1,1,0,15,takeEnemyPiece.POSSIBLE);
        this.movementOptions.add(triVec);
        this.movementOptions.add(triVec.invert());

        triVec = new triangleVector(0,1,0,-1,1,-1,15,takeEnemyPiece.POSSIBLE);
        this.movementOptions.add(triVec);
        this.movementOptions.add(triVec.invert());
    }
}
