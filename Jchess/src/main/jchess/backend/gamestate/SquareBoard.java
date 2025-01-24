package jchess.backend.gamestate;

import java.util.ArrayList;

import jchess.backend.AbstractGame;
import jchess.backend.Player;
import jchess.backend.pieces.Bishop;
import jchess.backend.pieces.King;
import jchess.backend.pieces.Knight;
import jchess.backend.pieces.Pawn;
import jchess.backend.pieces.Queen;
import jchess.backend.pieces.Rook;

public class SquareBoard extends AbstractBoard{
    Square[][] squares;
    ArrayList<Integer> promotionRows;
    int dimX;
    int dimY;

    public SquareBoard(int boardIdx, int dim_x, int dim_y, AbstractGame game){
        super(game,boardIdx);


        squares = new Square[dim_x][dim_y];
        for (int x = 0; x < dim_x; x++){
            for (int y = 0; y < dim_y; y++)
            {
                this.squares[x][y] = new Square(boardIdx,x, y, null);
            }
        }

        this.promotionRows = new ArrayList<>();
        this.dimX = dim_x;
        this.dimY = dim_y;
    }

    public Square getSquare(int[] localCoords){
        if(!validCoordinates(localCoords)) return null;
        return squares[localCoords[0]][localCoords[1]];
    }

    public void addPromotionRow(int y){
        this.promotionRows.add(y);
    }

    public Boolean isPromotionRow(int y){
        return promotionRows.contains(y);
    }

    public void setFigures4NewGame(int row, Player player)
    {
        if (row != 0 && row != 7)
        {
            System.out.println("Row out of bounds.");
            return;
        }

        this.squares[0][row].setPiece(new Rook(game, player));
        this.squares[7][row].setPiece(new Rook(game, player));
        this.squares[1][row].setPiece(new Knight(game, player));
        this.squares[6][row].setPiece(new Knight(game, player));
        this.squares[2][row].setPiece(new Bishop(game, player));
        this.squares[5][row].setPiece(new Bishop(game, player));
        this.squares[3][row].setPiece(new Queen(game, player));
        game.kings.put(player.playerID,new King(game, player));
        this.squares[4][row].setPiece(game.kings.get(player.playerID));
        for(int col=0;col<8;col++)
        {
            game.activePieces.add(this.squares[col][row].piece);
        }
        
    }

    public void createPawnRow(int row, Player player)
    {
        if(row<0 || row>=this.squares.length){
            System.out.println("Error setting pawns. Index out of bounds");
            return;
        }
        for (int col = 0; col < this.squares.length; col++)
        {
            this.squares[col][row].setPiece(new Pawn(game, player));
            game.activePieces.add(this.squares[col][row].piece);
        }
    }

    @Override
    public boolean validCoordinates(int[] localCoords){
        if(localCoords==null)return false;
        if(localCoords.length!=2) return false;
        if(localCoords[0] >= this.dimX || localCoords[0] <0 ) return false;
        if(localCoords[1] >= this.dimY || localCoords[1] <0 ) return false;
        return true;
    }

}
