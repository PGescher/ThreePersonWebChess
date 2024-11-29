package jchess;

import java.util.ArrayList;

public class PawnMovement implements IMovement{

    @Override
    public ArrayList calculateMoves(Piece piece) {

        //System.out.println(piece.player.goDown);//4test
        ArrayList list = new ArrayList();
        Square sq;
        Square sq1;
        int first = piece.square.pozY - 1;//number where to move
        int second = piece.square.pozY - 2;//number where to move (only in first move)
        if (piece.player.goDown)
        {//check if player "go" down or up
            first = piece.square.pozY + 1;//if yes, change value
            second = piece.square.pozY + 2;//if yes, change value
        }
        if (piece.isout(first, first))
        {//out of bounds protection
            return list;//return empty list
        }
        sq = piece.chessboard.squares[piece.square.pozX][first];
        if (sq.piece == null)
        {//if next is free
            //list.add(sq);//add
            if (piece.player.color == Player.colors.white)
            {//white

                if (piece.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(piece.square, piece.chessboard.squares[piece.square.pozX][first]))
                {
                    list.add(piece.chessboard.squares[piece.square.pozX][first]);
                }
            }
            else
            {//or black

                if (piece.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(piece.square, piece.chessboard.squares[piece.square.pozX][first]))
                {
                    list.add(piece.chessboard.squares[piece.square.pozX][first]);
                }
            }

            if ((piece.player.goDown && piece.square.pozY == 1) || (!piece.player.goDown && piece.square.pozY == 6))
            {
                sq1 = piece.chessboard.squares[piece.square.pozX][second];
                if (sq1.piece == null)
                {
                    //list.add(sq1);//only in first move
                    if (piece.player.color == Player.colors.white)
                    {//white

                        if (piece.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(piece.square, piece.chessboard.squares[piece.square.pozX][second]))
                        {
                            list.add(piece.chessboard.squares[piece.square.pozX][second]);
                        }
                    }
                    else
                    {//or black

                        if (piece.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(piece.square, piece.chessboard.squares[piece.square.pozX][second]))
                        {
                            list.add(piece.chessboard.squares[piece.square.pozX][second]);
                        }
                    }
                }
            }
        }
        if (!piece.isout(piece.square.pozX - 1, piece.square.pozY)) //out of bounds protection
        {
            //capture
            sq = piece.chessboard.squares[piece.square.pozX - 1][first];
            if (sq.piece != null)
            {//check if can hit left
                if (piece.player != sq.piece.player && !sq.piece.name.equals("King"))
                {
                    //list.add(sq);
                    if (piece.player.color == Player.colors.white)
                    {//white

                        if (piece.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(piece.square, piece.chessboard.squares[piece.square.pozX - 1][first]))
                        {
                            list.add(piece.chessboard.squares[piece.square.pozX - 1][first]);
                        }
                    }
                    else
                    {//or black

                        if (piece.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(piece.square, piece.chessboard.squares[piece.square.pozX - 1][first]))
                        {
                            list.add(piece.chessboard.squares[piece.square.pozX - 1][first]);
                        }
                    }
                }
            }

            //En passant
            sq = piece.chessboard.squares[piece.square.pozX - 1][piece.square.pozY];
            if (sq.piece != null
                    && piece.chessboard.twoSquareMovedPawn != null
                    && sq == piece.chessboard.twoSquareMovedPawn.square)
            {//check if can hit left
                if (piece.player != sq.piece.player && !sq.piece.name.equals("King"))
                {// unnecessary

                    //list.add(sq);
                    if (piece.player.color == Player.colors.white)
                    {//white

                        if (piece.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(piece.square, piece.chessboard.squares[piece.square.pozX - 1][first]))
                        {
                            list.add(piece.chessboard.squares[piece.square.pozX - 1][first]);
                        }
                    }
                    else
                    {//or black

                        if (piece.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(piece.square, piece.chessboard.squares[piece.square.pozX - 1][first]))
                        {
                            list.add(piece.chessboard.squares[piece.square.pozX - 1][first]);
                        }
                    }
                }
            }
        }
        if (!piece.isout(piece.square.pozX + 1, piece.square.pozY))
        {//out of bounds protection

            //capture
            sq = piece.chessboard.squares[piece.square.pozX + 1][first];
            if (sq.piece != null)
            {//check if can hit right
                if (piece.player != sq.piece.player && !sq.piece.name.equals("King"))
                {
                    //list.add(sq);
                    if (piece.player.color == Player.colors.white)
                    { //white

                        if (piece.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(piece.square, piece.chessboard.squares[piece.square.pozX + 1][first]))
                        {
                            list.add(piece.chessboard.squares[piece.square.pozX + 1][first]);
                        }
                    }
                    else
                    {//or black

                        if (piece.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(piece.square, piece.chessboard.squares[piece.square.pozX + 1][first]))
                        {
                            list.add(piece.chessboard.squares[piece.square.pozX + 1][first]);
                        }
                    }
                }
            }

            //En passant
            sq = piece.chessboard.squares[piece.square.pozX + 1][piece.square.pozY];
            if (sq.piece != null
                    && piece.chessboard.twoSquareMovedPawn != null
                    && sq == piece.chessboard.twoSquareMovedPawn.square)
            {//check if can hit left
                if (piece.player != sq.piece.player && !sq.piece.name.equals("King"))
                {// unnecessary

                    //list.add(sq);
                    if (piece.player.color == Player.colors.white)
                    {//white

                        if (piece.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(piece.square, piece.chessboard.squares[piece.square.pozX + 1][first]))
                        {
                            list.add(piece.chessboard.squares[piece.square.pozX + 1][first]);
                        }
                    }
                    else
                    {//or black

                        if (piece.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(piece.square, piece.chessboard.squares[piece.square.pozX + 1][first]))
                        {
                            list.add(piece.chessboard.squares[piece.square.pozX + 1][first]);
                        }
                    }
                }
            }
        }
        return list;
    }

}
