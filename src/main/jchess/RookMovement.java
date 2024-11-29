package jchess;

import java.util.ArrayList;

public class RookMovement implements IMovement{

     @Override
    public ArrayList calculateMoves(Piece piece) {

        //TODO: Set Type, maybe <Square>

        ArrayList validMoves = new ArrayList<>();

        // Square 0 - 7
        int x = piece.square.pozX;
        int y = piece.square.pozY;

        for (int i = x - 1; i >= 0; --i)
        {//left

            if (piece.checkPiece(i, y))
            {//if on piece sqhuare isn't piece

                if (piece.player.color == Player.colors.white)
                {//white

                    if (piece.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(piece.square, piece.chessboard.squares[i][y]))
                    {
                        validMoves.add(piece.chessboard.squares[i][y]);
                    }
                }
                else
                {//or black

                    if (piece.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(piece.square, piece.chessboard.squares[i][y]))
                    {
                        validMoves.add(piece.chessboard.squares[i][y]);
                    }
                }
                // Check if there enemy piece on the square, cant move past enemy pieces.
                if (piece.otherOwner(i, y))
                {
                    break;
                }
            }
            else
            {
                break;//we've to break becouse we cannot go beside other piece!!
            }
        }

        for (int i = x + 1; i <= 7; ++i)
        {//right

            if (piece.checkPiece(i, y))
            {//if on piece sqhuare isn't piece

                if (piece.player.color == Player.colors.white)
                {//white

                    if (piece.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(piece.square, piece.chessboard.squares[i][y]))
                    {
                        validMoves.add(piece.chessboard.squares[i][y]);
                    }
                }
                else
                {//or black

                    if (piece.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(piece.square, piece.chessboard.squares[i][y]))
                    {
                        validMoves.add(piece.chessboard.squares[i][y]);
                    }
                }

                if (piece.otherOwner(i, y))
                {
                    break;
                }
            }
            else
            {
                break;//we've to break becouse we cannot go beside other piece!!
            }
        }

        for (int i = y + 1; i <= 7; ++i)
        {//up

            if (piece.checkPiece(x, i))
            {//if on piece sqhuare isn't piece

                if (piece.player.color == Player.colors.white)
                {//white

                    if (piece.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(piece.square, piece.chessboard.squares[x][i]))
                    {
                        validMoves.add(piece.chessboard.squares[x][i]);
                    }
                }
                else
                {//or black

                    if (piece.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(piece.square, piece.chessboard.squares[x][i]))
                    {
                        validMoves.add(piece.chessboard.squares[x][i]);
                    }
                }

                if (piece.otherOwner(x, i))
                {
                    break;
                }
            }
            else
            {
                break;//we've to break becouse we cannot go beside other piece!!
            }
        }
        for (int i = y - 1; i >= 0; --i)
            {//down

                if (piece.checkPiece(x, i))
                {//if on piece sqhuare isn't piece

                    if (piece.player.color == Player.colors.white)
                    {//white

                        if (piece.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(piece.square, piece.chessboard.squares[x][i]))
                        {
                            validMoves.add(piece.chessboard.squares[x][i]);
                        }
                    }
                    else
                    {//or black

                        if (piece.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(piece.square, piece.chessboard.squares[x][i]))
                        {
                            validMoves.add(piece.chessboard.squares[x][i]);
                        }
                    }

                    if (piece.otherOwner(x, i))
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
