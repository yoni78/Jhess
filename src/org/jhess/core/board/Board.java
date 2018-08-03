package org.jhess.core.board;

import org.jhess.core.Alliance;
import org.jhess.logic.board.BoardUtils;

import java.util.Arrays;
import java.util.Objects;

// TODO: 2018-07-13 Distinguish between "real" and "imaginary" positions (like when calculating mate)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;

        boolean sameSquares = true;
        for (int i = 0; i < 8; i++) {
            if (!Arrays.equals(squares[i], board.squares[i])) {
                return false;
            }
        }

        return whiteCanCastleKingSide == board.whiteCanCastleKingSide &&
                whiteCanCastleQueenSide == board.whiteCanCastleQueenSide &&
                blackCanCastleKingSide == board.blackCanCastleKingSide &&
                blackCanCastleQueenSide == board.blackCanCastleQueenSide &&
                playerToMove == board.playerToMove &&
                Objects.equals(enPassantTarget, board.enPassantTarget);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(playerToMove, whiteCanCastleKingSide, whiteCanCastleQueenSide, blackCanCastleKingSide, blackCanCastleQueenSide, enPassantTarget, halfMoveClock, fullMoveNumber);
        result = 31 * result + Arrays.hashCode(squares);
        return result;
    }
}
