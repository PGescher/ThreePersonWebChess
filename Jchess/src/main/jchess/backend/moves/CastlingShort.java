package jchess.backend.moves;

import jchess.backend.gamestate.Square;
import jchess.backend.pieces.Piece;

public class CastlingShort extends AbstractMove{

    StandardMove kingMove;
    StandardMove rookMove;

    CastlingShort(Piece _king, Piece _rook){
        int y = _king.field.localCoords[1];
        int x = _king.field.localCoords[0]+2;
        Square targetSquare = _king.game.getField(x, y);
        kingMove = new StandardMove(_king, targetSquare);
        Square targetSquareRook = _king.game.getField(x-1,y); 
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
        return "0-0";
    }
    
}
