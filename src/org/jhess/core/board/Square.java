package org.jhess.core.board;

import org.jhess.core.pieces.Piece;

import java.util.Objects;

public class Square {
    private Piece piece;
    private final int rank;
    private final int file;

    public Square(int rank, int file) {
        this(rank, file, null);
    }

    public Square(int rank, int file, Piece piece){

        if ((rank < 0 || rank > 7) || (file < 0 || file > 7)){
            throw new IllegalArgumentException("The rank and file of a square should be between 0 and 7.");
        }

        this.piece = piece;
        this.rank = rank;
        this.file = file;
    }

    public Square(Square square) {
        rank = square.getRank();
        file = square.getFile();
        piece = square.getPiece() != null ? square.getPiece().getCopy() : null;
    }

    public Piece getPiece() {
        return piece != null ? piece.getCopy() : null;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return rank == square.rank &&
                file == square.file &&
                Objects.equals(piece, square.piece);
    }

    @Override
    public int hashCode() {

        return Objects.hash(piece, rank, file);
    }
}
