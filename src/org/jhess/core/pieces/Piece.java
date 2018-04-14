package org.jhess.core.pieces;

import org.jhess.core.Alliance;

public abstract class Piece {

    private Alliance alliance;

    public Piece(Alliance alliance) {
        this.alliance = alliance;
    }

    public Alliance getAlliance() {
        return alliance;
    }
}
