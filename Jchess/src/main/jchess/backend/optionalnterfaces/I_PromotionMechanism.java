package jchess.backend.optionalnterfaces;

import jchess.backend.pieces.Piece;

public interface I_PromotionMechanism {
    public default Piece.typesOfPieces getPromotedPiece(){
            return Piece.typesOfPieces.Queen;
        }
}
