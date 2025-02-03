package com.webapp.jchess.model.gamestate;
import com.webapp.jchess.model.pieces.Piece;

/**
 * Class to represent a chessboard square
 */
public class Triangle extends AbstractField
{

    Triangle(int boardIdx, int pozX, int pozY,int pozZ, Piece piece, AbstractBoard board)
    {
        super(piece, board);
        this.localCoords = new int[]{pozX, pozY, pozZ};
        this.globalCoords = new int[]{boardIdx,pozX, pozY, pozZ};
    }/*--endOf-Triangle--*/


    Triangle(Triangle field)
    {
        super(field.piece, field.board);
        this.localCoords = field.localCoords;
    }

    public Triangle clone(Triangle square)
    {
        return new Triangle(square);
    }


    @Override
    public AbstractBoard getBoard() {
        return (TriangleBoard)board;
    }

    
}
