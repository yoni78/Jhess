package org.jhess.ui;

import org.jhess.core.Alliance;
import org.jhess.core.Game;
import org.jhess.core.board.Board;
import org.jhess.core.board.Square;
import org.jhess.core.moves.GameMove;
import org.jhess.core.moves.MoveAnalysis;
import org.jhess.core.pieces.Piece;
import org.jhess.core.pieces.PieceType;
import org.jhess.logic.board.PositionAnalyser;
import org.jhess.logic.moves.MoveAnalyser;
import org.jhess.logic.moves.MovePerformer;

import javax.swing.*;
import java.text.MessageFormat;

public class GameController {

    private final Game game;
    private final GameWindow gameWindow;

    private SquarePanel srcSquare = null;
    private Piece pieceToMove;

    GameController(Game game, GameWindow gameWindow) {
        this.game = game;
        this.gameWindow = gameWindow;

        initiate();
    }

    /**
     * Initiates the controller
     */
    private void initiate() {
        gameWindow.getBoardPanel().addSquareClickedListener(this::handleSquareClicked);
        gameWindow.getBoardPanel().drawBoard(game.getCurrentPosition(), false);
    }

    /**
     * Changes the currentPlayer to the alliance of the player who should play next.
     */
    private void nextTurn(Board newPosition, GameMove playedMove) {
        game.addTurn(newPosition, playedMove);

        PositionAnalyser positionAnalyser = new PositionAnalyser(game.getCurrentPosition());

        if (positionAnalyser.isMate()) {
            Alliance otherPlayer = game.getPlayerToMove() == Alliance.WHITE ? Alliance.BLACK : Alliance.WHITE;

            drawBoard(otherPlayer);
            JOptionPane.showMessageDialog(null,
                    MessageFormat.format("Checkmate! {0} is the winner.", otherPlayer.toString()));

            gameWindow.setVisible(false);
            return;

        } else if (positionAnalyser.isStaleMate()) {
            Alliance otherPlayer = game.getPlayerToMove() == Alliance.WHITE ? Alliance.BLACK : Alliance.WHITE;

            drawBoard(otherPlayer);
            JOptionPane.showMessageDialog(null, "Stalemate!");

            gameWindow.setVisible(false);
            return;

        } else if (positionAnalyser.isFiftyMoveDraw()){
            Alliance otherPlayer = game.getPlayerToMove() == Alliance.WHITE ? Alliance.BLACK : Alliance.WHITE;

            drawBoard(otherPlayer);
            JOptionPane.showMessageDialog(null, "Fifty Move Draw!");

            gameWindow.setVisible(false);
            return;
        }

        drawBoard(game.getPlayerToMove());
    }

    /**
     * Draws the board in the correct orientation for the current player.
     */
    private void drawBoard(Alliance currentPlayer) {
        boolean reverseBoard = currentPlayer == Alliance.BLACK;
        SwingUtilities.invokeLater(() -> gameWindow.getBoardPanel().drawBoard(game.getCurrentPosition(), reverseBoard));
    }

    /**
     * The entry point for handling a mouse click.
     *
     * @param e The Square clicked event.
     */
    private void handleSquareClicked(SquareClickedEvent e) {
        if (SwingUtilities.isLeftMouseButton(e.getMouseEvent())) {
            leftMouseClicked(((SquarePanel) e.getSource()));

        } else if (SwingUtilities.isRightMouseButton(e.getMouseEvent())) {
            rightMouseClicked();
        }
    }

    /**
     * Handles a left mouse click.
     *
     * @param clickedSquare The clicked square.
     */
    private void leftMouseClicked(SquarePanel clickedSquare) {
        if (srcSquare == null) {
            firstClick(clickedSquare);

        } else {
            secondClick(clickedSquare);
        }
    }

    /**
     * Handles a right mouse click.
     */
    private void rightMouseClicked() {
        if (srcSquare != null) {
            srcSquare.removeHighLight();
            srcSquare = null;
            pieceToMove = null;
        }
    }

    /**
     * Handles the first time a square is clicked.
     *
     * @param clickedSquare The clicked square.
     */
    private void firstClick(SquarePanel clickedSquare) {
        Square square = clickedSquare.getSquare();

        if (square.getPiece() != null && square.getPiece().getAlliance() == game.getPlayerToMove()) {

            if (srcSquare != null) {
                srcSquare.removeHighLight();
            }

            pieceToMove = square.getPiece();
            srcSquare = clickedSquare;
            srcSquare.highLightBorder();
        }
    }

    /**
     * Handles the second time a square is clicked.
     *
     * @param clickedSquare The clicked square.
     */
    private void secondClick(SquarePanel clickedSquare) {
        Square destSquare = clickedSquare.getSquare();
        MovePerformer movePerformer = new MovePerformer(game.getCurrentPosition());
        MoveAnalysis moveAnalysis = new MoveAnalyser(game.getCurrentPosition()).analyseMove(srcSquare.getSquare(), destSquare);

        PieceType pieceToPromoteTo = null;
        if (moveAnalysis.isPromotionMove()) {
            pieceToPromoteTo = new PromotionDialog(game.getCurrentPosition().getPlayerToMove()).getSelectedPieceType();
        }

        Board newPosition = movePerformer.makeMove(srcSquare.getSquare(), destSquare, pieceToPromoteTo);
        if (newPosition != null) {
            nextTurn(newPosition, new GameMove(pieceToMove, srcSquare.getSquare(), destSquare));
        }

        rightMouseClicked();
    }
}
