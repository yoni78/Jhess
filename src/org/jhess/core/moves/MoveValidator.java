package org.jhess.core.moves;

import org.jhess.core.Alliance;
import org.jhess.core.board.Board;
import org.jhess.core.board.Square;
import org.jhess.core.pieces.*;

import static org.jhess.core.moves.Move.*;

public class MoveValidator {
    private MoveValidator() {
    }

    // TODO: Split method by piece types?
    /**
     * Determines if the given move is a legal one.
     *
     * @param board      The game board.
     * @param srcSquare  The source square.
     * @param destSquare The Destination square.
     * @return Whether the move is legal or not.
     */
    public static MoveValidation validateMove(Board board, Square srcSquare, Square destSquare) {
        Move move = new Move(srcSquare, destSquare);

        Piece piece = srcSquare.getPiece();
        Piece otherPiece = destSquare.getPiece();

        if (!hasNoPieceInHisWay(board, piece, move, srcSquare)){
            return new MoveValidation(false);
        }

        if (piece instanceof Pawn){

            if (isSpecialPawnMove(piece, move, destSquare)){
                return new MoveValidation(true);

            } else if (destSquare.isOccupied()){
                return new MoveValidation(false);
            }
        }

        // Castling
        if (piece instanceof King && isCastlingMove(move)){

            Square rookSquare = getCastlingRookSquare(board, destSquare, move);
            Piece rook = rookSquare.getPiece();

            if (piece.isFirstMove() && rook != null && rook.isFirstMove()){

                piece.setFirstMove(false);
                rook.setFirstMove(false);

                return new MoveValidation(true, true, rookSquare);
            }
        }

        // If the destination square contains a friendly piece
         if (destSquare.isOccupied() && otherPiece.getAlliance() == piece.getAlliance()) {
            return new MoveValidation(false);
        }

        // TODO: Check for castling
        // TODO: Handle promotions (not here?)
        // If the move is in the piece's possible move list
        if (piece.getMoveList().contains(move)){
            return new MoveValidation(true);
        } else {
            return new MoveValidation(false);
        }
    }

    /**
     * Gets the rook that should participate in the castling.
     * @param board The game board.
     * @param destSquare The destination square of the move.
     * @param move The move to be played.
     * @return The rook that should participate in the castling.
     */
    private static Square getCastlingRookSquare(Board board, Square destSquare, Move move) {

        Square rookSquare = null;

        // Short
        if (move.equals(RIGHT.extend(1))){
            rookSquare = board.getSquares()[destSquare.getRank()][destSquare.getFile() + 1];

        // Long
        } else if (move.equals(LEFT.extend(1))){
            rookSquare = board.getSquares()[destSquare.getRank()][destSquare.getFile() - 2];
        }

        return rookSquare;
    }

    /**
     * Checks if the move is a castling move.
     * @param move The move to check.
     * @return If it's a castling move.
     */
    private static boolean isCastlingMove(Move move) {
        return move.equals(LEFT.extend(1)) || move.equals(RIGHT.extend(1));
    }

    /**
     * Checks special cases for pawn like captures or double moves at the start.
     * @param piece The piece to be played.
     * @param move The Move to be played.
     * @param destSquare The destination square of the move.
     * @return If there was a valid pawn capture or double move at the start.
     */
    private static boolean isSpecialPawnMove(Piece piece, Move move, Square destSquare){

        // TODO: Check for En-Passant

        // If it's a capture move
        if (destSquare.isOccupied()){

            if (piece.getAlliance() == Alliance.WHITE && (move.equals(FORWARD_RIGHT) || move.equals(FORWARD_LEFT))){
                return true;
            }

            if (piece.getAlliance() == Alliance.BLACK && (move.equals(BACKWARD_RIGHT) || move.equals(BACKWARD_LEFT))){
                return true;
            }
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
     * Checks if the piece attempts to jump over another piece in it's move.
     * @param board The game board;
     * @param piece The piece to be played.
     * @param move The Move to be played.
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
