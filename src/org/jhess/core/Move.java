package org.jhess.core;

import org.jhess.core.board.Board;
import org.jhess.core.pieces.Piece;
import org.jhess.utils.SquareUtils;

import java.util.stream.IntStream;

public class Move {

    /**
     * Determines if the given move is a legal one.
     *
     * @param board      The game board.
     * @param srcSquare  The source square.
     * @param destSquare The Destination square.
     * @return Whether the move is legal or not.
     */
    public static boolean isValidMove(Board board, int srcSquare, int destSquare) {

        int move = destSquare - srcSquare;
        Piece piece = board.getSquare(srcSquare).getPiece();

        // If the move isn't in the piece's possible move list
        if (IntStream.of(piece.getMoveList()).noneMatch(possibleMove -> possibleMove == move)) {
            return false;
        }

        // If the destination square is illegal
        if (!SquareUtils.isInBoard(destSquare)) {
            return false;
        }

        // If the destination square contains a friendly piece
        Piece otherPiece = board.getSquare(destSquare).getPiece();

        return !board.getSquare(destSquare).isOccupied() || otherPiece.getAlliance() != piece.getAlliance();
    }
}
