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
 * Class to represent a chess pawn rook
 * Rook can move:
 *       |_|_|_|X|_|_|_|_|7
|_|_|_|X|_|_|_|_|6
|_|_|_|X|_|_|_|_|5
|_|_|_|X|_|_|_|_|4
|X|X|X|B|X|X|X|X|3
|_|_|_|X|_|_|_|_|2
|_|_|_|X|_|_|_|_|1
|_|_|_|X|_|_|_|_|0
0 1 2 3 4 5 6 7
 *
 */
public class Rook extends Piece
{

    protected static final Image imageWhite = GUI.loadImage("Rook-W.png");
    protected static final Image imageBlack = GUI.loadImage("Rook-B.png");
    public static short value = 5;

    public Rook(Chessboard chessboard, Player player)
    {
        super(chessboard, player);//call initializer of super type: Piece
        //this.setImages("Rook-W.png", "Rook-B.png");
        this.symbol = "R";
        this.setImage();

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
