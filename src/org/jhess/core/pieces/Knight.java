package org.jhess.core.pieces;

import org.jhess.core.Alliance;

public class Knight extends Piece {
    public Knight(Alliance alliance) {
        super(alliance);
    }

    @Override
    public int[] getMoveList() {
        return new int[]{6, 10, 15, 17, -6, -10, -15, -17};
    }
}
