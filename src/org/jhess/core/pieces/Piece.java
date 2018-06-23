package org.jhess.core.pieces;

import org.jhess.core.Alliance;
import org.jhess.core.board.Square;
import org.jhess.core.moves.MoveVector;

import java.util.List;

public abstract class Piece {

    protected final Alliance alliance;
    protected boolean isFirstMove = true;
    protected Square square; // TODO: 2018-06-09 Should be immutable

    public Piece(Alliance alliance, Square square) {

        this.alliance = alliance;
    }

    public Piece(Alliance alliance) {
        this(alliance, null);
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

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }
}
