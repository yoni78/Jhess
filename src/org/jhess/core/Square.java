package org.jhess.core;

import org.jhess.core.pieces.Piece;

public class Square {
    private Piece piece;

    public Square() {
        this(null);
    }

    Square(Piece piece){
        this.piece = piece;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public boolean isOccupied(){
        return piece != null;
    }
}
