package org.jhess.ui;

import org.jhess.core.Alliance;
import org.jhess.core.moves.GameMove;
import org.jhess.logic.pgn.PgnConverter;

import java.text.MessageFormat;

/**
 * A Wrapper for the GameMove class to be used in a ListView
 */
public class GameMoveListItem {

    private final GameMove gameMove;
    private final int moveNumber;

    public GameMoveListItem(GameMove gameMove, int moveNumber) {
        this.gameMove = gameMove;
        this.moveNumber = moveNumber;
    }

    public GameMove getGameMove() {
        return gameMove;
    }

    public int getMoveNumber() {
        return moveNumber;
    }

    @Override
    public String toString() {
        PgnConverter pgnConverter = new PgnConverter();

        if (gameMove.getPlayedPiece().getAlliance() == Alliance.WHITE)
            return MessageFormat.format("{0}. {1}", moveNumber, pgnConverter.moveToPgn(gameMove));
        else
            return MessageFormat.format("\t...{0}", pgnConverter.moveToPgn(gameMove));
    }
}
