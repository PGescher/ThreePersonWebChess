package jchess.backend.gamestate;
import jchess.backend.pieces.Piece;

public class Square extends AbstractField
{
    
    Square(int boardIdx, int pozX, int pozY, Piece piece)
    {
        super(piece);
        this.localCoords = new int[]{pozX, pozY};
        this.globalCoords = new int[]{boardIdx, pozX, pozY};

    }/*--endOf-Square--*/


    Square(Square square)
    {
        super(square.piece);
        this.localCoords = square.getLocalCoords();
    }

    public Square clone(Square square)
    {
        return new Square(square);
    }

    
}
