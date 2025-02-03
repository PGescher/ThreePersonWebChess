package com.webapp.jchess.model.gamestate;

public abstract class abstractVector {
    public enum takeEnemyPiece{
        REQUIRED,
        POSSIBLE,
        IMPOSSIBLE
    }

    public int maxSteps;
    public takeEnemyPiece takeEnemy;

    abstractVector(int _maxSteps, takeEnemyPiece _takePiece){
        this.maxSteps = _maxSteps;
        this.takeEnemy = _takePiece;
    }
}
