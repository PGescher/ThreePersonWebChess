package com.webapp.jchess.model.moves;

import com.webapp.jchess.model.AbstractGame;
import com.webapp.jchess.model.gamestate.AbstractField;
import com.webapp.jchess.model.pieces.Pawn;
import com.webapp.jchess.model.pieces.Piece;
import com.webapp.jchess.model.pieces.Queen;

public class StandardMove extends AbstractMove{

    Boolean wasMovedBefore;
    AbstractField from;
    AbstractField to;
    Piece movedPiece;
    Piece takenPiece;
    Piece promotedPiece;

    public StandardMove(Piece _movedPiece, AbstractField _to){
        this.from = _movedPiece.field;
        this.to = _to;
        this.movedPiece = _movedPiece;
        this.takenPiece = _to.piece;
        this.wasMovedBefore = movedPiece.wasMoved;
    }


    @Override
    public void execute_move() {
        if (this.takenPiece!=null) {
            AbstractGame board = this.movedPiece.game;
            board.removePiece(this.takenPiece);
        }
        this.to.piece= this.movedPiece;
        this.movedPiece.field = this.to;
        this.from.piece = null;
        this.movedPiece.wasMoved=true;
        
        // Pawn Promotion

        if(this.movedPiece instanceof Pawn && 
        movedPiece.game.isPromotionField(this.to.getGlobalCoords())){
            AbstractGame board = movedPiece.game;
            board.removePiece(this.movedPiece);

            //this.promotedPiece = new Queen(movedPiece.game, movedPiece.player);

            this.to.piece = this.promotedPiece;
            this.promotedPiece.field = this.to;
            this.movedPiece.game.activePieces.add(this.promotedPiece);
           
        }
    }

    @Override
    public void undo_move() {
        if(promotedPiece!=null){
            AbstractGame board = promotedPiece.game;
            board.removePiece(promotedPiece);
            board.activePieces.add(movedPiece);
        }

        this.from.piece = this.movedPiece;
        this.movedPiece.field = this.from;
        if(this.takenPiece!=null){
            this.to.piece = this.takenPiece;
            this.takenPiece.field = this.to;

            AbstractGame board = takenPiece.game;
            board.activePieces.add(takenPiece);
        }else{
            this.to.piece = null;
        }
        this.movedPiece.wasMoved = this.wasMovedBefore;
    }


    @Override
    public String toString() {
        String locMove = new String(from.piece.symbol);
        
        locMove += Character.toString((char) (from.getLocalCoords()[0] + 97));//add letter of Square from which move was made
        locMove += Integer.toString(8 - from.getLocalCoords()[1]);//add number of Square from which move was made
        if (to.piece != null){
            locMove += "x";//take down opponent piece
        }
        else{
            locMove += "-";//normal move
        }
        locMove += Character.toString((char) (to.getLocalCoords()[0] + 97));//add letter of Square to which move was made
        locMove += Integer.toString(8 - to.getLocalCoords()[1]);//add number of Square to which move was made
        
            
        // locMove += "#";//check mate
        // locMove += "+";//check
            
        
        return locMove;
    }

    public void setPromotionPiece(Piece toBePromotedTo) {
        this.promotedPiece = toBePromotedTo;
    }
    
}
