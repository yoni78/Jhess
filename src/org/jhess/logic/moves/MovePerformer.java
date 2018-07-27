package org.jhess.logic.moves;

import org.jhess.core.Alliance;
import org.jhess.core.board.Board;
import org.jhess.core.board.BoardBuilder;
import org.jhess.core.board.BoardFactory;
import org.jhess.core.board.Square;
import org.jhess.core.moves.MoveAnalysis;
import org.jhess.core.moves.MoveVector;
import org.jhess.core.pieces.*;
import org.jhess.logic.board.BoardUtils;
import org.jhess.logic.pieces.PieceUtils;

import java.util.Objects;

public class MovePerformer {

    private final Board currentPosition;

    public MovePerformer(Board currentPosition) {
        this.currentPosition = currentPosition;
    }

    /**
     * Performs the necessary actions for a castling move.
     *
     * @param moveAnalysis The analysis of the move.
     * @param board        The game board.
     */
    private Board castlingMove(Board board, MoveAnalysis moveAnalysis) {
        Square rookSquare = moveAnalysis.getRookToCastleSquare();
        Square newRookSquare = BoardUtils.getCastlingRookSquare(board, rookSquare);

        return movePiece(board, rookSquare, Objects.requireNonNull(newRookSquare));
    }

    /**
     * Performs the necessary actions for an en passant move.
     *
     * @param moveAnalysis The analysis of the move.
     */
    private Board enPassantMove(Board board, MoveAnalysis moveAnalysis) {
        Board newBoard = new BoardFactory().copyBoard(board);
        Square capturePawnSquare = moveAnalysis.getEnPassantSquare();
        Square newSquare = newBoard.getSquares()[capturePawnSquare.getRank()][capturePawnSquare.getFile()];

        newSquare.setPiece(null);

        return newBoard;
    }

    /**
     * Performs the necessary actions for a promotion move.
     *
     * @param moveAnalysis The analysis of the move.
     */
    private Board promotionMove(Board board, MoveAnalysis moveAnalysis, Alliance currentPlayer, PieceType pieceType) {
        Piece newPiece;

        switch (pieceType) {

            case BISHOP:
                newPiece = new Bishop(currentPlayer, moveAnalysis.getPromotionSquare());
                break;
            case KNIGHT:
                newPiece = new Knight(currentPlayer, moveAnalysis.getPromotionSquare());
                break;
            case ROOK:
                newPiece = new Rook(currentPlayer, moveAnalysis.getPromotionSquare());
                break;
            case QUEEN:
                newPiece = new Queen(currentPlayer, moveAnalysis.getPromotionSquare());
                break;
            default:
                return board;
        }

        Square[][] squares = board.getSquares();
        Square promotionSquare = moveAnalysis.getPromotionSquare();

        Square newSquare = squares[promotionSquare.getRank()][promotionSquare.getFile()];

        newSquare.setPiece(newPiece);

        BoardBuilder boardBuilder = new BoardFactory().getBoardBuilder(board);
        boardBuilder.setSquares(squares);

        return boardBuilder.createBoard();
    }

    /**
     * Performs all of the necessary actions for a special move.
     *
     * @param moveAnalysis The analysis of the move.
     */
    private Board handleSpecialMoves(Alliance currentPlayer, Board position, MoveAnalysis moveAnalysis, PieceType pieceToPromoteTo) {

        Board board = new BoardFactory().copyBoard(position);

        if (moveAnalysis.isCastlingMove()) {
            board = castlingMove(board, moveAnalysis);

        } else if (moveAnalysis.isEnPassant()) {
            board = enPassantMove(board, moveAnalysis);
        }

        if (moveAnalysis.isPromotionMove()) {
            board = promotionMove(board, moveAnalysis, currentPlayer, pieceToPromoteTo);
        }

        return board;
    }

    /**
     * Sets the relevant castling flag.
     *
     * @param srcSquare The source square of the move.
     * @param rookFile  The file of the rook the set the flag to (king or queen side).
     * @return Whether the player can castle to this side after this move.
     */
    private boolean setCastlingFlag(Alliance playerToSetTheFlagFor, Square srcSquare, int rookFile) {
        Piece playedPiece = srcSquare.getPiece();

        return !(playedPiece.getAlliance() == playerToSetTheFlagFor &&
                (PieceUtils.isKing(playedPiece) || PieceUtils.isRook(playedPiece) && srcSquare.getFile() == rookFile));
    }

    /**
     * Sets the square which en-passant can be performed on.
     *
     * @param srcSquare   The source square of the move.
     * @param destSquare  The destination square of the move.
     * @param newPosition The new position after the move.
     * @return The square which can be taken by en passant.
     */
    private Square setEnPassantTarget(Square srcSquare, Square destSquare, Board newPosition) {
        Piece piece = srcSquare.getPiece();
        MoveVector moveVector = new MoveVector(srcSquare, destSquare);

        Square enPassantTarget = null;
        if (piece.getPieceType() == PieceType.PAWN && MoveUtils.isPawnDoubleMove(srcSquare, piece, moveVector)) {
            int enPassantRank = piece.getAlliance() == Alliance.WHITE ? destSquare.getRank() - 1 : destSquare.getRank() + 1;
            enPassantTarget = newPosition.getSquares()[enPassantRank][destSquare.getFile()];
        }

        return enPassantTarget;
    }

    /**
     * Sets the half move clock.
     *
     * @param newPosition  The new position after the move.
     * @param srcSquare    The source square of the move.
     * @param moveAnalysis The analysis of the move.
     * @return The number of half moves.
     */
    private int setHalfMoveClock(Board newPosition, Square srcSquare, MoveAnalysis moveAnalysis) {

        // If the move is an irrevocable move, reset the counter
        if (srcSquare.getPiece().getPieceType() == PieceType.PAWN || moveAnalysis.isCaptureMove()) {
            return 0;
        }
        return newPosition.getHalfMoveClock() + 1;
    }

    /**
     * Sets all of the relevant flags for the new position.
     *
     * @param newPosition The current position.
     * @return The new position with it's flags set.
     */
    private Board setPositionFlags(Board newPosition, Square srcSquare, Square destSquare, MoveAnalysis moveAnalysis) {
        BoardBuilder boardBuilder = new BoardFactory().getBoardBuilder(newPosition);

        // Switch player
        Alliance playerToMove = newPosition.getPlayerToMove() == Alliance.WHITE ? Alliance.BLACK : Alliance.WHITE;
        boardBuilder.setPlayerToMove(playerToMove);

        // Full move number
        int fullMoveNumber = playerToMove == Alliance.WHITE ? newPosition.getFullMoveNumber() + 1 : newPosition.getFullMoveNumber();
        boardBuilder.setFullMoveNumber(fullMoveNumber);

        // Half move clock
        int halfMoveClock = setHalfMoveClock(newPosition, srcSquare, moveAnalysis);
        boardBuilder.setHalfMoveClock(halfMoveClock);

        // White can castle kingside
        boolean whiteCanCastleKingSide = setCastlingFlag(Alliance.WHITE, srcSquare, 7);
        boardBuilder.setWhiteCanCastleKingSide(whiteCanCastleKingSide);

        // White can castle queenside
        boolean whiteCanCastleQueenSide = setCastlingFlag(Alliance.WHITE, srcSquare, 0);
        boardBuilder.setWhiteCanCastleQueenSide(whiteCanCastleQueenSide);

        // Black can castle kingside
        boolean blackCanCastleKingSide = setCastlingFlag(Alliance.BLACK, srcSquare, 7);
        boardBuilder.setBlackCanCastleKingSide(blackCanCastleKingSide);

        // Black can castle queenside
        boolean blackCanCastleQueenSide = setCastlingFlag(Alliance.BLACK, srcSquare, 0);
        boardBuilder.setBlackCanCastleQueenSide(blackCanCastleQueenSide);

        // Set en passant target
        Square enPassantTarget = setEnPassantTarget(srcSquare, destSquare, newPosition);
        boardBuilder.setEnPassantTarget(enPassantTarget);

        return boardBuilder.createBoard();
    }

    /**
     * Moves a piece from the source square to the destination square.
     *
     * @param srcSquare  The source square.
     * @param destSquare The destination square.
     */
    public static Board movePiece(Board board, Square srcSquare, Square destSquare) {
        Square[][] squares = board.getSquares();

        Square newSrc = squares[srcSquare.getRank()][srcSquare.getFile()];
        Square newDest = squares[destSquare.getRank()][destSquare.getFile()];

        Piece pieceToMove = newSrc.getPiece();

        newDest.setPiece(pieceToMove);
        newSrc.setPiece(null);

        BoardBuilder boardBuilder = new BoardFactory().getBoardBuilder(board);
        boardBuilder.setSquares(squares);

        return boardBuilder.createBoard();
    }

    /**
     * Makes the move, and returns the position of the board after it's done.
     *
     * @param srcSquare  The source square.
     * @param destSquare The destination square.
     * @return Returns the new position, or null if the move is illegal.
     */
    public Board makeMove(Square srcSquare, Square destSquare, PieceType pieceToPromoteTo) {
        MoveAnalyser moveAnalyser = new MoveAnalyser(currentPosition);
        MoveAnalysis moveAnalysis = moveAnalyser.analyseMove(srcSquare, destSquare);

        if (!moveAnalysis.isLegal()) {
            return null;
        }

        Board newPosition = movePiece(currentPosition, srcSquare, destSquare);
        newPosition = handleSpecialMoves(newPosition.getPlayerToMove(), newPosition, moveAnalysis, pieceToPromoteTo);

        newPosition = setPositionFlags(newPosition, srcSquare, destSquare, moveAnalysis);

        return newPosition;
    }

    public Board makeMove(Square srcSquare, Square destSquare) {
        return makeMove(srcSquare, destSquare, null);
    }
}

