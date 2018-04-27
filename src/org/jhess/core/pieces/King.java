package org.jhess.core.pieces;

import org.jhess.core.Alliance;
import org.jhess.core.moves.Move;

import java.util.ArrayList;
import java.util.List;

import static org.jhess.core.moves.Move.*;

public class King extends Piece {
    public King(Alliance alliance) {
        super(alliance);
    }

    @Override
    public List<Move> getMoveList() {
        List<Move> moveList = new ArrayList<>();

        moveList.add(FORWARD);
        moveList.add(BACKWARD);
        moveList.add(RIGHT);
        moveList.add(LEFT);
        moveList.add(FORWARD_RIGHT);
        moveList.add(FORWARD_LEFT);
        moveList.add(BACKWARD_RIGHT);
        moveList.add(BACKWARD_LEFT);

        return moveList;
    }
}
