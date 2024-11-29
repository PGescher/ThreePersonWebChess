package jchess;

import java.util.ArrayList;

public class KnigtMovement implements IMovement{

    public ArrayList movecalculation(Piece piece, int newX, int newY){

        ArrayList validMoves = new ArrayList<>();

        if (!piece.isout(newX, newY) && piece.checkPiece(newX, newY))
        {
            if (piece.player.color == Player.colors.white) //white
            {
                if (piece.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(piece.square, piece.chessboard.squares[newX][newY]))
                {
                    validMoves.add(piece.chessboard.squares[newX][newY]);
                }
            }
            else //or black
            {
                if (piece.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(piece.square, piece.chessboard.squares[newX][newY]))
                {
                    validMoves.add(piece.chessboard.squares[newX][newY]);
                }
            }
        }
        return validMoves;
    }

    @Override
    public ArrayList calculateMoves(Piece piece) {

        ArrayList validMoves = new ArrayList();

        // knight all moves
        //  _______________ Y:
        // |_|_|_|_|_|_|_|_|7
        // |_|_|_|_|_|_|_|_|6
        // |_|_|2|_|3|_|_|_|5
        // |_|1|_|_|_|4|_|_|4
        // |_|_|_|K|_|_|_|_|3
        // |_|8|_|_|_|5|_|_|2
        // |_|_|7|_|6|_|_|_|1
        // |_|_|_|_|_|_|_|_|0
        //X:0 1 2 3 4 5 6 7
        //

        int newX, newY;

        //1
        newX = piece.square.pozX - 2;
        newY = piece.square.pozY + 1;

        validMoves.addAll(movecalculation(piece,newX, newY));

        //2
        newX = piece.square.pozX - 1;
        newY = piece.square.pozY + 2;

        validMoves.addAll(movecalculation(piece,newX, newY));

        //3
        newX = piece.square.pozX + 1;
        newY = piece.square.pozY + 2;

        validMoves.addAll(movecalculation(piece,newX, newY));

        //4
        newX = piece.square.pozX + 2;
        newY = piece.square.pozY + 1;

        validMoves.addAll(movecalculation(piece,newX, newY));

        //5
        newX = piece.square.pozX + 2;
        newY = piece.square.pozY - 1;

        validMoves.addAll(movecalculation(piece,newX, newY));

        //6
        newX = piece.square.pozX + 1;
        newY = piece.square.pozY - 2;

        validMoves.addAll(movecalculation(piece,newX, newY));

        //7
        newX = piece.square.pozX - 1;
        newY = piece.square.pozY - 2;

        validMoves.addAll(movecalculation(piece,newX, newY));

        //8
        newX = piece.square.pozX - 2;
        newY = piece.square.pozY - 1;

        validMoves.addAll(movecalculation(piece,newX, newY));
    
        return validMoves;
    }
}
