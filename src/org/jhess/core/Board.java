package org.jhess.core;

import org.jhess.core.pieces.*;
import org.jhess.utils.SquareUtils;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final Square[][] squares = new Square[8][8];

    public Board() {

        List<Piece> whitePieces = generatePieces(Alliance.WHITE);
        List<Piece> blackPieces = generatePieces(Alliance.BLACK);

        List<Piece> whitePawns = generatePawns(Alliance.WHITE);
        List<Piece> blackPawns = generatePawns(Alliance.BLACK);

        initializeSquares(whitePieces, blackPieces, whitePawns, blackPawns);
    }

    /**
     * Generates a list of the pieces.
     * @param alliance
     * @return
     */
    private List<Piece> generatePieces(Alliance alliance) {
        List<Piece> pieces = new ArrayList<>();

        pieces.add(new Rook(alliance));
        pieces.add(new Knight(alliance));
        pieces.add(new Bishop(alliance));
        pieces.add(new Queen(alliance));
        pieces.add(new King(alliance));
        pieces.add(new Bishop(alliance));
        pieces.add(new Knight(alliance));
        pieces.add(new Rook(alliance));

        return pieces;
    }

    /**
     * Generates a list of pawns.
     * @param alliance
     * @return
     */
    private List<Piece> generatePawns(Alliance alliance) {
        List<Piece> pieces = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            pieces.add(new Pawn(alliance));
        }

        return pieces;
    }

    /**
     * Places the pieces in their default places.
     * @param whitePieces
     * @param blackPieces
     * @param whitePawns
     * @param blackPawns
     */
    private void initializeSquares(List<Piece> whitePieces, List<Piece> blackPieces,
                                   List<Piece> whitePawns, List<Piece> blackPawns) {
        for (int i = 0; i < 8; i++) {

            squares[0][i] = new Square(whitePieces.get(i));
            squares[7][i] = new Square(blackPieces.get(i));

            squares[1][i] = new Square(whitePawns.get(i));
            squares[6][i] = new Square(blackPawns.get(i));

            for (int j = 2; j < 6; j++) {
                squares[j][i] = new Square();
            }
        }
    }

    public Square[][] getSquares() {
        return squares;
    }

    public Square getSquare(int squareId){
        int row = SquareUtils.getSquareRow(squareId);
        int file = SquareUtils.getSquareFile(squareId);

        return getSquares()[row][file];
    }

}
