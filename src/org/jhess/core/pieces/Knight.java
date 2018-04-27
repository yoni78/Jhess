package org.jhess.core.pieces;

import org.jhess.core.Alliance;
import org.jhess.core.moves.Move;

import java.util.ArrayList;
import java.util.List;

import static org.jhess.core.moves.Move.*;

public class Knight extends Piece {
    public Knight(Alliance alliance) {
        super(alliance);
    }

    @Override
    public List<Move> getMoveList() {
        List<Move> moveList = new ArrayList<>();

        moveList.add(KNIGHT_FORWARD_CLOSE_RIGHT);
        moveList.add(KNIGHT_FORWARD_CLOSE_LEFT);
        moveList.add(KNIGHT_FORWARD_FAR_RIGHT);
        moveList.add(KNIGHT_FORWARD_FAR_LEFT);
        moveList.add(KNIGHT_BACKWARD_CLOSE_RIGHT);
        moveList.add(KNIGHT_BACKWARD_CLOSE_LEFT);
        moveList.add(KNIGHT_BACKWARD_FAR_RIGHT);
        moveList.add(KNIGHT_BACKWARD_FAR_LEFT);

        return moveList;
    }
}
