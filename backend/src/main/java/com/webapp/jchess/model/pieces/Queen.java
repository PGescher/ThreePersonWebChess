package com.webapp.jchess.model.pieces;

import com.webapp.jchess.model.AbstractGame;
import com.webapp.jchess.model.Player;
import com.webapp.jchess.model.gamestate.squareVector;
import com.webapp.jchess.model.gamestate.triangleVector;
import com.webapp.jchess.model.gamestate.abstractVector.takeEnemyPiece;

public class Queen extends Piece
{

    public Queen(AbstractGame game, Player player)
    {
        super(game, player,Piece.typesOfPieces.Queen);//call initializer of super type: Piece
        this.symbol = "Q";

        //Dont couple this with rook or bishop.
        //Otherwise changing the classes becomes more complicated
        this.movementOptions.add(new squareVector(1,1,7,takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new squareVector(1,-1,7,takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new squareVector(-1,1,7,takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new squareVector(-1,-1,7,takeEnemyPiece.POSSIBLE));

        this.movementOptions.add(new squareVector(1,0,7,takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new squareVector(-1,0,7,takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new squareVector(0,1,7,takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new squareVector(0,-1,7,takeEnemyPiece.POSSIBLE));


        //Triangle horizontal and vertical
        triangleVector triVec = new triangleVector(0,0,1,-1,0,0,15,takeEnemyPiece.POSSIBLE);
        this.movementOptions.add(triVec);
        this.movementOptions.add(triVec.invert());

        triVec = new triangleVector(0,1,0,-1,0,0,15,takeEnemyPiece.POSSIBLE);
        this.movementOptions.add(triVec);
        this.movementOptions.add(triVec.invert());

        triVec = new triangleVector(0,1,0,0,0,-1,15,takeEnemyPiece.POSSIBLE);
        this.movementOptions.add(triVec);
        this.movementOptions.add(triVec.invert());

        //Triangle diagonal
        triVec = new triangleVector(-1,1,1,-1,0,0,15,takeEnemyPiece.POSSIBLE);
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