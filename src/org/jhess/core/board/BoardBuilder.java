package org.jhess.core.board;

import org.jhess.core.Alliance;

public class BoardBuilder {
    private Square[][] squares;
    private Alliance playerToMove;
    private boolean whiteCanCastleKingSide;
    private boolean whiteCanCastleQueenSide;
    private boolean blackCanCastleKingSide;
    private boolean blackCanCastleQueenSide;
    private Square enPassantTarget;
    private int halfMoveClock;
    private int fullMoveNumber;

    public BoardBuilder setSquares(Square[][] squares) {
        this.squares = squares;
        return this;
    }

    public BoardBuilder setPlayerToMove(Alliance playerToMove) {
        this.playerToMove = playerToMove;
        return this;
    }

    public BoardBuilder setWhiteCanCastleKingSide(boolean whiteCanCastleKingSide) {
        this.whiteCanCastleKingSide = whiteCanCastleKingSide;
        return this;
    }

    public BoardBuilder setWhiteCanCastleQueenSide(boolean whiteCanCastleQueenSide) {
        this.whiteCanCastleQueenSide = whiteCanCastleQueenSide;
        return this;
    }

    public BoardBuilder setBlackCanCastleKingSide(boolean blackCanCastleKingSide) {
        this.blackCanCastleKingSide = blackCanCastleKingSide;
        return this;
    }

    public BoardBuilder setBlackCanCastleQueenSide(boolean blackCanCastleQueenSide) {
        this.blackCanCastleQueenSide = blackCanCastleQueenSide;
        return this;
    }

    public BoardBuilder setEnPassantTarget(Square enPassantTarget) {
        this.enPassantTarget = enPassantTarget;
        return this;
    }

    public BoardBuilder setHalfMoveClock(int halfMoveClock) {
        this.halfMoveClock = halfMoveClock;
        return this;
    }

    public BoardBuilder setFullMoveNumber(int fullMoveNumber) {
        this.fullMoveNumber = fullMoveNumber;
        return this;
    }

    public Board createBoard() {
        return new Board(squares, playerToMove, whiteCanCastleKingSide, whiteCanCastleQueenSide, blackCanCastleKingSide, blackCanCastleQueenSide, enPassantTarget, halfMoveClock, fullMoveNumber);
    }
}