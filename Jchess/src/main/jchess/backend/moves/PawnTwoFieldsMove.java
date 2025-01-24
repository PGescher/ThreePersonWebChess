package jchess.backend.moves;

import jchess.backend.gamestate.Square;
import jchess.backend.pieces.Piece;

public class PawnTwoFieldsMove extends AbstractMove {

    private StandardMove pawnMove;

    PawnTwoFieldsMove(Piece _pawn, Square _to){
        this.pawnMove = new StandardMove(_pawn, _to);
    }

    @Override
    public void execute_move() {
        pawnMove.execute_move();
    }

    @Override
    public void undo_move() {
        pawnMove.undo_move();
    }

    @Override
    public String toString() {
        return pawnMove.toString();
    }
    
}
