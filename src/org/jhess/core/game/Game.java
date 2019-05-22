package org.jhess.core.game;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import org.jhess.core.Alliance;
import org.jhess.core.board.Board;
import org.jhess.core.board.BoardFactory;
import org.jhess.core.moves.GameMove;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a game of chess.
 */
public class Game {

    private final GameDetails gameDetails;

    private final List<GameMove> moveList;
    private final List<Board> positionList;
    private Alliance playerToMove = Alliance.WHITE;

    public Game(Board startPosition, GameDetails gameDetails) {
        this.gameDetails = gameDetails;
        moveList = new ArrayList<>();
        positionList = new ArrayList<>();

        positionList.add(startPosition);
    }

    public Game(GameDetails gameDetails) {
        this(new BoardFactory().createDefaultBoard(), gameDetails);
    }

    public Game(Game game, int lastMoveIndex) {
        gameDetails = game.gameDetails;
        moveList = new ArrayList<>(game.getMoveList().subList(0, lastMoveIndex - 1));
        positionList = new ArrayList<>(game.getPositionList().subList(0, lastMoveIndex));

        playerToMove = Iterables.getLast(positionList).getPlayerToMove();
    }

    public Game(Game game) {
        this(game, game.getPositionList().size());
    }

    private void swapAlliance() {
        playerToMove = playerToMove == Alliance.WHITE ? Alliance.BLACK : Alliance.WHITE;
    }

    public Board getCurrentPosition() {
        return Iterables.getLast(positionList);
    }

    public Alliance getPlayerToMove() {
        return playerToMove;
    }

    public List<GameMove> getMoveList() {
        return ImmutableList.copyOf(moveList);
    }

    public List<Board> getPositionList() {
        return ImmutableList.copyOf(positionList);
    }

    public GameDetails getGameDetails() {
        return gameDetails;
    }

    /**
     * Add a turn to the game.
     *
     * @param newPosition The new position.
     * @param playedMove  The move that was played.
     */
    public void addTurn(Board newPosition, GameMove playedMove) {
        positionList.add(new BoardFactory().copyBoard(newPosition));

        moveList.add(playedMove);

        swapAlliance();
    }
}
