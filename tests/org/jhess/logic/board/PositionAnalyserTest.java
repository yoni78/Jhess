package org.jhess.logic.board;

import org.jhess.core.Game;
import org.jhess.core.board.Board;
import org.jhess.core.board.BoardFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionAnalyserTest {

    @Test
    void givenStartingPositionThenShouldNotBeCheck() {
        PositionAnalyser positionAnalyser = new PositionAnalyser(new BoardFactory().createDefaultBoard());

        assertFalse(positionAnalyser.isCheck());
    }

    @Test
    void isMate() {
    }

    @Test
    void isStaleMate() {
    }
}