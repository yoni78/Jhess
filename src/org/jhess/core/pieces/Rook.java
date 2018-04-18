package org.jhess.core.pieces;

import org.jhess.core.Alliance;

public class Rook extends Piece {
    public Rook(Alliance alliance) {
        super(alliance);
    }

    @Override
    public int[] getMoveList() {
        return new int[]{8, 16, 24, 32, 40, 48, 56,
                -8, -16, -24, -32, -40, -48, -56,
                1, 2, 3, 4, 5, 6, 7,
                -1, -2, -3, -4,- 5, -6, -7};
    }
}
