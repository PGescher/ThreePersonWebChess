package com.webapp.jchess.model.gamestate;
import com.webapp.jchess.model.pieces.Piece;

public class Square extends AbstractField
{
    
    Square(int boardIdx, int pozX, int pozY, Piece piece, SquareBoard board)
    {
        super(piece, board);
        this.localCoords = new int[]{pozX, pozY};
        this.globalCoords = new int[]{boardIdx, pozX, pozY};

    }/*--endOf-Square--*/


    Square(Square square)
    {
        super(square.piece, square.board);
        this.localCoords = square.getLocalCoords();
    }

    public Square clone(Square square)
    {
        return new Square(square);
    }

    @Override
    public SquareBoard getBoard() {
        return (SquareBoard)board;
    }

    @Override
    public int[] addVec(abstractVector vec){
        if(!(vec instanceof squareVector))return null;
        squareVector sqVec = (squareVector)vec;
        int[] retCoords = new int[]{
            this.getGlobalCoords()[0],
            this.getGlobalCoords()[1]+sqVec.movementVector_X,
            this.getGlobalCoords()[2]+sqVec.movementVector_Y};

        return retCoords;
    }
}
