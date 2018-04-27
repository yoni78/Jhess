package org.jhess.core.pieces;

import org.jhess.core.Alliance;
import org.jhess.core.moves.Move;

import java.util.ArrayList;
import java.util.List;

import static org.jhess.core.moves.Move.*;

public class Bishop extends Piece {
    public Bishop(Alliance alliance) {
        super(alliance);
    }

    @Override
    public List<Move> getMoveList() {
        Move[] baseMoves = {FORWARD_RIGHT, FORWARD_LEFT, BACKWARD_RIGHT, BACKWARD_LEFT};
        List<Move> moveList = new ArrayList<>();

        for(Move baseMove : baseMoves){
            for (int i = 0; i < 8; i++) {
                moveList.add(baseMove.extend(i));
            }
        }

        return moveList;
    }
}
