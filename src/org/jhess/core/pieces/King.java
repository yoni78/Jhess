package org.jhess.core.pieces;

import org.jhess.core.Alliance;
import org.jhess.core.board.Square;
import org.jhess.core.moves.MoveVector;

import java.util.ArrayList;
import java.util.List;

import static org.jhess.core.moves.MoveVector.*;

public class King extends Piece {

    public King(Alliance alliance, Square square) {
        super(alliance, square);
    }

    public King(Alliance alliance) {
        super(alliance);
    }

    @Override
    public List<MoveVector> getMoveList() {
        List<MoveVector> moveVectorList = new ArrayList<>();

        moveVectorList.add(FORWARD);
        moveVectorList.add(BACKWARD);
        moveVectorList.add(RIGHT);
        moveVectorList.add(LEFT);
        moveVectorList.add(FORWARD_RIGHT);
        moveVectorList.add(FORWARD_LEFT);
        moveVectorList.add(BACKWARD_RIGHT);
        moveVectorList.add(BACKWARD_LEFT);

        return moveVectorList;
    }

    @Override
    public Piece getCopy() {
        return new King(alliance, square);
    }
}
