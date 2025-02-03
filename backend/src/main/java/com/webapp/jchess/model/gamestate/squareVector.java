package com.webapp.jchess.model.gamestate;


public class squareVector extends abstractVector{

    public int movementVector_X;
    public int movementVector_Y;

    public squareVector(int _x, int _y , int _maxSteps, takeEnemyPiece _takeEnemy){
        super(_maxSteps,_takeEnemy);
        
        this.movementVector_X=_x;
        this.movementVector_Y=_y;

    }

    //Allow for special moves like casteling, en passant, ...
}
