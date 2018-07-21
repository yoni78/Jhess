package org.jhess.core;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import org.jhess.core.board.Board;
import org.jhess.core.board.BoardFactory;
import org.jhess.core.moves.GameMove;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a game of chess.
 */
public class Game {

    private final List<GameMove> moveList;
    private final List<Board> positionList;
    private Alliance playerToMove = Alliance.WHITE;

    public Game() {
        this(new BoardFactory().createDefaultBoard());
    }

    public Game(Board startPosition){
        moveList = new ArrayList<>();
        positionList = new ArrayList<>();

        positionList.add(startPosition);
    }

    public Game(Game game){
        moveList = new ArrayList<>(game.getMoveList());
        positionList = new ArrayList<>(game.getPositionList());

        playerToMove = game.getPlayerToMove();
    }

    private void swapAlliance(){
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

    /**
     * Add a turn to the game.
     * @param newPosition The new position.
     * @param playedMove The move that was played.
     */
    public void addTurn(Board newPosition, GameMove playedMove){
        positionList.add(new BoardFactory().copyBoard(newPosition));

        moveList.add(playedMove);

        swapAlliance();
    }
}
