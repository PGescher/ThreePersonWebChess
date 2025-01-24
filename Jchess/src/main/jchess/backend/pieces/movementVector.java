package jchess.backend.pieces;

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
    
    movementVector(int _x, int _y , int _maxSteps, takeEnemyPiece _takeEnemy){
        this.movementVector_X=_x;
        this.movementVector_Y=_y;
        this.maxSteps=_maxSteps;
        this.takeEnemy=_takeEnemy;
    }

    //Allow for special moves like casteling, en passant, ... 
}
