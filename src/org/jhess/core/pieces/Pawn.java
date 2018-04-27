package org.jhess.core.pieces;

import org.jhess.core.Alliance;
import org.jhess.core.Move;

import java.util.ArrayList;
import java.util.List;

import static org.jhess.core.Move.BACKWARD;
import static org.jhess.core.Move.FORWARD;

public class Pawn extends Piece {

    public Pawn(Alliance alliance) {
        super(alliance);
    }

    // TODO: Add capture moves(?)
    @Override
    public List<Move> getMoveList() {
        List<Move> moveList = new ArrayList<>();

        // TODO: Use only forward, and reverse the sign somewhere else
        if (alliance == Alliance.WHITE) {
            moveList.add(FORWARD);

        } else {
            moveList.add(BACKWARD);
        }
        return moveList;
    }
}
