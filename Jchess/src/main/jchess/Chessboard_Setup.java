package jchess;

import jchess.figures.Bishop;
import jchess.figures.King;
import jchess.figures.Knight;
import jchess.figures.Pawn;
import jchess.figures.Queen;
import jchess.figures.Rook;

public interface Chessboard_Setup {

/** Method setPieces on begin of new game or loaded game
     * @param places string with pieces to set on chessboard
     * @param plWhite reference to white player
     * @param plBlack reference to black player
     */
    public default void setPieces(String places, Player plWhite, Player plBlack, Chessboard board)
    {
        setPieces4NewGame( plWhite, plBlack,board);
        
    }/*--endOf-setPieces--*/


    /**
     *
     */
    private void setPieces4NewGame(Player plWhite, Player plBlack,Chessboard board)
    {

        /* WHITE PIECES */
        Player player = plBlack;
        Player player1 = plWhite;
        setFigures4NewGame(0, player,board);
        createPawnRow(1, player,board);
        setFigures4NewGame(7, player1,board);
        createPawnRow(6, player1,board);
    }/*--endOf-setPieces(boolean upsideDown)--*/


    /**  method set Figures in row (and set Queen and King to right position)
     *  @param row row where to set figures (Rook, Knight etc.)
     *  @param player which is owner of pawns
     *  @param upsideDown if true white pieces will be on top of chessboard
     * */
    private void setFigures4NewGame(int row, Player player,Chessboard board)
    {

        if (row != 0 && row != 7)
        {
            System.out.println("error setting figures like rook etc.");
            return;
        }
        else if (row == 0)
        {
            player.goDown = true;
        }

        board.squares[0][row].setPiece(new Rook(board, player));
        board.squares[7][row].setPiece(new Rook(board, player));
        board.squares[1][row].setPiece(new Knight(board, player));
        board.squares[6][row].setPiece(new Knight(board, player));
        board.squares[2][row].setPiece(new Bishop(board, player));
        board.squares[5][row].setPiece(new Bishop(board, player));
        
        board.squares[3][row].setPiece(new Queen(board, player));
        if (player.color == Player.colors.white)
        {
            board.squares[4][row].setPiece(board.kingWhite = new King(board, player));
        }
        else
        {
            board.squares[4][row].setPiece(board.kingBlack = new King(board, player));
        }
        for(int col=0;col<8;col++)
        {
            board.activePieces.add(board.squares[col][row].piece);
        }
        
    }

    /**  method set Pawns in row
     *  @param row row where to set pawns
     *  @param player player which is owner of pawns
     * */
    private void createPawnRow(int row, Player player,Chessboard board)
    {
        if(row<0 || row>=board.squares.length){
            System.out.println("Error setting pawns. Index out of bounds");
            return;
        }
        for (int col = 0; col < board.squares.length; col++)
        {
            board.squares[col][row].setPiece(new Pawn(board, player));
            board.activePieces.add(board.squares[col][row].piece);
        }
    }
}
