package org.jhess.logic.board;

import org.jhess.core.board.Board;
import org.jhess.core.board.BoardFactory;
import org.jhess.core.board.Square;
import org.jhess.logic.moves.MovePerformer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionAnalyserTest {

    @Test
    void isCheck_givenStartingPosition_shouldNotBeCheck() {
        PositionAnalyser positionAnalyser = new PositionAnalyser(new BoardFactory().createDefaultBoard());
        assertFalse(positionAnalyser.isCheck());
    }

    @Test
    void isFiftyMoveDraw_oneHundredRevocableMovesWerePlayed_shouldBeADraw() {
        Board board = new BoardFactory().createDefaultBoard();

        MovePerformer movePerformer;
        Square knightSquare;
        Square destSquare;

        for (int i = 0; i < 25; i++) {
            movePerformer = new MovePerformer(board);
            knightSquare = BoardUtils.getSquareByPgn(board, "g1");
            destSquare = BoardUtils.getSquareByPgn(board, "f3");
            board = movePerformer.makeMove(knightSquare, destSquare);

            movePerformer = new MovePerformer(board);
            knightSquare = BoardUtils.getSquareByPgn(board, "g8");
            destSquare = BoardUtils.getSquareByPgn(board, "f6");
            board = movePerformer.makeMove(knightSquare, destSquare);

            movePerformer = new MovePerformer(board);
            knightSquare = BoardUtils.getSquareByPgn(board, "f3");
            destSquare = BoardUtils.getSquareByPgn(board, "g1");
            board = movePerformer.makeMove(knightSquare, destSquare);

            movePerformer = new MovePerformer(board);
            knightSquare = BoardUtils.getSquareByPgn(board, "f6");
            destSquare = BoardUtils.getSquareByPgn(board, "g8");
            board = movePerformer.makeMove(knightSquare, destSquare);
        }

        assertTrue(new PositionAnalyser(board).isFiftyMoveDraw());
    }
}