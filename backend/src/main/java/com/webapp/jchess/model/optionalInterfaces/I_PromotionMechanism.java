package com.webapp.jchess.model.optionalInterfaces;

import com.webapp.jchess.model.pieces.Piece;

public interface I_PromotionMechanism {
    public default Piece.typesOfPieces getPromotedPiece(){
            return Piece.typesOfPieces.Queen;
        }
}
