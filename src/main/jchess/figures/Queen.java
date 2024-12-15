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
 * Class to represent a queen piece
 * Queen can move almost in every way:
 * |_|_|_|X|_|_|_|X|7
    |X|_|_|X|_|_|X|_|6
    |_|X|_|X|_|X|_|_|5
    |_|_|X|X|x|_|_|_|4
    |X|X|X|Q|X|X|X|X|3
    |_|_|X|X|X|_|_|_|2
    |_|X|_|X|_|X|_|_|1
    |X|_|_|X|_|_|X|_|0
    0 1 2 3 4 5 6 7
 */
public class Queen extends Piece
{
    //The Queen basically combined the Bishop and Rook Movement.
    public static short value = 9;
    protected static final Image imageWhite = GUI.loadImage("Queen-W.png");
    protected static final Image imageBlack = GUI.loadImage("Queen-B.png");

    public Queen(Chessboard chessboard, Player player)
    {
        super(chessboard, player);//call initializer of super type: Piece
        //this.setImages("Queen-W.png", "Queen-B.png");
        this.symbol = "Q";
        this.setImage();

        //Dont couple this with rook or bishop.
        //Otherwise changing the classes becomes more complicated
        this.movementOptions.add(new movementVector(1,1,7,movementVector.takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new movementVector(1,-1,7,movementVector.takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new movementVector(-1,1,7,movementVector.takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new movementVector(-1,-1,7,movementVector.takeEnemyPiece.POSSIBLE));

        this.movementOptions.add(new movementVector(1,0,7,movementVector.takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new movementVector(-1,0,7,movementVector.takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new movementVector(0,1,7,movementVector.takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new movementVector(0,-1,7,movementVector.takeEnemyPiece.POSSIBLE));
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

}
