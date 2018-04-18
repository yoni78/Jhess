package org.jhess.core.pieces;

import org.jhess.core.Alliance;

public class King extends Piece {
    public King(Alliance alliance) {
        super(alliance);
    }

    @Override
    public int[] getMoveList() {
        return new int[]{1, -1, 8, -8, 7, 9, -7 , -9};
    }
}
