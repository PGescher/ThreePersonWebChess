package jchess.backend.moves;

import jchess.backend.gamestate.Square;
import jchess.backend.pieces.Piece;

public class enPassant extends AbstractMove{


    private StandardMove activePawnMove;
    Piece takenPawn;
    Square takenSquare;

    enPassant(Piece _movedPawn, Piece _takenPawn, Square _to){
        this.activePawnMove = new StandardMove(_movedPawn, _to);

        this.takenPawn=_takenPawn;
        this.takenSquare = (Square) _takenPawn.field;
    }

    @Override
    public void execute_move() {
        //Remove Pawn
        this.takenPawn.field = null;
        this.takenSquare.piece = null;

        //Move active Pawn
        this.activePawnMove.execute_move();
    }

    @Override
    public void undo_move() {
        //Place removed pawn again
        this.takenPawn.field = this.takenSquare;
        this.takenSquare.piece = this.takenPawn;

        //Undo active pawn move
        this.activePawnMove.undo_move();
    }

    @Override
    public String toString() {
        String locMove = new String(activePawnMove.movedPiece.symbol);
        Square begin = activePawnMove.from;
        Square end = activePawnMove.to;
        locMove += Character.toString((char) (begin.localCoords[0] + 97));//add letter of Square from which move was made
        locMove += Integer.toString(8 - begin.localCoords[1]);//add number of Square from which move was made
        locMove += "-";//normal move
        locMove += Character.toString((char) (end.localCoords[0] + 97));//add letter of Square to which move was made
        locMove += Integer.toString(8 - end.localCoords[1]);//add number of Square to which move was made
        locMove += "(e.p)";//pawn take down opponent en passant

        return locMove;
    }
    
}
