package com.webapp.jchess.model.moves;

import com.webapp.jchess.model.gamestate.Square;
import com.webapp.jchess.model.pieces.Piece;

public class CastlingLong extends AbstractMove{

    private StandardMove kingMove;
    private StandardMove rookMove;

    //TODO: Check the Coords and the getField
    CastlingLong(Piece _king, Piece _rook){
        int y = _king.field.getLocalCoords()[1];
        int x = _king.field.getLocalCoords()[0]+2;
        Square targetSquare = (Square) _king.game.getField(new int[]{x,y});
        kingMove = new StandardMove(_king, targetSquare);
        Square targetSquareRook = (Square) _king.game.getField(new int[]{x-1,y}); 
        rookMove = new StandardMove(_rook,targetSquareRook);
    }

    @Override
    public void execute_move() {
        rookMove.execute_move();
        kingMove.execute_move();
    }

    @Override
    public void undo_move() {
        rookMove.undo_move();
        kingMove.undo_move();
    }

    @Override
    public String toString() {
        return "0-0-0";
    }
    
}
