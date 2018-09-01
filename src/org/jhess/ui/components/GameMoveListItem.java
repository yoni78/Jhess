package org.jhess.ui.components;

import org.jhess.core.Alliance;
import org.jhess.core.moves.GameMove;
import org.jhess.logic.pgn.PgnConverter;

import java.text.MessageFormat;

/**
 * A Wrapper for the GameMove class to be used in a ListView.
 */
public class GameMoveListItem {

    private final GameMove gameMove;
    private final int fullMoveNumber;

    public GameMoveListItem(GameMove gameMove, int fullMoveNumber) {
        this.gameMove = gameMove;
        this.fullMoveNumber = fullMoveNumber;
    }

    public GameMove getGameMove() {
        return gameMove;
    }

    public int getFullMoveNumber() {
        return fullMoveNumber;
    }

    @Override
    public String toString() {
        PgnConverter pgnConverter = new PgnConverter();

        if (gameMove.getPlayedPiece().getAlliance() == Alliance.WHITE)
            return MessageFormat.format("{0}. {1}", fullMoveNumber, pgnConverter.moveToPgn(gameMove));
        else
            return MessageFormat.format("\t...{0}", pgnConverter.moveToPgn(gameMove));
    }
}
