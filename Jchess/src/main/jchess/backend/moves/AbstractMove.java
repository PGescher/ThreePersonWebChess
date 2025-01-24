package jchess.backend.moves;

public abstract class AbstractMove {
    public enum specialMoves{
        none, shortCastling, longCastling, pawnTwoFields, enPassant
    }


    public abstract void execute_move();

    public abstract void undo_move();

    public abstract String toString();
}
