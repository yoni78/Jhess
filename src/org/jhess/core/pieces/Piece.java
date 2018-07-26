package org.jhess.core.pieces;

import org.jhess.core.Alliance;
import org.jhess.core.board.Square;
import org.jhess.core.moves.MoveVector;

import java.util.List;

public abstract class Piece {

    protected final Alliance alliance;
    protected boolean isFirstMove = true; // TODO: 2018-07-26 Delete?
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

    public boolean isFirstMove() {
        return isFirstMove;
    }

    public void setFirstMove(boolean firstMove) {
        isFirstMove = firstMove;
    }

    public PieceType getPieceType() {
        return pieceType;
    }
}
