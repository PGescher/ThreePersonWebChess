/*
#    This program is free software: you can redistribute it and/or modify
#    it under the terms of the GNU General Public License as published by
#    the Free Software Foundation, either version 3 of the License, or
#    (at your option) any later version.
#
#    This program is distributed in the hope that it will be useful,
#    but WITHOUT ANY WARRANTY; without even the implied warranty of
#    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#    GNU General Public License for more details.
#
#    You should have received a copy of the GNU General Public License
#    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * Authors:
 * Mateusz Sławomir Lach ( matlak, msl )
 * Damian Marciniak
 */
package jchess;

// import java.awt.*;
// import java.awt.image.BufferedImage;

// import java.util.Iterator;
// import javax.swing.JPanel;
import java.util.ArrayList;

import jchess.Moves.castling;
import jchess.figures.King;
import jchess.figures.Pawn;
import jchess.figures.Piece;
import jchess.figures.Rook;
import jchess.figures.movementVector;
import jchess.gui_java.Chessboard_JPanel;

/** Class to represent chessboard. Chessboard is made from squares.
 * It is setting the squers of chessboard and sets the pieces(pawns)
 * witch the owner is current player on it.
 */
public class Chessboard extends Chessboard_JPanel implements Chessboard_Setup
{

    public static final int top = 0;
    public static final int bottom = 7;
    public Square[][] squares = new Square[8][8];//squares of chessboard
    public ArrayList<Piece> activePieces = new ArrayList<Piece>();
    

    
    public King kingWhite;
    public King kingBlack;
    //-------- for undo ----------
    private Square undo1_sq_begin = null;
    private Square undo1_sq_end = null;
    private Piece undo1_piece_begin = null;
    private Piece undo1_piece_end = null;
    private Piece ifWasEnPassant = null;
    private Piece ifWasCastling = null;
    private boolean breakCastling = false; //if last move break castling
    //----------------------------
    //For En passant:
    //|-> Pawn whose in last turn moved two square
    public Pawn twoSquareMovedPawn = null;
    public Pawn twoSquareMovedPawn2 = null;
    private Moves moves_history;

    /** Chessboard class constructor
     * @param _settings reference to Settings class object for this chessboard
     * @param moves_history reference to Moves class object for this chessboard 
     */
    public Chessboard(Settings _settings, Moves moves_history)
    {
        super(_settings);

        for (int i = 0; i < 8; i++)
        {//create object for each square
            for (int y = 0; y < 8; y++)
            {
                this.squares[i][y] = new Square(i, y, null);
            }
        }//--endOf--create object for each square
        this.moves_history = moves_history;
        
        super.setSquares(squares);
    }/*--endOf-Chessboard--*/



    public void move(Square begin, Square end)
    {
        move(begin, end, true);
    }

    /** Method to move piece over chessboard
     * @param xFrom from which x move piece
     * @param yFrom from which y move piece
     * @param xTo to which x move piece
     * @param yTo to which y move piece
     */
    public void move(int xFrom, int yFrom, int xTo, int yTo)
    {
        Square fromSQ = null;
        Square toSQ = null;
        try
        {
            fromSQ = this.squares[xFrom][yFrom];
            toSQ = this.squares[xTo][yTo];
        }
        catch (java.lang.IndexOutOfBoundsException exc)
        {
            System.out.println("error moving piece: " + exc);
            return;
        }
        this.move(this.squares[xFrom][yFrom], this.squares[xTo][yTo], true);
    }

    public void move(Square begin, Square end, boolean refresh)
    {
        this.move(begin, end, refresh, true);
    }

    /** Method move piece from square to square
     * @param begin square from which move piece
     * @param end square where we want to move piece         *
     * @param refresh chessboard, default: true
     * */
    public void move(Square begin, Square end, boolean refresh, boolean clearForwardHistory)
    {

        castling wasCastling = Moves.castling.none;
        Piece promotedPiece = null;
        boolean wasEnPassant = false;
        if (end.piece != null)
        {
            end.piece.square = null;
        }

        Square tempBegin = new Square(begin);//4 moves history
        Square tempEnd = new Square(end);  //4 moves history
        //for undo
        undo1_piece_begin = begin.piece;
        undo1_sq_begin = begin;
        undo1_piece_end = end.piece;
        undo1_sq_end = end;
        ifWasEnPassant = null;
        ifWasCastling = null;
        breakCastling = false;
        // ---

        twoSquareMovedPawn2 = twoSquareMovedPawn;

        begin.piece.square = end;//set square of piece to ending
        end.piece = begin.piece;//for ending square set piece from beginin square
        begin.piece = null;//make null piece for begining square

        if (end.piece.name.equals("King"))
        {
            if (!((King) end.piece).wasMoved)
            {
                breakCastling = true;
                ((King) end.piece).wasMoved = true;
            }

            //Castling
            if (begin.pozX + 2 == end.pozX)
            {
                move(squares[7][begin.pozY], squares[end.pozX - 1][begin.pozY], false, false);
                ifWasCastling = end.piece;  //for undo
                wasCastling = Moves.castling.shortCastling;
                //this.moves_history.addMove(tempBegin, tempEnd, clearForwardHistory, wasCastling, wasEnPassant);
                //return;
            }
            else if (begin.pozX - 2 == end.pozX)
            {
                move(squares[0][begin.pozY], squares[end.pozX + 1][begin.pozY], false, false);
                ifWasCastling = end.piece;  // for undo
                wasCastling = Moves.castling.longCastling;
                //this.moves_history.addMove(tempBegin, tempEnd, clearForwardHistory, wasCastling, wasEnPassant);
                //return;
            }
            //endOf Castling
        }
        else if (end.piece.name.equals("Rook"))
        {
            if (((Rook) end.piece).wasMoved)
            {
                breakCastling = true;
                ((Rook) end.piece).wasMoved = true;
            }
        }
        else if (end.piece.name.equals("Pawn"))
        {
            if (twoSquareMovedPawn != null && squares[end.pozX][begin.pozY] == twoSquareMovedPawn.square) //en passant
            {
                ifWasEnPassant = squares[end.pozX][begin.pozY].piece; //for undo

                tempEnd.piece = squares[end.pozX][begin.pozY].piece; //ugly hack - put taken pawn in en passant plasty do end square

                squares[end.pozX][begin.pozY].piece = null;
                wasEnPassant = true;
            }

            if (begin.pozY - end.pozY == 2 || end.pozY - begin.pozY == 2) //moved two square
            {
                breakCastling = true;
                twoSquareMovedPawn = (Pawn) end.piece;
                
            }
            else
            {
                twoSquareMovedPawn = null; //erase last saved move (for En passant)
            }

            if (end.piece.square.pozY == 0 || end.piece.square.pozY == 7) //promote Pawn
            {
                if (clearForwardHistory)
                {
                    String color;
                    if (end.piece.player.color == Player.colors.white)
                    {
                        color = "W"; // promotionWindow was show with pieces in this color
                    }
                    else
                    {
                        color = "B";
                    }

                    String newPiece = JChessApp.jcv.showPawnPromotionBox(color); //return name of new piece

                    ((Pawn) end.piece).promote(newPiece,end);
                    
                    promotedPiece = end.piece;
                }
            }
        }
        else if (!end.piece.name.equals("Pawn"))
        {
            twoSquareMovedPawn = null; //erase last saved move (for En passant)
        }
        //}

        if (refresh)
        {
            this.unselect();//unselect square
            repaint();
        }

        if (clearForwardHistory)
        {
            this.moves_history.clearMoveForwardStack();
            this.moves_history.addMove(tempBegin, tempEnd, true, wasCastling, wasEnPassant, promotedPiece);
        }
        else
        {
            this.moves_history.addMove(tempBegin, tempEnd, false, wasCastling, wasEnPassant, promotedPiece);
        }
        end.piece.wasMoved=true;
    }/*endOf-move()-*/


    public boolean redo()
    {
        return redo(true);
    }

    public boolean redo(boolean refresh)
    {
        if ( this.settings.gameType == Settings.gameTypes.local ) //redo only for local game
        {
            Move first = this.moves_history.redo();

            Square from = null;
            Square to = null;

            if (first != null)
            {
                from = first.getFrom();
                to = first.getTo();

                this.move(this.squares[from.pozX][from.pozY], this.squares[to.pozX][to.pozY], true, false);
                if (first.getPromotedPiece() != null)
                {
                    Pawn pawn = (Pawn) this.squares[to.pozX][to.pozY].piece;
                    pawn.square = null;

                    this.squares[to.pozX][to.pozY].piece = first.getPromotedPiece();
                    Piece promoted = this.squares[to.pozX][to.pozY].piece;
                    promoted.square = this.squares[to.pozX][to.pozY];
                }
                return true;
            }
            
        }
        return false;
    }

    public boolean undo()
    {
        return undo(true);
    }

    public synchronized boolean undo(boolean refresh) //undo last move
    {
        Move last = this.moves_history.undo();


        if (last != null && last.getFrom() != null)
        {
            Square begin = last.getFrom();
            Square end = last.getTo();
            try
            {
                Piece moved = last.getMovedPiece();
                this.squares[begin.pozX][begin.pozY].piece = moved;

                moved.square = this.squares[begin.pozX][begin.pozY];

                Piece taken = last.getTakenPiece();
                if (last.getCastlingMove() != castling.none)
                {
                    Piece rook = null;
                    if (last.getCastlingMove() == castling.shortCastling)
                    {
                        rook = this.squares[end.pozX - 1][end.pozY].piece;
                        this.squares[7][begin.pozY].piece = rook;
                        rook.square = this.squares[7][begin.pozY];
                        this.squares[end.pozX - 1][end.pozY].piece = null;
                    }
                    else
                    {
                        rook = this.squares[end.pozX + 1][end.pozY].piece;
                        this.squares[0][begin.pozY].piece = rook;
                        rook.square = this.squares[0][begin.pozY];
                        this.squares[end.pozX + 1][end.pozY].piece = null;
                    }
                    ((King) moved).wasMoved = false;
                    ((Rook) rook).wasMoved = false;
                    this.breakCastling = false;
                }
                else if (moved.name.equals("Rook"))
                {
                    ((Rook) moved).wasMoved = false;
                }
                else if (moved.name.equals("Pawn") && last.wasEnPassant())
                {
                    Pawn pawn = (Pawn) last.getTakenPiece();
                    this.squares[end.pozX][begin.pozY].piece = pawn;
                    pawn.square = this.squares[end.pozX][begin.pozY];

                }
                else if (moved.name.equals("Pawn") && last.getPromotedPiece() != null)
                {
                    Piece promoted = this.squares[end.pozX][end.pozY].piece;
                    promoted.square = null;
                    this.squares[end.pozX][end.pozY].piece = null;
                }

                //check one more move back for en passant
                Move oneMoveEarlier = this.moves_history.getLastMoveFromHistory();
                if (oneMoveEarlier != null && oneMoveEarlier.wasPawnTwoFieldsMove())
                {
                    Piece canBeTakenEnPassant = this.squares[oneMoveEarlier.getTo().pozX][oneMoveEarlier.getTo().pozY].piece;
                    if (canBeTakenEnPassant.name.equals("Pawn"))
                    {
                        this.twoSquareMovedPawn = (Pawn) canBeTakenEnPassant;
                    }
                }

                if (taken != null && !last.wasEnPassant())
                {
                    this.squares[end.pozX][end.pozY].piece = taken;
                    taken.square = this.squares[end.pozX][end.pozY];
                }
                else
                {
                    this.squares[end.pozX][end.pozY].piece = null;
                }

                if (refresh)
                {
                    this.unselect();//unselect square
                    repaint();
                }

            }
            catch (java.lang.ArrayIndexOutOfBoundsException exc)
            {
                return false;
            }
            catch (java.lang.NullPointerException exc)
            {
                return false;
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    static boolean outOfGameboard(int X, int Y){
        return X>7 || X<0 || Y<0 || Y>7;
    }

    public static void recursiveDirectionCheck(ArrayList<Square> validMoves,Piece piece,Square lastSquare,movementVector moveVec,int steps){
        
        //if no steps are left or figure would leave the gameboard
        int newX = lastSquare.pozX+moveVec.movementVector_X;
        int newY = lastSquare.pozY +moveVec.movementVector_Y;
        if(moveVec.maxSteps<=steps || outOfGameboard(newX, newY)){return;}
        
        //Collision with own figure
        Square targetSquare = piece.chessboard.squares[newX][newY];
        if (targetSquare.piece != null && targetSquare.piece.player == piece.player){return;}

        //If Square is empty or contains enemy piece
        if(targetSquare.piece == null){
            if(moveVec.takeEnemy!=movementVector.takeEnemyPiece.REQUIRED){        
                validMoves.add(targetSquare);
            }
            recursiveDirectionCheck(validMoves, piece, targetSquare,moveVec,steps+1);
        }else if (targetSquare.piece.player != piece.player) {
            // check if enemy piece can be taken
            if(moveVec.takeEnemy!=movementVector.takeEnemyPiece.IMPOSSIBLE){        
                validMoves.add(targetSquare);
            }
        }
        
        

    }
}
