package com.webapp.jchess.model.pieces;

import com.webapp.jchess.model.AbstractGame;
import com.webapp.jchess.model.Player;
import com.webapp.jchess.model.gamestate.squareVector;
import com.webapp.jchess.model.gamestate.triangleVector;

public class Knight extends Piece
{

    public Knight(AbstractGame game, Player player)
    {
        super(game, player,Piece.typesOfPieces.Knight);//call initializer of super type: Piece
        this.symbol = "N";

        this.movementOptions.add(new squareVector(2,1,1,squareVector.takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new squareVector(2,-1,1,squareVector.takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new squareVector(-2,1,1,squareVector.takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new squareVector(-2,-1,1,squareVector.takeEnemyPiece.POSSIBLE));

        this.movementOptions.add(new squareVector(1,2,1,squareVector.takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new squareVector(1,-2,1,squareVector.takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new squareVector(-1,2,1,squareVector.takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new squareVector(-1,-2,1,squareVector.takeEnemyPiece.POSSIBLE));

        triangleVector triVec = new triangleVector(-2,0,2,-2,0,2,1,squareVector.takeEnemyPiece.POSSIBLE);
        this.movementOptions.add(triVec);
        this.movementOptions.add(triVec.invert());

        triVec = new triangleVector(-2,2,0,-2,2,0,1,squareVector.takeEnemyPiece.POSSIBLE);
        this.movementOptions.add(triVec);
        this.movementOptions.add(triVec.invert());

        triVec = new triangleVector(0,-2,2,-0,-2,2,1,squareVector.takeEnemyPiece.POSSIBLE);
        this.movementOptions.add(triVec);
        this.movementOptions.add(triVec.invert());

    }
}
