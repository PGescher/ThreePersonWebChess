package com.webapp.jchess.model.gamestate;

import com.webapp.jchess.model.pieces.Piece;

public abstract class AbstractField {
    public Piece piece = null;
    public AbstractBoard board = null;
    protected int[] localCoords;
    protected int[] globalCoords;

    AbstractField(Piece piece, AbstractBoard board){
        this.piece = piece;
        this.board = board;
    }

    public int[] getGlobalCoords() {
        return globalCoords;
    }
    
    public int[] getLocalCoords() {
        return localCoords;
    }

    public void setPiece(Piece piece)
    {
        this.piece = piece;
        this.piece.field = this;
    }

    public abstract int[] addVec(abstractVector vec);

    public abstract AbstractBoard getBoard();
}
