package org.jhess.core.pieces;

import org.jhess.core.Alliance;

public class Pawn extends Piece {

    public Pawn(Alliance alliance) {
        super(alliance);
    }

    // TODO: Add capture moves
    @Override
    public int[] getMoveList() {
        return new int[]{8};
    }
}
