package com.webapp.jchess.model.gamestate;
// import java.util.Arrays;

import com.webapp.jchess.model.pieces.Piece;

/**
 * Class to represent a chessboard square
 */
public class Triangle extends AbstractField
{
    boolean upright;

    Triangle(int boardIdx, int pozX, int pozY,int pozZ, Piece piece, AbstractBoard board, boolean _upright)
    {
        super(piece, board);
        this.localCoords = new int[]{pozX, pozY, pozZ};
        this.globalCoords = new int[]{boardIdx,pozX, pozY, pozZ};
        this.upright = _upright;
    }/*--endOf-Triangle--*/


    Triangle(Triangle _triangle)
    {
        super(_triangle.piece, _triangle.board);
        this.localCoords = _triangle.localCoords;
        this.globalCoords = _triangle.globalCoords;
        this.upright = _triangle.upright;
    }

    public Triangle clone(Triangle triangle)
    {
        return new Triangle(triangle);
    }


    @Override
    public AbstractBoard getBoard() {
        return (TriangleBoard)board;
    }

    @Override
    public int[] addVec(abstractVector vec){
        if(!(vec instanceof triangleVector))return null;
        triangleVector triVec = (triangleVector)vec;
        int[] retCoords = new int[]{
            this.getGlobalCoords()[0],
            this.getGlobalCoords()[1],
            this.getGlobalCoords()[2],
            this.getGlobalCoords()[3]};
        
        // System.out.println("### TriCoords upright: "+this.upright+" before: " + Arrays.toString(retCoords));
        
        if(this.upright){
            retCoords[1]+= triVec.Up_X;
            retCoords[2]+= triVec.Up_Y;
            retCoords[3]+= triVec.Up_Z;
        }else{
            retCoords[1]+= triVec.Down_X;
            retCoords[2]+= triVec.Down_Y;
            retCoords[3]+= triVec.Down_Z;
        }

        // System.out.println("TriCoords after: " + Arrays.toString(retCoords));

        return retCoords;
    }
    
}
