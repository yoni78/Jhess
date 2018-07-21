package org.jhess.logic;

import org.jhess.core.Alliance;
import org.jhess.core.board.Board;
import org.jhess.core.board.Square;
import org.jhess.core.pieces.Piece;

/**
 * Converts to and from the FEN format.
 */
public class FenConverter {

    public static final String startPosition = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

    private String fen;

    public FenConverter(String fen) {
        this.fen = fen;
    }

    /**
     * Expands rows with empty spaces to make them easier to parse.
     * @param row A row in FEN format.
     * @return A new row with a length of 8 where the blank squares are filled with a '-'.
     */
    private String expandRow(String row){
        // If the row doesn't contain a blank square, return it
        if (!row.matches(".*\\d+.*")){
            return row;
        }

        StringBuilder newRow = new StringBuilder();
        for(char col : row.toCharArray()){
            int emptySquares = 0;
            try{
                emptySquares = Integer.parseInt(Character.toString(col));
            } catch (NumberFormatException ignored){ }

            if (emptySquares != 0){
                for (int i = 0; i < emptySquares; i++){
                    newRow.append('-');
                }
            } else {
                    newRow.append(col);
            }
        }

        return newRow.toString();
    }

    public Piece[][] getPieces(){
        PgnConverter pgnConverter = new PgnConverter();

        Piece[][] pieces = new Piece[8][8];
        String[]rows = fen.split(" ")[0].split("/");

        for (int i = 0; i < 8; i++){
            String newRow = expandRow(rows[i]);

            for(int j = 0; j < 8; j++){
                if(newRow.charAt(j) != '-'){
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
