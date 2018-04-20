package org.jhess.core.pieces;

import org.jhess.core.Alliance;
import org.jhess.core.Move;

import java.util.ArrayList;
import java.util.List;

import static org.jhess.core.Move.*;

public class Rook extends Piece {
    public Rook(Alliance alliance) {
        super(alliance);
    }

    @Override
    public List<Move> getMoveList() {
        Move[] baseMoves = {FORWARD, BACKWARD, RIGHT, LEFT};
        List<Move> moveList = new ArrayList<>();

        for(Move baseMove : baseMoves){
            for (int i = 0; i < 8; i++) {
                moveList.add(baseMove.extend(i));
            }
        }

        return moveList;
    }
}
