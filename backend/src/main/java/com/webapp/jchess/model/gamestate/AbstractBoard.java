package com.webapp.jchess.model.gamestate;

import com.webapp.jchess.model.AbstractGame;


public abstract class AbstractBoard {
    AbstractGame game;
    int boardIdx;

    protected AbstractBoard(AbstractGame game, int idx){
        this.game = game;
        this.boardIdx = idx;
    }

    abstract public boolean validCoordinates(int[] localCoords);
}