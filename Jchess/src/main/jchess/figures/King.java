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
package jchess.figures;

/**
 * Class to represent a chess pawn king. King is the most important
 * piece for the game. Loose of king is the and of game.
 * When king is in danger by the opponent then it's a Checked, and when have
 * no other escape then stay on a square "in danger" by the opponent
 * then it's a CheckedMate, and the game is over.
 *
 *       |_|_|_|_|_|_|_|_|7
        |_|_|_|_|_|_|_|_|6
        |_|_|_|_|_|_|_|_|5
        |_|_|X|X|X|_|_|_|4
        |_|_|X|K|X|_|_|_|3
        |_|_|X|X|X|_|_|_|2
        |_|_|_|_|_|_|_|_|1
        |_|_|_|_|_|_|_|_|0
        0 1 2 3 4 5 6 7
 */
import java.util.ArrayList;

import jchess.Chessboard;
import jchess.Player;
import jchess.Square;
import jchess.gui_java.GUI;

import java.awt.Image;

public class King extends Piece
{
    
    //public boolean checked     = false;
    public static short value = 99;
    private static final Image imageWhite = GUI.loadImage("King-W.png");
    private static final Image imageBlack = GUI.loadImage("King-B.png");

    public King(Chessboard chessboard, Player player)
    {
        super(chessboard, player);
        //this.setImages("King-W.png", "King-B.png");
        this.symbol = "K";
        this.setImage();
        //this.image = imageWhite;

        
        this.movementOptions.add(new movementVector(1,1,1,movementVector.takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new movementVector(1,-1,1,movementVector.takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new movementVector(-1,1,1,movementVector.takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new movementVector(-1,-1,1,movementVector.takeEnemyPiece.POSSIBLE));

        this.movementOptions.add(new movementVector(1,0,1,movementVector.takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new movementVector(-1,0,1,movementVector.takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new movementVector(0,1,1,movementVector.takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new movementVector(0,-1,1,movementVector.takeEnemyPiece.POSSIBLE));
    }

    @Override
    public void setImage()
    {
        if (this.player.color == this.player.color.black)
        {
            image = imageBlack;
        }
        else
        {
            image = imageWhite;
        }
        orgImage = image;
    }

    /** Method to check is the king is checked
     *  @return bool true if king is not save, else returns false
     */
    public boolean isChecked()
    {
        return !isSafe(this.square);
    }

    /** Method to check is the king is checked or stalemated
     *  @return int 0 if nothing, 1 if checkmate, else returns 2
     */
    public int isCheckmatedOrStalemated()
    {
        /*
         *returns: 0-nothing, 1-checkmate, 2-stalemate
         */
        if (this.allMoves().size() == 0)
        {
            for (int i = 0; i < 8; ++i)
            {
                for (int j = 0; j < 8; ++j)
                {
                    if (chessboard.squares[i][j].piece != null
                            && chessboard.squares[i][j].piece.player == this.player
                            && chessboard.squares[i][j].piece.allMoves().size() != 0)
                    {
                        return 0;
                    }
                }
            }

            if (this.isChecked())
            {
                return 1;
            }
            else
            {
                return 2;
            }
        }
        else
        {
            return 0;
        }
    }

    public boolean getisSafe(Square s){
        return isSafe(s);
    }

    /** Method to check is the king is checked by an opponent
     * @param s Squere where is a king
     * @return bool true if king is save, else returns false
     */
    public boolean isSafe(Square s) //A bit confusing code.
    {
        // Rook & Queen
        for (int i = s.pozY + 1; i <= 7; ++i) //up
        {
            if (this.chessboard.squares[s.pozX][i].piece == null || this.chessboard.squares[s.pozX][i].piece == this) //if on this sqhuare isn't piece
            {
                continue;
            }
            else if (this.chessboard.squares[s.pozX][i].piece.player != this.player) //if isn't our piece
            {
                if (this.chessboard.squares[s.pozX][i].piece.name.equals("Rook")
                        || this.chessboard.squares[s.pozX][i].piece.name.equals("Queen"))
                {
                    return false;
                }
                else
                {
                    break;
                }
            }
            else
            {
                break;
            }
        }

        for (int i = s.pozY - 1; i >= 0; --i) //down
        {
            if (this.chessboard.squares[s.pozX][i].piece == null || this.chessboard.squares[s.pozX][i].piece == this) //if on this sqhuare isn't piece
            {
                continue;
            }
            else if (this.chessboard.squares[s.pozX][i].piece.player != this.player) //if isn't our piece
            {
                if (this.chessboard.squares[s.pozX][i].piece.name.equals("Rook")
                        || this.chessboard.squares[s.pozX][i].piece.name.equals("Queen"))
                {
                    return false;
                }
                else
                {
                    break;
                }
            }
            else
            {
                break;
            }
        }

        for (int i = s.pozX - 1; i >= 0; --i) //left
        {
            if (this.chessboard.squares[i][s.pozY].piece == null || this.chessboard.squares[i][s.pozY].piece == this) //if on this sqhuare isn't piece
            {
                continue;
            }
            else if (this.chessboard.squares[i][s.pozY].piece.player != this.player) //if isn't our piece
            {
                if (this.chessboard.squares[i][s.pozY].piece.name.equals("Rook")
                        || this.chessboard.squares[i][s.pozY].piece.name.equals("Queen"))
                {
                    return false;
                }
                else
                {
                    break;
                }
            }
            else
            {
                break;
            }
        }

        for (int i = s.pozX + 1; i <= 7; ++i) //right
        {
            if (this.chessboard.squares[i][s.pozY].piece == null || this.chessboard.squares[i][s.pozY].piece == this) //if on this sqhuare isn't piece
            {
                continue;
            }
            else if (this.chessboard.squares[i][s.pozY].piece.player != this.player) //if isn't our piece
            {
                if (this.chessboard.squares[i][s.pozY].piece.name.equals("Rook")
                        || this.chessboard.squares[i][s.pozY].piece.name.equals("Queen"))
                {
                    return false;
                }
                else
                {
                    break;
                }
            }
            else
            {
                break;
            }
        }

        // Bishop & Queen
        for (int h = s.pozX - 1, i = s.pozY + 1; !isout(h, i); --h, ++i) //left-up
        {
            if (this.chessboard.squares[h][i].piece == null || this.chessboard.squares[h][i].piece == this) //if on this sqhuare isn't piece
            {
                continue;
            }
            else if (this.chessboard.squares[h][i].piece.player != this.player) //if isn't our piece
            {
                if (this.chessboard.squares[h][i].piece.name.equals("Bishop")
                        || this.chessboard.squares[h][i].piece.name.equals("Queen"))
                {
                    return false;
                }
                else
                {
                    break;
                }
            }
            else
            {
                break;
            }
        }

        for (int h = s.pozX - 1, i = s.pozY - 1; !isout(h, i); --h, --i) //left-down
        {
            if (this.chessboard.squares[h][i].piece == null || this.chessboard.squares[h][i].piece == this) //if on this sqhuare isn't piece
            {
                continue;
            }
            else if (this.chessboard.squares[h][i].piece.player != this.player) //if isn't our piece
            {
                if (this.chessboard.squares[h][i].piece.name.equals("Bishop")
                        || this.chessboard.squares[h][i].piece.name.equals("Queen"))
                {
                    return false;
                }
                else
                {
                    break;
                }
            }
            else
            {
                break;
            }
        }

        for (int h = s.pozX + 1, i = s.pozY + 1; !isout(h, i); ++h, ++i) //right-up
        {
            if (this.chessboard.squares[h][i].piece == null || this.chessboard.squares[h][i].piece == this) //if on this sqhuare isn't piece
            {
                continue;
            }
            else if (this.chessboard.squares[h][i].piece.player != this.player) //if isn't our piece
            {
                if (this.chessboard.squares[h][i].piece.name.equals("Bishop")
                        || this.chessboard.squares[h][i].piece.name.equals("Queen"))
                {
                    return false;
                }
                else
                {
                    break;
                }
            }
            else
            {
                break;
            }
        }

        for (int h = s.pozX + 1, i = s.pozY - 1; !isout(h, i); ++h, --i) //right-down
        {
            if (this.chessboard.squares[h][i].piece == null || this.chessboard.squares[h][i].piece == this) //if on this sqhuare isn't piece
            {
                continue;
            }
            else if (this.chessboard.squares[h][i].piece.player != this.player) //if isn't our piece
            {
                if (this.chessboard.squares[h][i].piece.name.equals("Bishop")
                        || this.chessboard.squares[h][i].piece.name.equals("Queen"))
                {
                    return false;
                }
                else
                {
                    break;
                }
            }
            else
            {
                break;
            }
        }

        // Knight
        int newX, newY;

        //1
        newX = s.pozX - 2;
        newY = s.pozY + 1;

        if (!isout(newX, newY))
        {
            if (this.chessboard.squares[newX][newY].piece == null) //if on this sqhuare isn't piece
            {
            }
            else if (this.chessboard.squares[newX][newY].piece.player == this.player) //if is our piece
            {
            }
            else if (this.chessboard.squares[newX][newY].piece.name.equals("Knight"))
            {
                return false;
            }
        }

        //2
        newX = s.pozX - 1;
        newY = s.pozY + 2;

        if (!isout(newX, newY))
        {
            if (this.chessboard.squares[newX][newY].piece == null) //if on this sqhuare isn't piece
            {
            }
            else if (this.chessboard.squares[newX][newY].piece.player == this.player) //if is our piece
            {
            }
            else if (this.chessboard.squares[newX][newY].piece.name.equals("Knight"))
            {
                return false;
            }
        }

        //3
        newX = s.pozX + 1;
        newY = s.pozY + 2;

        if (!isout(newX, newY))
        {
            if (this.chessboard.squares[newX][newY].piece == null) //if on this sqhuare isn't piece
            {
            }
            else if (this.chessboard.squares[newX][newY].piece.player == this.player) //if is our piece
            {
            }
            else if (this.chessboard.squares[newX][newY].piece.name.equals("Knight"))
            {
                return false;
            }
        }

        //4
        newX = s.pozX + 2;
        newY = s.pozY + 1;

        if (!isout(newX, newY))
        {
            if (this.chessboard.squares[newX][newY].piece == null) //if on this sqhuare isn't piece
            {
            }
            else if (this.chessboard.squares[newX][newY].piece.player == this.player) //if is our piece
            {
            }
            else if (this.chessboard.squares[newX][newY].piece.name.equals("Knight"))
            {
                return false;
            }
        }

        //5
        newX = s.pozX + 2;
        newY = s.pozY - 1;

        if (!isout(newX, newY))
        {
            if (this.chessboard.squares[newX][newY].piece == null) //if on this sqhuare isn't piece
            {
            }
            else if (this.chessboard.squares[newX][newY].piece.player == this.player) //if is our piece
            {
            }
            else if (this.chessboard.squares[newX][newY].piece.name.equals("Knight"))
            {
                return false;
            }
        }

        //6
        newX = s.pozX + 1;
        newY = s.pozY - 2;

        if (!isout(newX, newY))
        {
            if (this.chessboard.squares[newX][newY].piece == null) //if on this sqhuare isn't piece
            {
            }
            else if (this.chessboard.squares[newX][newY].piece.player == this.player) //if is our piece
            {
            }
            else if (this.chessboard.squares[newX][newY].piece.name.equals("Knight"))
            {
                return false;
            }
        }

        //7
        newX = s.pozX - 1;
        newY = s.pozY - 2;

        if (!isout(newX, newY))
        {
            if (this.chessboard.squares[newX][newY].piece == null) //if on this sqhuare isn't piece
            {
            }
            else if (this.chessboard.squares[newX][newY].piece.player == this.player) //if is our piece
            {
            }
            else if (this.chessboard.squares[newX][newY].piece.name.equals("Knight"))
            {
                return false;
            }
        }

        //8
        newX = s.pozX - 2;
        newY = s.pozY - 1;

        if (!isout(newX, newY))
        {
            if (this.chessboard.squares[newX][newY].piece == null) //if on this sqhuare isn't piece
            {
            }
            else if (this.chessboard.squares[newX][newY].piece.player == this.player) //if is our piece
            {
            }
            else if (this.chessboard.squares[newX][newY].piece.name.equals("Knight"))
            {
                return false;
            }
        }

        // King
        King otherKing;
        if (this == chessboard.kingWhite)
        {
            otherKing = chessboard.kingBlack;
        }
        else
        {
            otherKing = chessboard.kingWhite;
        }

        if (s.pozX <= otherKing.square.pozX + 1
                && s.pozX >= otherKing.square.pozX - 1
                && s.pozY <= otherKing.square.pozY + 1
                && s.pozY >= otherKing.square.pozY - 1)
        {
            return false;
        }

        // Pawn
        // if (this.player.goDown) //check if player "go" down or up
        // {//System.out.println("go down");
            newX = s.pozX - 1;
            newY = s.pozY + 1;
            if (!isout(newX, newY))
            {
                if (this.chessboard.squares[newX][newY].piece == null) //if on this sqhuare isn't piece
                {
                }
                else if (this.chessboard.squares[newX][newY].piece.player == this.player) //if is our piece
                {
                }
                else if (this.chessboard.squares[newX][newY].piece.name.equals("Pawn"))
                {
                    return false;
                }
            }
            newX = s.pozX + 1;
            if (!isout(newX, newY))
            {
                if (this.chessboard.squares[newX][newY].piece == null) //if on this sqhuare isn't piece
                {
                }
                else if (this.chessboard.squares[newX][newY].piece.player == this.player) //if is our piece
                {
                }
                else if (this.chessboard.squares[newX][newY].piece.name.equals("Pawn"))
                {
                    return false;
                }
            }
        // }
        // else
        // {//System.out.println("go up");
            newX = s.pozX - 1;
            newY = s.pozY - 1;
            if (!isout(newX, newY))
            {
                if (this.chessboard.squares[newX][newY].piece == null) //if on this sqhuare isn't piece
                {
                }
                else if (this.chessboard.squares[newX][newY].piece.player == this.player) //if is our piece
                {
                }
                else if (this.chessboard.squares[newX][newY].piece.name.equals("Pawn"))
                {
                    return false;
                }
            }
            newX = s.pozX + 1;
            if (!isout(newX, newY))
            {
                if (this.chessboard.squares[newX][newY].piece == null) //if on this sqhuare isn't piece
                {
                }
                else if (this.chessboard.squares[newX][newY].piece.player == this.player) //if is our piece
                {
                }
                else if (this.chessboard.squares[newX][newY].piece.name.equals("Pawn"))
                {
                    return false;
                }
            }
        // }

        return true;
    }

    /** Method to check will the king be safe when move
     *  @return bool true if king is save, else returns false
     */
    public boolean willBeSafeWhenMoveOtherPiece(Square sqIsHere, Square sqWillBeThere) //long name ;)
    {
        Piece tmp = sqWillBeThere.piece;
        sqWillBeThere.piece = sqIsHere.piece; // move without redraw
        sqIsHere.piece = null;

        boolean ret = isSafe(this.square);

        sqIsHere.piece = sqWillBeThere.piece;
        sqWillBeThere.piece = tmp;

        return ret;
    }

    public boolean willBeSafeAfterMove(Piece piece, Square start, Square targetSquare){
        boolean safe = false;
        //If we are moving a king
        if(piece == this){
            King activeKing = ((King)piece);
            safe = activeKing.isSafe(targetSquare);
        }
        else{
            if(piece.chessboard.kingWhite.player == piece.player){
                safe = piece.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(piece.square, targetSquare);
            }
            else{
                safe = piece.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(piece.square, targetSquare);
            }
        }

        return safe;
    }
}