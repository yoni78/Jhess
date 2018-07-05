package org.jhess.logic.pieces;

import org.jhess.core.moves.MoveVector;
import org.jhess.core.pieces.Bishop;
import org.jhess.core.pieces.Knight;
import org.jhess.core.pieces.Pawn;
import org.jhess.core.pieces.Rook;

import java.util.List;

// TODO: 2018-06-08 Make the methods generic
public final class PieceUtils {

    private PieceUtils() {
    }

    public static List<MoveVector> getRookMoves(){
        return new Rook(null).getMoveList();
    }

    public static List<MoveVector> getBishopMoves(){
        return new Bishop(null).getMoveList();
    }

    public static List<MoveVector> getKnightMoves(){
        return new Knight(null).getMoveList();
    }

    public static List<MoveVector> getPawnMoves(){
        return new Pawn(null).getMoveList();
    }
}
