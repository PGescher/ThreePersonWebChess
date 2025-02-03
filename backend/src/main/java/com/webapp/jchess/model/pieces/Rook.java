package com.webapp.jchess.model.pieces;

import com.webapp.jchess.model.AbstractGame;
import com.webapp.jchess.model.Player;
import com.webapp.jchess.model.gamestate.*;
import com.webapp.jchess.model.gamestate.abstractVector.takeEnemyPiece;

public class Rook extends Piece
{

    public Rook(AbstractGame game, Player player)
    {
        super(game, player,Piece.typesOfPieces.Rook);//call initializer of super type: Piece
        this.symbol = "R";

        this.movementOptions.add(new squareVector(1,0,7,squareVector.takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new squareVector(-1,0,7,squareVector.takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new squareVector(0,1,7,squareVector.takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new squareVector(0,-1,7,squareVector.takeEnemyPiece.POSSIBLE));

        //Triangle horizontal and  vertical
        triangleVector triVec = new triangleVector(0,0,1,-1,0,0,15,takeEnemyPiece.POSSIBLE);
        this.movementOptions.add(triVec);
        this.movementOptions.add(triVec.invert());

        triVec = new triangleVector(0,1,0,-1,0,0,15,takeEnemyPiece.POSSIBLE);
        this.movementOptions.add(triVec);
        this.movementOptions.add(triVec.invert());

        triVec = new triangleVector(0,1,0,0,0,-1,15,takeEnemyPiece.POSSIBLE);
        this.movementOptions.add(triVec);
        this.movementOptions.add(triVec.invert());
    }

}
