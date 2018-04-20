package org.jhess.core.pieces;

import org.jhess.core.Alliance;
import org.jhess.core.Move;

import java.util.List;

public abstract class Piece {

    protected Alliance alliance;

    public Piece(Alliance alliance) {
        this.alliance = alliance;
    }

    public Alliance getAlliance() {
        return alliance;
    }

    public abstract List<Move> getMoveList();
}
