package com.webapp.jchess.model.pieces;

public class movementVector {
    public enum takeEnemyPiece{
        REQUIRED,
        POSSIBLE,
        IMPOSSIBLE
    }

    public int movementVector_X;
    public int movementVector_Y;
    public int maxSteps;
    public takeEnemyPiece takeEnemy;

    public int TrianglemovementVector_X;
    public int TrianglemovementVector_Y;
    public int TrianglemovementVector_Z;
    
    movementVector(int _x, int _y , int _maxSteps, takeEnemyPiece _takeEnemy){
        this.movementVector_X=_x;
        this.movementVector_Y=_y;
        this.maxSteps=_maxSteps;
        this.takeEnemy=_takeEnemy;
        
        //TriangleMovement Vectors are 0 so the Piece does not Move?
        this.TrianglemovementVector_X = 0;
        this.TrianglemovementVector_Y = 0;
        this.TrianglemovementVector_Z = 0;
    }
    movementVector(int _x, int _y, int _z, int _maxSteps, takeEnemyPiece _takeEnemy){
        this.movementVector_X= 0;
        this.movementVector_Y= 0;

        this.TrianglemovementVector_X=_x;
        this.TrianglemovementVector_Y=_y;
        this.TrianglemovementVector_Z = _z;
        this.maxSteps=_maxSteps;
        this.takeEnemy=_takeEnemy;
    }

    //Allow for special moves like casteling, en passant, ...
}
