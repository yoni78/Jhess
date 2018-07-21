package org.jhess.core.pieces;

import org.jhess.core.Alliance;
import org.jhess.core.board.Square;
import org.jhess.core.moves.MoveVector;

import java.util.ArrayList;
import java.util.List;

import static org.jhess.core.moves.MoveVector.*;

public class Knight extends Piece {


    public Knight(Alliance alliance, Square square) {
        super(alliance, square, PieceType.KNIGHT);
    }

    public Knight(Alliance alliance) {
        super(alliance, PieceType.KNIGHT);
    }

    @Override
    public List<MoveVector> getMoveList() {
        List<MoveVector> moveVectorList = new ArrayList<>();

        moveVectorList.add(KNIGHT_FORWARD_CLOSE_RIGHT);
        moveVectorList.add(KNIGHT_FORWARD_CLOSE_LEFT);
        moveVectorList.add(KNIGHT_FORWARD_FAR_RIGHT);
        moveVectorList.add(KNIGHT_FORWARD_FAR_LEFT);
        moveVectorList.add(KNIGHT_BACKWARD_CLOSE_RIGHT);
        moveVectorList.add(KNIGHT_BACKWARD_CLOSE_LEFT);
        moveVectorList.add(KNIGHT_BACKWARD_FAR_RIGHT);
        moveVectorList.add(KNIGHT_BACKWARD_FAR_LEFT);

        return moveVectorList;
    }

    @Override
    public Piece getCopy() {
        Knight copy = new Knight(alliance, square);
        copy.setFirstMove(isFirstMove);
        return copy;
    }
}
