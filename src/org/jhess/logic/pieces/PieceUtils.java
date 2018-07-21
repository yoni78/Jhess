package org.jhess.logic.pieces;

import org.jhess.core.moves.MoveVector;
import org.jhess.core.pieces.*;

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

    public static boolean isPawn(Piece piece){
        return piece.getPieceType() == PieceType.PAWN;
    }

    public static boolean isKnight(Piece piece){
        return piece.getPieceType() == PieceType.KNIGHT;
    }

    public static boolean isBishop(Piece piece){
        return piece.getPieceType() == PieceType.BISHOP;
    }

    public static boolean isRook(Piece piece){
        return piece.getPieceType() == PieceType.ROOK;
    }

    public static boolean isQueen(Piece piece){
        return piece.getPieceType() == PieceType.QUEEN;
    }

    public static boolean isKing(Piece piece){
        return piece.getPieceType() == PieceType.KING;
    }
}
