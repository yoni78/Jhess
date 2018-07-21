package org.jhess.core.board;

import org.jhess.core.Alliance;
import org.jhess.core.pieces.*;
import org.jhess.logic.FenConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates boards.
 */
public class BoardFactory {

    /**
     * Generates a list of all of the pieces a player starts with except the pawns.
     *
     * @param alliance The alliance of the pieces.
     * @return A list of all the pieces with the given alliance.
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
     * Generates a list of pawns that a player starts with.
     *
     * @param alliance The alliance of the pawns.
     * @return A list eight pawns with the given alliance.
     */
    private List<Piece> generatePawns(Alliance alliance) {
        List<Piece> pieces = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            pieces.add(new Pawn(alliance));
        }

        return pieces;
    }

    /**
     * Generates the squares of a board from a matrix of pieces.
     * @param pieces A matrix of pieces.
     * @return A matrix of squares.
     */
    private Square[][] generateSquares(Piece[][] pieces) {
        Square[][] squares = new Square[8][8];

        for(int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                squares[i][j] = new Square(i, j, pieces[i][j]);
            }
        }

        return squares;
    }

    /**
     * Generates a Board from a FEN string.
     * @param fen The FEN string.
     * @return A Board which is represented by the FEN string.
     */
    public Board createFromFen(String fen){
        FenConverter fenConverter = new FenConverter(fen);
        BoardBuilder boardBuilder = new BoardBuilder();

        boardBuilder.setSquares(generateSquares(fenConverter.getPieces()));
        boardBuilder.setPlayerToMove(fenConverter.getThePlayerToMove());
        boardBuilder.setWhiteCanCastleKingSide(fenConverter.canWhiteCastleKingSide());
        boardBuilder.setWhiteCanCastleQueenSide(fenConverter.canWhiteCastleQueenSide());
        boardBuilder.setBlackCanCastleKingSide(fenConverter.canBlackCastleKingSide());
        boardBuilder.setBlackCanCastleQueenSide(fenConverter.canBlackCastleQueenSide());
        boardBuilder.setEnPassantTarget(fenConverter.getEnPassantTarget());
        boardBuilder.setHalfMoveClock(fenConverter.getHalfMoves());
        boardBuilder.setFullMoveNumber(fenConverter.getFullMoves());

        return boardBuilder.createBoard();
    }

    /**
     * Returns a new Board with the default starting position.
     * @return A default board.
     */
    public Board createDefaultBoard(){
        return createFromFen(FenConverter.startPosition);
    }

    /**
     * Gets a board builder for the given board.
     * @param board The board to create the board builder for.
     * @return A board builder with it's fields set to the given board's fields.
     */
    public BoardBuilder getBoardBuilder(Board board){
        BoardBuilder boardBuilder = new BoardBuilder();

        boardBuilder.setSquares(board.getSquares());
        boardBuilder.setPlayerToMove(board.getPlayerToMove());
        boardBuilder.setWhiteCanCastleKingSide(board.isWhiteCanCastleKingSide());
        boardBuilder.setWhiteCanCastleQueenSide(board.isWhiteCanCastleQueenSide());
        boardBuilder.setBlackCanCastleKingSide(board.isBlackCanCastleKingSide());
        boardBuilder.setBlackCanCastleQueenSide(board.isBlackCanCastleQueenSide());
        boardBuilder.setEnPassantTarget(board.getEnPassantTarget());
        boardBuilder.setHalfMoveClock(board.getHalfMoveClock());
        boardBuilder.setFullMoveNumber(board.getFullMoveNumber());

        return boardBuilder;
    }

    /**
     * Copies a board to a new board.
     * @param board The board to copy.
     * @return A new board identical to the given one.
     */
    public Board copyBoard(Board board){
        return getBoardBuilder(board).createBoard();
    }
}
