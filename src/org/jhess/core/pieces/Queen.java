package org.jhess.core.pieces;

import org.jhess.core.Alliance;
import org.jhess.core.board.Square;
import org.jhess.core.moves.MoveVector;

import java.util.ArrayList;
import java.util.List;

import static org.jhess.core.moves.MoveVector.*;
import static org.jhess.core.moves.MoveVector.LEFT;

public class Queen extends Piece {


    public Queen(Alliance alliance, Square square) {
        super(alliance, square, PieceType.QUEEN);
    }

    public Queen(Alliance alliance) {
        super(alliance, PieceType.QUEEN);
    }

    @Override
    public List<MoveVector> getMoveList() {
        MoveVector[] baseMoveVectors = {FORWARD, BACKWARD, RIGHT, LEFT, FORWARD_RIGHT, FORWARD_LEFT, BACKWARD_RIGHT, BACKWARD_LEFT};
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
        Queen copy = new Queen(alliance, square);
        copy.setFirstMove(isFirstMove);
        return copy;
    }
}
