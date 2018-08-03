package org.jhess.core.pieces;

import org.jhess.core.Alliance;
import org.jhess.core.board.Square;
import org.jhess.core.moves.MoveVector;

import java.util.List;
import java.util.Objects;

public abstract class Piece {

    protected final Alliance alliance;
    protected final PieceType pieceType;

    public Piece(Alliance alliance, Square square, PieceType pieceType) {
        this.alliance = alliance;
        this.pieceType = pieceType;
    }

    public Piece(Alliance alliance, PieceType pieceType) {
        this(alliance, null, pieceType);
    }

    public Alliance getAlliance() {
        return alliance;
    }

    public abstract List<MoveVector> getMoveList();

    public abstract Piece getCopy();

    public PieceType getPieceType() {
        return pieceType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return alliance == piece.alliance &&
                pieceType == piece.pieceType;
    }

    @Override
    public int hashCode() {

        return Objects.hash(alliance, pieceType);
    }
}
