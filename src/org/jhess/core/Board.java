package org.jhess.core;

import org.jhess.core.pieces.*;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final Square[][] squares = new Square[8][8];

    public Board() {

        List<Piece> whitePieces = generatePieces(Alliance.White);
        List<Piece> blackPieces = generatePieces(Alliance.Black);

        List<Piece> whitePawns = generatePawns(Alliance.White);
        List<Piece> blackPawns = generatePawns(Alliance.Black);

        initializeSquares(whitePieces, blackPieces, whitePawns, blackPawns);
    }


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

    private List<Piece> generatePawns(Alliance alliance) {
        List<Piece> pieces = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            pieces.add(new Pawn(alliance));
        }

        return pieces;
    }

    private void initializeSquares(List<Piece> whitePieces, List<Piece> blackPieces,
                                   List<Piece> whitePawns, List<Piece> blackPawns) {
        for (int i = 0; i < 8; i++) {

            squares[0][i] = new Square(whitePieces.get(i));
            squares[7][i] = new Square(blackPieces.get(i));

            squares[1][i] = new Square(whitePawns.get(i));
            squares[6][i] = new Square(blackPawns.get(i));
        }
    }

    public Square[][] getSquares() {
        return squares;
    }
}
