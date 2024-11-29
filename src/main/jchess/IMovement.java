package jchess;

import java.util.ArrayList;

public interface IMovement {

    ArrayList calculateMoves(Piece piece);

    //ArrayList calculateMoves(Piece piece, short movementRange, boolean biDirectional);
}
