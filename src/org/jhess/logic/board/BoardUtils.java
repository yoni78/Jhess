package org.jhess.logic.board;

import org.jhess.core.Alliance;
import org.jhess.core.board.Board;
import org.jhess.core.board.Square;
import org.jhess.core.moves.MoveVector;
import org.jhess.core.pieces.King;
import org.jhess.logic.PgnConverter;

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
     * @return The square of the king of the given alliance.
     */
    public static Square getKing(Board board, Alliance alliance) {

        return Arrays.stream(board.getSquares())
                .flatMap(Arrays::stream)
                .filter(square -> hasKing(square, alliance))
                .findFirst()
                .orElse(null);
    }

    /**
     * Adds the move to the given square.
     *
     * @param board      The game board.
     * @param square     The source square.
     * @param moveVector The move.
     * @return The destination square of the move.
     */
    public static Square addMoveToSquare(Board board, Square square, MoveVector moveVector) {
        int rank = square.getRank() + moveVector.getRankToAdvance();
        int file = square.getFile() + moveVector.getFileToAdvance();

        if (rank < 0 || rank > 7 || file < 0 || file > 7) {
            return null;
        }

        return board.getSquares()[rank][file];
    }

    /**
     * Gets all of the squares which have pieces of the given player on them.
     *
     * @param board  The game board.
     * @param player The player.
     * @return The player's pieces.
     */
    public static List<Square> getPieces(Board board, Alliance player) {
        return Arrays.stream(board.getSquares())
                .flatMap(Arrays::stream)
                .filter(square -> square.getPiece() != null && square.getPiece().getAlliance() == player)
                .collect(Collectors.toList());
    }

    /**
     * Copies the squares from another board to a array of squares.
     *
     * @param squares The squares of the other board.
     * @return A copied array of the squares.
     */
    public static Square[][] copySquares(Square[][] squares) {

        Square[][] newSquares = new Square[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                newSquares[i][j] = new Square(squares[i][j]);
            }
        }

        return newSquares;
    }

    /**
     * Returns the square represented by the pgn on the given board.
     *
     * @param board     The game board.
     * @param squarePgn The PGN string which represents the square.
     * @return The requested square from the board.
     */
    public static Square getSquareByPgn(Board board, String squarePgn) {
        Square square = new PgnConverter().pgnToSquare(squarePgn);
        return board.getSquares()[square.getRank()][square.getFile()];
    }

    /**
     * Checks if the pawn got to the last rank (and should subsequently be promoted).
     *
     * @param destSquare   The destination square of the move.
     * @param pawnAlliance The alliance of the pawn.
     * @return If the pawn got to the last rank.
     */
    public static boolean isPawnOnLastRank(Square destSquare, Alliance pawnAlliance) {
        return (pawnAlliance == Alliance.WHITE && destSquare.getRank() == 7) ||
                (pawnAlliance == Alliance.BLACK && destSquare.getRank() == 0);
    }
}
