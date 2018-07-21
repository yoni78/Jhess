package org.jhess.core.board;

import org.jhess.core.Alliance;
import org.jhess.logic.board.BoardUtils;

// TODO: 2018-07-13 Distinguish between "real" and "imaginary" positions (like when calcuating mate)
public class Board {
    private final Square[][] squares;
    private final Alliance playerToMove;
    private final boolean whiteCanCastleKingSide;
    private final boolean whiteCanCastleQueenSide;
    private final boolean blackCanCastleKingSide;
    private final boolean blackCanCastleQueenSide;
    private final Square enPassantTarget;
    private final int halfMoveClock;
    private final int fullMoveNumber;


    public Board(Square[][] squares, Alliance playerToMove, boolean whiteCanCastleKingSide,
                 boolean whiteCanCastleQueenSide, boolean blackCanCastleKingSide, boolean blackCanCastleQueenSide,
                 Square enPassantTarget, int halfMoveClock, int fullMoveNumber) {
        this.squares = squares;
        this.playerToMove = playerToMove;
        this.whiteCanCastleKingSide = whiteCanCastleKingSide;
        this.whiteCanCastleQueenSide = whiteCanCastleQueenSide;
        this.blackCanCastleKingSide = blackCanCastleKingSide;
        this.blackCanCastleQueenSide = blackCanCastleQueenSide;
        this.enPassantTarget = enPassantTarget;
        this.halfMoveClock = halfMoveClock;
        this.fullMoveNumber = fullMoveNumber;
    }

    public Square[][] getSquares() {
        return BoardUtils.copySquares(squares);
    }

    public Alliance getPlayerToMove() {
        return playerToMove;
    }

    public boolean isWhiteCanCastleKingSide() {
        return whiteCanCastleKingSide;
    }

    public boolean isWhiteCanCastleQueenSide() {
        return whiteCanCastleQueenSide;
    }

    public boolean isBlackCanCastleKingSide() {
        return blackCanCastleKingSide;
    }

    public boolean isBlackCanCastleQueenSide() {
        return blackCanCastleQueenSide;
    }

    public Square getEnPassantTarget() {
        return enPassantTarget == null ? null : new Square(enPassantTarget);
    }

    public int getHalfMoveClock() {
        return halfMoveClock;
    }

    public int getFullMoveNumber() {
        return fullMoveNumber;
    }

}
