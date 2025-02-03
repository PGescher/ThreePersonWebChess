package com.webapp.jchess.model.gamestate;



public class triangleVector extends abstractVector{
    

    public int Up_X;
    public int Up_Y;
    public int Up_Z;

    public int Down_X;
    public int Down_Y;
    public int Down_Z;
    

    public triangleVector(int _xUP, int _yUP, int _zUP, int _xDOWN, int _yDOWN, int _zDOWN, int _maxSteps, takeEnemyPiece _takeEnemy){
        
        super(_maxSteps,_takeEnemy);

        this.Up_X=_xUP;
        this.Up_Y=_yUP;
        this.Up_Z = _zUP;

        this.Down_X=_xDOWN;
        this.Down_Y=_yDOWN;
        this.Down_Z=_zDOWN;
    }

    public triangleVector invert(){
        return new triangleVector(-Down_X, -Down_Y, -Down_Z, -Up_X, -Up_Y, -Up_Z, maxSteps, takeEnemy);
    }

    //Allow for special moves like casteling, en passant, ...
}
