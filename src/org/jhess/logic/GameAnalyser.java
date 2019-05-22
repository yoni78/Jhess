package org.jhess.logic;

import org.jhess.core.Alliance;
import org.jhess.core.game.Game;
import org.jhess.core.board.Board;
import org.jhess.logic.board.PositionAnalyser;

/**
 * An extension for the PositionAnalyser, which can also determine three-fold repetition.
 */
public class GameAnalyser {

    private final Game game;
    private final PositionAnalyser positionAnalyser;

    public GameAnalyser(Game game) {
        this.game = game;
        positionAnalyser = new PositionAnalyser(game.getCurrentPosition());
    }

    public boolean isCheck(Alliance playerToCheck) {
        return positionAnalyser.isCheck(playerToCheck);
    }

    public boolean isCheck() {
        return positionAnalyser.isCheck();
    }

    public boolean isMate() {
        return positionAnalyser.isMate();
    }

    public boolean isStaleMate() {
        return positionAnalyser.isStaleMate();
    }

    public boolean isFiftyMoveDraw() {
        return positionAnalyser.isFiftyMoveDraw();
    }

    /**
     * Checks if the current position has been repeated three times, which results in a draw.
     *
     * @return If it's a draw.
     */
    public boolean isThreeFoldRepetition() {
        int repeatNum = 0;

        for (Board position : game.getPositionList()) {
            if (game.getCurrentPosition().equals(position)) {
                repeatNum++;
            }
        }

        return repeatNum >= 3;
    }
}
