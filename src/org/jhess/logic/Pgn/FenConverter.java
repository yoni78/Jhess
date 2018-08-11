package org.jhess.logic.Pgn;

import org.jhess.core.Alliance;
import org.jhess.core.board.Board;
import org.jhess.core.board.Square;
import org.jhess.core.pieces.Piece;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Converts to and from the FEN format.
 */
public class FenConverter {

    public static final String startPosition = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

    private final String fen;

    public FenConverter(String fen) {
        this.fen = fen;
    }

    /**
     * Expands rows with empty spaces to make them easier to parse.
     *
     * @param row A row in FEN format.
     * @return A new row with a length of 8 where the blank squares are filled with a '-'.
     */
    private String expandRow(String row) {
        // If the row doesn't contain a blank square, return it
        if (!row.matches(".*\\d+.*")) {
            return row;
        }

        StringBuilder newRow = new StringBuilder();
        for (char col : row.toCharArray()) {
            int emptySquares = 0;
            try {
                emptySquares = Integer.parseInt(Character.toString(col));
            } catch (NumberFormatException ignored) {
            }

            if (emptySquares != 0) {
                for (int i = 0; i < emptySquares; i++) {
                    newRow.append('-');
                }
            } else {
                newRow.append(col);
            }
        }

        return newRow.toString();
    }

    /**
     * Creates the pieces section of the FEN string from the Board object.
     *
     * @param board The board.
     * @return The piece section of a FEN string which represents the given board.
     */
    private static String piecesToFen(Board board) {
        StringBuilder[] pieces = new StringBuilder[8];
        PgnConverter pgnConverter = new PgnConverter();

        for (int i = 0; i < pieces.length; i++) {
            pieces[i] = new StringBuilder();
        }

        for (int i = 7; i >= 0; i--) {

            int emptySquaresCounter = 0;
            for (int j = 0; j < 8; j++) {
                Piece piece = board.getSquares()[i][j].getPiece();

                if (piece != null) {

                    if (emptySquaresCounter > 0) {
                        pieces[7 - i].append(emptySquaresCounter);
                    }

                    pieces[7 - i].append(pgnConverter.pieceToPgn(piece));
                    emptySquaresCounter = 0;

                } else {
                    emptySquaresCounter++;
                }

                if (j == 7 && emptySquaresCounter > 0) {
                    pieces[7 - i].append(emptySquaresCounter);
                }
            }
        }

        List<String> rowStrings = Arrays.stream(pieces)
                .map(StringBuilder::toString)
                .collect(Collectors.toList());

        return String.join("/", rowStrings);
    }

    /**
     * Creates the castling rights section of the FEN string from the Board object.
     *
     * @param board The board.
     * @return The castling rights section of a FEN string which represents the given board.
     */
    private static String castlingRightsToFen(Board board) {
        StringBuilder castlingString = new StringBuilder();

        if (board.isWhiteCanCastleKingSide()) {
            castlingString.append('K');
        }

        if (board.isWhiteCanCastleQueenSide()) {
            castlingString.append('Q');
        }

        if (board.isBlackCanCastleKingSide()) {
            castlingString.append('k');
        }

        if (board.isBlackCanCastleQueenSide()) {
            castlingString.append('q');
        }

        return castlingString.toString();
    }

    /**
     * Converts a Board object to a FEN string.
     *
     * @param board The board.
     * @return A FEN string representing the board.
     */
    public static String boardToFen(Board board) {

        PgnConverter pgnConverter = new PgnConverter();

        String pieces = piecesToFen(board);
        String playerToMove = String.valueOf(pgnConverter.allianceToPgn(board.getPlayerToMove()));
        String castlingRights = castlingRightsToFen(board);
        String enPassantSquare = board.getEnPassantTarget() != null ? pgnConverter.squareToPgn(board.getEnPassantTarget()) : "-";
        String halfMoveClock = String.valueOf(board.getHalfMoveClock());
        String fullMoveNumber = String.valueOf(board.getFullMoveNumber());

        return String.join(" ", pieces, playerToMove, castlingRights, enPassantSquare, halfMoveClock, fullMoveNumber);
    }

    public Piece[][] getPieces() {
        PgnConverter pgnConverter = new PgnConverter();

        Piece[][] pieces = new Piece[8][8];
        String[] rows = fen.split(" ")[0].split("/");

        for (int i = 0; i < 8; i++) {
            String newRow = expandRow(rows[i]);

            for (int j = 0; j < 8; j++) {
                if (newRow.charAt(j) != '-') {
                    pieces[7 - i][j] = pgnConverter.pgnToPiece(newRow.toCharArray()[j]);
                }
            }
        }

        return pieces;
    }

    public Alliance getThePlayerToMove() {
        return new PgnConverter().pgnToAlliance(fen.split(" ")[1].charAt(0));
    }

    public boolean canWhiteCastleKingSide() {
        return fen.split(" ")[2].contains("K");
    }

    public boolean canWhiteCastleQueenSide() {
        return fen.split(" ")[2].contains("Q");
    }

    public boolean canBlackCastleKingSide() {
        return fen.split(" ")[2].contains("k");
    }

    public boolean canBlackCastleQueenSide() {
        return fen.split(" ")[2].contains("q");
    }

    public Square getEnPassantTarget() {
        String enPassantSection = fen.split(" ")[3];

        if (enPassantSection.equals("-")) {
            return null;
        } else {
            return new PgnConverter().pgnToSquare(enPassantSection);
        }
    }

    public int getHalfMoves() {
        return Integer.parseInt(fen.split(" ")[4]);
    }

    public int getFullMoves() {
        return Integer.parseInt(fen.split(" ")[5]);
    }
}
