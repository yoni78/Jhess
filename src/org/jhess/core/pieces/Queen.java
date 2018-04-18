package org.jhess.core.pieces;

import org.jhess.core.Alliance;

public class Queen extends Piece {
    public Queen(Alliance alliance) {
        super(alliance);
    }

    @Override
    public int[] getMoveList() {
        return new int[]{8, 16, 24, 32, 40, 48, 56,
                -8, -16, -24, -32, -40, -48, -56,
                1, 2, 3, 4, 5, 6, 7,
                -1, -2, -3, -4, -5, -6, -7,
                9, 18, 27, 36, 45, 54, 63,
                -9, -18, -27, -36, -45, -54, -63,
                7, 14, 21, 28, 35, 42, 49,
                -7, -14, -21, -28, -35, -42, -49};
    }
}
