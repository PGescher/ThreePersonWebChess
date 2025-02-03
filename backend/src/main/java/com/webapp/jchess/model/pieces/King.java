package com.webapp.jchess.model.pieces;

import com.webapp.jchess.model.AbstractGame;
import com.webapp.jchess.model.Player;
import com.webapp.jchess.model.gamestate.*;
import com.webapp.jchess.model.gamestate.abstractVector.takeEnemyPiece;

import java.util.ArrayList;

public class King extends Piece
{
    
    //public boolean checked  = false;

    public King(AbstractGame game, Player player)
    {
        super(game, player, Piece.typesOfPieces.King);
        this.symbol="K";
        
        this.movementOptions.add(new squareVector(1,1,1,takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new squareVector(1,-1,1,takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new squareVector(-1,1,1,takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new squareVector(-1,-1,1,takeEnemyPiece.POSSIBLE));

        this.movementOptions.add(new squareVector(1,0,1,takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new squareVector(-1,0,1,takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new squareVector(0,1,1,takeEnemyPiece.POSSIBLE));
        this.movementOptions.add(new squareVector(0,-1,1,takeEnemyPiece.POSSIBLE));

        //Triangle horizontal and vertical
        triangleVector triVec = new triangleVector(0,0,1,-1,0,0,1,takeEnemyPiece.POSSIBLE);
        this.movementOptions.add(triVec);
        this.movementOptions.add(triVec.invert());

        triVec = new triangleVector(0,1,0,-1,0,0,1,takeEnemyPiece.POSSIBLE);
        this.movementOptions.add(triVec);
        this.movementOptions.add(triVec.invert());

        triVec = new triangleVector(0,1,0,0,0,-1,1,takeEnemyPiece.POSSIBLE);
        this.movementOptions.add(triVec);
        this.movementOptions.add(triVec.invert());

        //Triangle diagonal
        triVec = new triangleVector(-1,1,1,-1,0,0,15,takeEnemyPiece.POSSIBLE);
        this.movementOptions.add(triVec);
        this.movementOptions.add(triVec.invert());

        triVec = new triangleVector(0,0,1,1,1,0,15,takeEnemyPiece.POSSIBLE);
        this.movementOptions.add(triVec);
        this.movementOptions.add(triVec.invert());

        triVec = new triangleVector(0,1,0,-1,1,-1,15,takeEnemyPiece.POSSIBLE);
        this.movementOptions.add(triVec);
        this.movementOptions.add(triVec.invert());
    }

    //Is the King at its current position threatened by an enemy piece?
    public boolean isCurrentlyChecked()
    {
        for(Piece p: this.game.activePieces){
            if(p.player==this.player || p.field == null) {continue;}
            if(p.possibleMoves().contains(this.field)) {return true;}
        }
        return false;
    }

    /** Method to check is the king is checked or stalemated
     *  @return int 0 if nothing, 1 if checkmate, else returns 2
     */
    public int isCheckmatedOrStalemated()
    {
        //Get all valid moves of our own pieces.
        //If we can Move, we`re fine
        ArrayList<Piece> tmpPieces = new ArrayList<>(game.activePieces);
        for(Piece p: tmpPieces){
            if(p.player==this.player && p.validMoves().size()>0) return 0;
        }

        // If we cant move and are checked, we lost
        if (this.isCurrentlyChecked()){
            return 1;
        }
        else{
            return 2;
        }
        
    }
    
    //Test if moving our piece would threaten the safety of our king.
    public boolean willBeSafeAfterMove(AbstractField start, AbstractField targetSquare){

        //Only if piece was at target square
        Piece tmp = null;
        if(targetSquare.piece!=null){
            tmp = targetSquare.piece;
            tmp.field = null;
            game.activePieces.remove(tmp);
        }

        //Main part is always relevant
        targetSquare.piece = start.piece; // move without redraw
        targetSquare.piece.field = targetSquare;
        start.piece = null;
        boolean ret = !this.isCurrentlyChecked();
        start.piece = targetSquare.piece;
        start.piece.field = start;
        targetSquare.piece = null;

        //Only if piece was at target square
        if(tmp!=null){
        targetSquare.piece = tmp;
        tmp.field = targetSquare;
        game.activePieces.add(tmp);
        }
        return ret;
    }

    /*
    @Override
    public void specialMoves(ArrayList<AbstractField> validMoves){
        if(this.wasMoved)return;

        int shortX = this.field.coords[0]+3;
        int shortY = this.field.coords[1];
        int[] shortCoords = new int[]{shortX,shortY};
        if(game.validCoordinates(shortCoords)
            &&!game.getField(shortCoords).piece.wasMoved){
            movementVector specialMove = new movementVector(2,0,1,movementVector.takeEnemyPiece.IMPOSSIBLE);
            recursiveDirectionCheck(validMoves, this, this.field, specialMove,0);
        }

        int longX = this.field.coords[0]-4;
        int longY = this.field.coords[1];
        int [] longCoords = new int[]{longX,longY};
        if(game.validCoordinates(longCoords)&&!game.getField(longCoords).piece.wasMoved){
            movementVector specialMove = new movementVector(-2,0,1,movementVector.takeEnemyPiece.IMPOSSIBLE);
            recursiveDirectionCheck(validMoves, this, this.field, specialMove,0);
        }
    }
    */
}