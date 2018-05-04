package org.jhess.core.pieces;

import org.jhess.core.Alliance;
import org.jhess.core.board.Square;
import org.jhess.core.moves.MoveVector;

import java.util.ArrayList;
import java.util.List;

import static org.jhess.core.moves.MoveVector.FORWARD;

public class Pawn extends Piece {


    public Pawn(Alliance alliance, Square square) {
        super(alliance, square);
    }

    public Pawn(Alliance alliance) {
        super(alliance);
    }

    // TODO: Add capture moves(?)
    @Override
    public List<MoveVector> getMoveList() {
        List<MoveVector> moveVectorList = new ArrayList<>();
        moveVectorList.add(FORWARD);

        return moveVectorList;
    }
}
