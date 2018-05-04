package org.jhess.core.board;

import org.jhess.core.pieces.Piece;

public class Square {
    private Piece piece;
    private final int rank;
    private final int file;

    Square(int rank, int file) {
        this(rank, file, null);
    }

    Square(int rank, int file, Piece piece){

        if ((rank < 0 || rank > 7) || (file < 0 || file > 7)){
            throw new IllegalArgumentException("The rank and file of a square should be between 0 and 7.");
        }

        this.piece = piece;
        this.rank = rank;
        this.file = file;

        if (piece != null){
            piece.setSquare(this);
        }
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public int getRank() {
        return rank;
    }

    public int getFile() {
        return file;
    }

    public boolean isOccupied(){
        return piece != null;
    }
}
