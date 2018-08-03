package org.jhess.core.pieces;

import org.jhess.core.Alliance;
import org.jhess.core.board.Square;
import org.jhess.core.moves.MoveVector;

import java.util.ArrayList;
import java.util.List;

import static org.jhess.core.moves.MoveVector.*;

public class Pawn extends Piece {


    public Pawn(Alliance alliance, Square square) {
        super(alliance, square, PieceType.PAWN);
    }

    public Pawn(Alliance alliance) {
        super(alliance, PieceType.PAWN);
    }

    @Override
    public List<MoveVector> getMoveList() {
        List<MoveVector> moveVectorList = new ArrayList<>();

        if (alliance == Alliance.WHITE) {
            moveVectorList.add(FORWARD);
            moveVectorList.add(FORWARD_LEFT);
            moveVectorList.add(FORWARD_RIGHT);

        } else {
            moveVectorList.add(BACKWARD);
            moveVectorList.add(BACKWARD_LEFT);
            moveVectorList.add(BACKWARD_RIGHT);
        }

        return moveVectorList;
    }

    @Override
    public Piece getCopy() {
        return new Pawn(alliance);
    }
}
