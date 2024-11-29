package jchess;

import java.util.ArrayList;

public class BishopMovement implements IMovement{

    @Override
    public ArrayList calculateMoves(Piece piece) {
        
        int range = 7;

        return this.calculateMoves(piece, range);
    }

    
    public ArrayList calculateMoves(Piece piece, int range) {
        
        ArrayList validMoves = new ArrayList<>();

        int x = piece.square.pozX;
        int y = piece.square.pozY;

        for (int h = x - 1, i = y + 1; !piece.isout(h, i); --h, ++i) //left-up
        {
            if (piece.checkPiece(h, i)) //if on piece sqhuare isn't piece
            {
                if (piece.player.color == Player.colors.white) //white
                {
                    if (piece.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(piece.square, piece.chessboard.squares[h][i]))
                    {
                        validMoves.add(piece.chessboard.squares[h][i]);
                    }
                }
                else //or black
                {
                    if (piece.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(piece.square, piece.chessboard.squares[h][i]))
                    {
                        validMoves.add(piece.chessboard.squares[h][i]);
                    }
                }

                if (piece.otherOwner(h, i))
                {
                    break;
                }
            }
            else
            {
                break;//we've to break becouse we cannot go beside other piece!!
            }
        }

        for (int h = x - 1, i = y - 1; !piece.isout(h, i); --h, --i) //left-down
        {
            if (piece.checkPiece(h, i)) //if on piece sqhuare isn't piece
            {
                if (piece.player.color == Player.colors.white) //white
                {
                    if (piece.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(piece.square, piece.chessboard.squares[h][i]))
                    {
                        validMoves.add(piece.chessboard.squares[h][i]);
                    }
                }
                else //or black
                {
                    if (piece.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(piece.square, piece.chessboard.squares[h][i]))
                    {
                        validMoves.add(piece.chessboard.squares[h][i]);
                    }
                }

                if (piece.otherOwner(h, i))
                {
                    break;
                }
            }
            else
            {
                break;//we've to break becouse we cannot go beside other piece!!
            }
        }

        for (int h = x + 1, i = y + 1; !piece.isout(h, i); ++h, ++i) //right-up
        {
            if (piece.checkPiece(h, i)) //if on piece sqhuare isn't piece
            {
                if (piece.player.color == Player.colors.white) //white
                {
                    if (piece.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(piece.square, piece.chessboard.squares[h][i]))
                    {
                        validMoves.add(piece.chessboard.squares[h][i]);
                    }
                }
                else //or black
                {
                    if (piece.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(piece.square, piece.chessboard.squares[h][i]))
                    {
                        validMoves.add(piece.chessboard.squares[h][i]);
                    }
                }

                if (piece.otherOwner(h, i))
                {
                    break;
                }
            }
            else
            {
                break;//we've to break becouse we cannot go beside other piece!!
            }
        }

        for (int h = x + 1, i = y - 1; !piece.isout(h, i); ++h, --i) //right-down
        {
            if (piece.checkPiece(h, i)) //if on piece sqhuare isn't piece
            {
                if (piece.player.color == Player.colors.white) //white
                {
                    if (piece.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(piece.square, piece.chessboard.squares[h][i]))
                    {
                        validMoves.add(piece.chessboard.squares[h][i]);
                    }
                }
                else //or black
                {
                    if (piece.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(piece.square, piece.chessboard.squares[h][i]))
                    {
                        validMoves.add(piece.chessboard.squares[h][i]);
                    }
                }

                if (piece.otherOwner(h, i))
                {
                    break;
                }
            }
            else
            {
                break;//we've to break becouse we cannot go beside other piece!!
            }
        }

        return validMoves;
    }

}
