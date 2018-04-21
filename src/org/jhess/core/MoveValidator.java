package org.jhess.core;

import org.jhess.core.board.Board;
import org.jhess.core.board.Square;
import org.jhess.core.pieces.*;

import static org.jhess.core.Move.*;

public class MoveValidator {
    private MoveValidator() {
    }

    /**
     * Determines if the given move is a legal one.
     *
     * @param board      The game board.
     * @param srcSquare  The source square.
     * @param destSquare The Destination square.
     * @return Whether the move is legal or not.
     */
    public static boolean isValidMove(Board board, Square srcSquare, Square destSquare) {

        Move move = new Move(destSquare.getRank() - srcSquare.getRank(),
                destSquare.getFile() - srcSquare.getFile());

        Piece piece = srcSquare.getPiece();
        Piece otherPiece = destSquare.getPiece();

        if (!hasNoPieceInHisWay(board, piece, move, srcSquare)){
            return false;
        }

        if (piece instanceof Pawn){

            if (isSpecialPawnMove(piece, move, destSquare)){
                return true;

            } else if (destSquare.isOccupied()){
                return false;
            }
        }

        // If the destination square contains a friendly piece
        if (destSquare.isOccupied() && otherPiece.getAlliance() == piece.getAlliance()) {
            return false;
        }

        // TODO: Check for castling
        // TODO: Handle promotions (not here?)

        // If the move is in the piece's possible move list
        return piece.getMoveList().contains(move);
    }

    /**
     * Checks special cases for pawn like captures or double moves at the start.
     * @param piece The piece that was played.
     * @param move The Move that was played.
     * @param destSquare The destination square of the move.
     * @return If there was a valid pawn capture or double move at the start.
     */
    private static boolean isSpecialPawnMove(Piece piece, Move move, Square destSquare){

        // TODO: Check for En-Passant

        // If it's a capture move
        if (destSquare.isOccupied() && (move.equals(FORWARD_RIGHT) || move.equals(FORWARD_LEFT))){
            return true;
        }

        // If it's the pawn's first move
        Move possibleMove;
        if(piece.getAlliance() == Alliance.WHITE) {
            possibleMove = FORWARD.extend(1);

        } else {
            possibleMove = BACKWARD.extend(1);
        }

        if (piece.isFirstMove() && move.equals(possibleMove)) {
            piece.setFirstMove(false);
            return true;
        }

        return false;
    }

    /**
     * Checks if the piece attemps to jump over another piece in it's move.
     * @param board The game board;
     * @param piece The piece that was played.
     * @param move The Move that was played.
     * @param srcSquare The source square of the move.
     * @return If the path for the move is clear.
     */
    private static boolean hasNoPieceInHisWay(Board board, Piece piece, Move move, Square srcSquare){

        // If the piece is skipping over another piece
        if (piece instanceof Knight) {
            return true;
        }

        int distance = Math.max(Math.abs(move.getRankToAdvance()), Math.abs(move.getFileToAdvance()));

        Move tempMove;
        Square tempSquare;
        for (int i = 1; i < distance; i++) {
            tempMove = move.extend(-i);
            tempSquare = board.addMoveToSquare(srcSquare, tempMove);

            if (tempSquare.isOccupied()) {
                return false;
            }
        }

        return true;
    }
}
