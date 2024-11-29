package jchess;

import java.util.ArrayList;


public class QueenMovement implements IMovement{

    private IMovement rookMovement;
    private IMovement bishopMovement;

    public QueenMovement(){
        this.rookMovement = new RookMovement();
        this.bishopMovement = new BishopMovement();
    }

    @Override
    public ArrayList calculateMoves(Piece piece)
    {

        ArrayList validMoves = new ArrayList<>();

        validMoves.addAll(rookMovement.calculateMoves(piece));
        validMoves.addAll(bishopMovement.calculateMoves(piece));
        
    return validMoves;
    }
}

