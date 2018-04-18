package org.jhess.core.board;

import org.jhess.core.pieces.Piece;

public class Square {
    private Piece piece;

    Square() {
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
