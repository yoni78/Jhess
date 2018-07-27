package org.jhess.core.pieces;

import org.jhess.core.Alliance;
import org.jhess.core.board.Square;
import org.jhess.core.moves.MoveVector;

import java.util.ArrayList;
import java.util.List;

import static org.jhess.core.moves.MoveVector.*;

public class Rook extends Piece {


    public Rook(Alliance alliance, Square square) {
        super(alliance, square, PieceType.ROOK);
    }

    public Rook(Alliance alliance) {
        super(alliance, PieceType.ROOK);
    }

    @Override
    public List<MoveVector> getMoveList() {
        MoveVector[] baseMoveVectors = {FORWARD, BACKWARD, RIGHT, LEFT};
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
        return new Rook(alliance);
    }
}
