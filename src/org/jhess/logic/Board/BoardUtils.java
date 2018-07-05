package org.jhess.logic.Board;

import org.jhess.core.Alliance;
import org.jhess.core.board.Board;
import org.jhess.core.board.Square;
import org.jhess.core.moves.MoveVector;
import org.jhess.core.pieces.King;
import org.jhess.core.pieces.Piece;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class BoardUtils {

    private BoardUtils() {
    }

    private static boolean hasKing(Square square, Alliance alliance) {

        return square.getPiece() != null && square.getPiece().getAlliance() == alliance && square.getPiece() instanceof King;
    }

    /**
     * Gets the square to which the rook should move to in a castling move.
     *
     * @param board      The game board.
     * @param rookSquare The square the rook is currently on.
     * @return The square to which the rook should move to in a castling move.
     */
    public static Square getCastlingRookSquare(Board board, Square rookSquare) {
        if (rookSquare.getFile() == 0) {
            return board.getSquares()[rookSquare.getRank()][3];

        } else if (rookSquare.getFile() == 7) {
            return board.getSquares()[rookSquare.getRank()][5];

        } else {
            return null;
        }
    }

    /**
     * Finds the king of the given alliance on the board.
     *
     * @param board    The game board.
     * @param alliance The alliance of the king.
     * @return The king of the given alliance.
     */
    public static King getKing(Board board, Alliance alliance) {

        return Arrays.stream(board.getSquares())
                .flatMap(Arrays::stream)
                .filter(square -> hasKing(square, alliance))
                .findFirst()
                .map(square -> (King) square.getPiece())
                .orElse(null);
    }

    /**
     * Adds the move to the given square.
     * @param board The game board.
     * @param square The source square.
     * @param moveVector The move.
     * @return The destination square of the move.
     */
    public static Square addMoveToSquare(Board board, Square square, MoveVector moveVector){
        int rank = square.getRank() + moveVector.getRankToAdvance();
        int file = square.getFile() + moveVector.getFileToAdvance();

        if (rank < 0 || rank > 7 || file < 0 || file > 7){
            return null; // TODO: 2018-05-17 throw an exception?
        }

        return board.getSquares()[rank][file];
    }

    /**
     * Gets all of the pieces of the given player.
     * @param board The game board.
     * @param player The player.
     * @return The player's pieces.
     */
    public static List<Piece> getPieces(Board board, Alliance player){
        return Arrays.stream(board.getSquares())
                .flatMap(Arrays::stream)
                .filter(square -> square.getPiece() != null)
                .filter(square -> square.getPiece()!= null && square.getPiece().getAlliance() == player)
                .map(Square::getPiece)
                .collect(Collectors.toList());
    }
}
