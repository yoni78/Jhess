package org.jhess.core.pieces;

import org.jhess.core.Alliance;
import org.jhess.core.board.Square;
import org.jhess.core.moves.MoveVector;

import java.util.ArrayList;
import java.util.List;

import static org.jhess.core.moves.MoveVector.*;

public class Bishop extends Piece {


    public Bishop(Alliance alliance, Square square) {
        super(alliance, square, PieceType.BISHOP);
    }

    public Bishop(Alliance alliance) {
        super(alliance, PieceType.BISHOP);
    }

    @Override
    public List<MoveVector> getMoveList() {
        MoveVector[] baseMoveVectors = {FORWARD_RIGHT, FORWARD_LEFT, BACKWARD_RIGHT, BACKWARD_LEFT};
        List<MoveVector> moveVectorList = new ArrayList<>();

        for(MoveVector baseMoveVector : baseMoveVectors){
            for (int i = 0; i < 8; i++) {
                moveVectorList.add(baseMoveVector.extend(i));
            }
        }

        return moveVectorList;
    }

    @Override
    public Piece getCopy() {
        return new Bishop(alliance);
    }
}
