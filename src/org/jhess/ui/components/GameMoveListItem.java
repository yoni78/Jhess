package org.jhess.ui.components;

import org.jhess.core.Alliance;
import org.jhess.core.game.Game;
import org.jhess.core.moves.GameMove;
import org.jhess.logic.pgn.PgnConverter;

import java.text.MessageFormat;

/**
 * A Wrapper for the GameMove class to be used in a ListView.
 */
public class GameMoveListItem {

    private final Game game;
    private final GameMove gameMove;
    private final int fullMoveNumber;

    public GameMoveListItem(Game game, GameMove gameMove, int fullMoveNumber) {
        this.game = game;
        this.gameMove = gameMove;
        this.fullMoveNumber = fullMoveNumber;
    }

    public Game getGame() {
        return game;
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
            return MessageFormat.format("{0}. {1}", fullMoveNumber, pgnConverter.moveToPgn(game, gameMove));
        else
            return MessageFormat.format("\t...{0}", pgnConverter.moveToPgn(game, gameMove));
    }
}
