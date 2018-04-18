package org.jhess.core.pieces;

import org.jhess.core.Alliance;

public class Bishop extends Piece {
    public Bishop(Alliance alliance) {
        super(alliance);
    }

    @Override
    public int[] getMoveList() {
        return new int[]{9, 18, 27, 36, 45, 54, 63,
                -9, -18, -27, -36, -45, -54, -63,
                7, 14, 21, 28, 35, 42, 49,
                -7, -14, -21, -28, -35, -42, -49};
    }
}
