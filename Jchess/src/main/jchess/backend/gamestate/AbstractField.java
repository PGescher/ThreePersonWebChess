package jchess.backend.gamestate;

import jchess.backend.pieces.Piece;

public class AbstractField {
    public Piece piece = null;
    protected int[] localCoords;
    protected int[] globalCoords;
    

    AbstractField(Piece piece){
        this.piece = piece;
    }

    public int[] getGlobalCoords() {
        return globalCoords;
    }

    public int[] getLocalCoords() {
        return localCoords;
    }

    void setPiece(Piece piece)
    {
        this.piece = piece;
        this.piece.field = this;
    }
}
