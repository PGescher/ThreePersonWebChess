package com.webapp.jchess.model.gamestate;

import java.util.ArrayList;
import java.util.Arrays;

import com.webapp.jchess.model.AbstractGame;
import com.webapp.jchess.model.Player;
import com.webapp.jchess.model.pieces.*;

public class TriangleBoard extends AbstractBoard{
    private ArrayList<Triangle> triangles;
    private int maxIdx;
    
    public TriangleBoard(int boardIdx, int dim, AbstractGame game){
        super(game, boardIdx);
        this.maxIdx = dim-1;

        //Initalize the Arraylist triangles
        this.triangles = new ArrayList<>();

        for(int row=0;row<dim;row++){

            //First Triangle in Row
            int firstZ = maxIdx-row;
            triangles.add(new Triangle(boardIdx, row,0,firstZ,null, this,false));
            
            // How many extra squares
            int squares = maxIdx - row;
            if(squares==0) break;
            for(int sq =1;sq<=squares;sq++){
                
                int leftY =  sq -1;
                int rightY =  sq;
                int sqZ = firstZ-sq;
                triangles.add(new Triangle(boardIdx, row,leftY,sqZ,null, this,true));
                triangles.add(new Triangle(boardIdx, row,rightY,sqZ,null, this,false));
            }
        }

    }

    public void setPawn(int index, Player player){
        this.triangles.get(index).setPiece(new Pawn(game, player));
        game.activePieces.add(this.triangles.get(index).piece);
    }

    public Triangle getField(int[] localCoords){
        if(!validCoordinates(localCoords)) return null;

        return getTriangle(localCoords[0],localCoords[1],localCoords[2]);
    }

    public boolean trianglePointingUp(int[] localCoords){
        int x = localCoords[0];
        int y = localCoords[1];
        int z = localCoords[2];
        return x+y+z == maxIdx;
    }

    public boolean validCoordinates(int[] localCoords){
        int x = localCoords[0];
        int y = localCoords[1];
        int z = localCoords[2];

        if(x+y+z != maxIdx && x+y+z != maxIdx-1 ||
            x<0 || y< 0 || z<0) {
            System.out.println("Invalid Triangle coordinate " + Arrays.toString(localCoords));
            return false;
        }

        return true;
    }

    private Triangle getTriangle(int x, int y, int z){
        int triIndx=0;

        int row=0;
        while(x>row){
            int squares = this.maxIdx - row;
            triIndx += 1 + 2*squares;
            row++;
        }
        int firstZ = triangles.get(triIndx).getLocalCoords()[2];
        triIndx += (firstZ-z)*2 -1; 
        
        //Dreieck zeigt nach oben
        if(x+y+z == maxIdx){triIndx+=1;}

        if(triIndx >= triangles.size()) {
            return null;
        }else{
            return triangles.get(triIndx);
        }
        
    }

}
