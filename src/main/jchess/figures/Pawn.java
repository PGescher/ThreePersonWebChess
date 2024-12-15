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

import java.util.ArrayList;

import jchess.Chessboard;
import jchess.Player;
import jchess.Square;
import jchess.gui_java.GUI;

import java.awt.Graphics;
import java.awt.Image;

/**
 * Class to represent a pawn piece
 * Pawn can move only forvard and can beat only across
 * In first move pawn can move 2 sqares
 * pawn can be upgreade to rook, knight, bishop, Queen if it's in the
 * squers nearest the side where opponent is lockated
 * Firat move of pawn:
 *       |_|_|_|_|_|_|_|_|7
|_|_|_|_|_|_|_|_|6
|_|_|_|X|_|_|_|_|5
|_|_|_|X|_|_|_|_|4
|_|_|_|P|_|_|_|_|3
|_|_|_|_|_|_|_|_|2
|_|_|_|_|_|_|_|_|1
|_|_|_|_|_|_|_|_|0
0 1 2 3 4 5 6 7
 *
 * Move of a pawn:
 *       |_|_|_|_|_|_|_|_|7
|_|_|_|_|_|_|_|_|6
|_|_|_|_|_|_|_|_|5
|_|_|_|X|_|_|_|_|4
|_|_|_|P|_|_|_|_|3
|_|_|_|_|_|_|_|_|2
|_|_|_|_|_|_|_|_|1
|_|_|_|_|_|_|_|_|0
0 1 2 3 4 5 6 7
 * Beats with can take pawn:
 *       |_|_|_|_|_|_|_|_|7
|_|_|_|_|_|_|_|_|6
|_|_|_|_|_|_|_|_|5
|_|_|X|_|X|_|_|_|4
|_|_|_|P|_|_|_|_|3
|_|_|_|_|_|_|_|_|2
|_|_|_|_|_|_|_|_|1
|_|_|_|_|_|_|_|_|0
0 1 2 3 4 5 6 7
 */
public class Pawn extends Piece
{
    
    boolean down;
    protected static final Image imageWhite = GUI.loadImage("Pawn-W.png");
    protected static final Image imageBlack = GUI.loadImage("Pawn-B.png");
    public static short value = 1;

    public Pawn(Chessboard chessboard, Player player)
    {
        super(chessboard, player);
        //this.setImages("Pawn-W.png", "Pawn-B.png");
        this.symbol = "";
        this.setImage();

        // this.movement = new PawnMovement();
        this.movementOptions.add(new movementVector(0,1,1,movementVector.takeEnemyPiece.IMPOSSIBLE));
        this.movementOptions.add(new movementVector(-1,1,1,movementVector.takeEnemyPiece.REQUIRED));
        this.movementOptions.add(new movementVector(1,1,1,movementVector.takeEnemyPiece.REQUIRED));

        this.movementOptions.add(new movementVector(0,-1,1,movementVector.takeEnemyPiece.IMPOSSIBLE));
        this.movementOptions.add(new movementVector(-1,-1,1,movementVector.takeEnemyPiece.REQUIRED));
        this.movementOptions.add(new movementVector(1,-1,1,movementVector.takeEnemyPiece.REQUIRED));

        //ADD Special moves
        // Start with 2 steps if pawn hasnt been moved yet
        // Add En Passant

        // Maybe remove pawns ability to move backwards. 
        // That would make it impossible to move between enemy chessboards
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

    @Override
    public void specialMoves(ArrayList<Square> validMoves){
        if(this.wasMoved==false){
            movementVector specialMove = new movementVector(0,2,1,movementVector.takeEnemyPiece.IMPOSSIBLE);
            Chessboard.recursiveDirectionCheck(validMoves, this, this.square, specialMove,0);
            movementVector specialMove2 = new movementVector(0,-2,1,movementVector.takeEnemyPiece.IMPOSSIBLE);
            Chessboard.recursiveDirectionCheck(validMoves, this, this.square, specialMove2,0);
        }
    }


    public void promote(String newPiece,Square square)
    {
        Piece piece;
        switch (newPiece) {
            case "Queen":
                piece = new Queen(this.chessboard, square.piece.player);
                break;
            case "Rook":
                piece = new Rook(this.chessboard, square.piece.player);
                break;
            case "Bishop":
                piece = new Bishop(this.chessboard, square.piece.player);
                break;
            default:
                piece = new Knight(this.chessboard, square.piece.player);
                break;
        }
            
        piece.chessboard = square.piece.chessboard;
        piece.player = square.piece.player;
        piece.square = square.piece.square;
        square.piece = piece;

    }
}
