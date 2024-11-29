package jchess;

import java.util.ArrayList;

public class KingMovement implements IMovement{

    @Override
    public ArrayList calculateMoves(Piece piece){

        if (piece instanceof King) {
            return this.calculateKingMoves((King) piece);
        } else {
            System.err.println("Piece is not a King");
            return new ArrayList<>();
        }
    }

    public ArrayList calculateKingMoves(King piece) {

        ArrayList validMoves = new ArrayList<>();

        Square sq;
        Square sq1;
        for (int i = piece.square.pozX - 1; i <= piece.square.pozX + 1; i++)
        {
            for (int y = piece.square.pozY - 1; y <= piece.square.pozY + 1; y++)
            {
                if (!piece.isout(i, y))
                {//out of bounds protection
                    sq = piece.chessboard.squares[i][y];
                    if (piece.square == sq)
                    {//if we're checking square on which is King
                        continue;
                    }
                    if (piece.checkPiece(i, y))
                    {//if square is empty
                        if (piece.getisSafe(sq))
                        {
                            validMoves.add(sq);
                        }
                    }
                }
            }
        }

        if (!piece.wasMotion && !piece.isChecked())
        {//check if king was not moved before


            if (piece.chessboard.squares[0][piece.square.pozY].piece != null
                    && piece.chessboard.squares[0][piece.square.pozY].piece.name.equals("Rook"))
            {
                boolean canCastling = true;

                Rook rook = (Rook) piece.chessboard.squares[0][piece.square.pozY].piece;
                if (!rook.wasMotion)
                {
                    for (int i = piece.square.pozX - 1; i > 0; i--)
                    {//go left
                        if (piece.chessboard.squares[i][piece.square.pozY].piece != null)
                        {
                            canCastling = false;
                            break;
                        }
                    }
                    sq = piece.chessboard.squares[piece.square.pozX - 2][piece.square.pozY];
                    sq1 = piece.chessboard.squares[piece.square.pozX - 1][piece.square.pozY];
                    if (canCastling && piece.getisSafe(sq) && piece.getisSafe(sq1))
                    { //can do castling when none of Sq,sq1 is checked
                        validMoves.add(sq);
                    }
                }
            }
            if (piece.chessboard.squares[7][piece.square.pozY].piece != null
                    && piece.chessboard.squares[7][piece.square.pozY].piece.name.equals("Rook"))
            {
                boolean canCastling = true;
                Rook rook = (Rook) piece.chessboard.squares[7][piece.square.pozY].piece;
                if (!rook.wasMotion)
                {//if king was not moves before and is not checked
                    for (int i = piece.square.pozX + 1; i < 7; i++)
                    {//go right
                        if (piece.chessboard.squares[i][piece.square.pozY].piece != null)
                        {//if square is not empty
                            canCastling = false;//cannot castling
                            break; // exit
                        }
                    }
                    sq = piece.chessboard.squares[piece.square.pozX + 2][piece.square.pozY];
                    sq1 = piece.chessboard.squares[piece.square.pozX + 1][piece.square.pozY];
                    if (canCastling && piece.getisSafe(sq) && piece.getisSafe(sq1))
                    {//can do castling when none of Sq,sq1 is checked
                        validMoves.add(sq);
                    }
                }
            }
        }

        return validMoves;
    }

}
