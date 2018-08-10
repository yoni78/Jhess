package org.jhess.ui;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.jhess.core.Alliance;
import org.jhess.core.Game;
import org.jhess.core.board.Board;
import org.jhess.core.board.Square;
import org.jhess.core.moves.GameMove;
import org.jhess.core.moves.MoveAnalysis;
import org.jhess.core.pieces.Piece;
import org.jhess.core.pieces.PieceType;
import org.jhess.logic.FenConverter;
import org.jhess.logic.GameAnalyser;
import org.jhess.logic.PgnConverter;
import org.jhess.logic.engine.EngineCommunicator;
import org.jhess.logic.moves.MoveAnalyser;
import org.jhess.logic.moves.MovePerformer;

import java.io.IOException;
import java.text.MessageFormat;

public class PositionAnalyserController {

    private final Game game;
    private final PositionAnalyserWindow gameWindow;

    private EngineCommunicator engineCommunicator;

    private SquarePane srcSquare = null;
    private Piece pieceToMove;

    private SquarePane bestMoveSrcSquare;
    private SquarePane bestMoveDestSquare;

    PositionAnalyserController(Game game, PositionAnalyserWindow gameWindow) {
        this.game = game;
        this.gameWindow = gameWindow;

        initiate();
    }

    /**
     * Initiates the controller
     */
    private void initiate() {
        gameWindow.getBoardPane().setOnMouseClicked(this::handleSquareClicked);
        drawBoard();

        try {
            engineCommunicator = new EngineCommunicator("C:\\Users\\Yoni.DESKTOP-9C58T0E\\Desktop\\stockfish-9-win\\Windows\\stockfish_9_x64.exe");
        } catch (IOException e) {
            e.printStackTrace();
        }

        highLightBestMove();
    }

    /**
     * Changes the currentPlayer to the alliance of the player who should play next.
     */
    private void nextTurn(Board newPosition, GameMove playedMove) {
        game.addTurn(newPosition, playedMove);

        GameAnalyser gameAnalyser = new GameAnalyser(game);

        if (gameAnalyser.isMate()) {
            Alliance otherPlayer = game.getPlayerToMove() == Alliance.WHITE ? Alliance.BLACK : Alliance.WHITE;

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Checkmate");
            alert.setHeaderText(null);
            alert.setContentText(MessageFormat.format("Checkmate! {0} is the winner!", otherPlayer.toString()));
            alert.showAndWait();

            gameWindow.getStage().close();
            return;

        } else if (gameAnalyser.isStaleMate()) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Draw");
            alert.setHeaderText(null);
            alert.setContentText("Stalemate!");
            alert.showAndWait();

            gameWindow.getStage().close();
            return;

        } else if (gameAnalyser.isFiftyMoveDraw()) {


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Draw");
            alert.setHeaderText(null);
            alert.setContentText("Fifty move draw!");
            alert.showAndWait();

            gameWindow.getStage().close();
            return;

        } else if (gameAnalyser.isThreeFoldRepetition()) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Draw");
            alert.setHeaderText(null);
            alert.setContentText("Draw by repetition!");
            alert.showAndWait();

            gameWindow.getStage().close();
            return;
        }

        drawBoard();
        highLightBestMove();
    }

    /**
     * Draws the board in the correct orientation for the current player.
     */
    private void drawBoard() {
        gameWindow.getBoardPane().drawBoard(game.getCurrentPosition());
    }

    /**
     * Gets the best move from the engine an show it on the board
     */
    private void highLightBestMove() {
        String bestMoveString = null;
        try {
            System.out.println(FenConverter.boardToFen(game.getCurrentPosition()));
            bestMoveString = engineCommunicator.getBestMove(FenConverter.boardToFen(game.getCurrentPosition()));

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (bestMoveString == null) {
            return;
        }

        PgnConverter pgnConverter = new PgnConverter();

        Square bestMoveSrcSquare = pgnConverter.pgnToSquare(bestMoveString.substring(0, 2));
        Square bestMoveDestSquare = pgnConverter.pgnToSquare(bestMoveString.substring(2, 4));

        SquarePane bestMoveSrcSquarePane = null;
        SquarePane bestMoveDestSquarePane = null;

        for (Node n : gameWindow.getBoardPane().getGridPane().getChildren()) {
            if (((SquarePane) n).getSquare().getRank() == bestMoveSrcSquare.getRank() && ((SquarePane) n).getSquare().getFile() == bestMoveSrcSquare.getFile()) {
                bestMoveSrcSquarePane = (SquarePane) n;

            } else if (((SquarePane) n).getSquare().getRank() == bestMoveDestSquare.getRank() && ((SquarePane) n).getSquare().getFile() == bestMoveDestSquare.getFile()) {
                bestMoveDestSquarePane = (SquarePane) n;
            }
        }

        if (bestMoveSrcSquarePane != null) {
            bestMoveSrcSquarePane.highLightBorder(Color.GREEN);
        }

        if (bestMoveDestSquarePane != null) {
            bestMoveDestSquarePane.highLightBorder(Color.GREEN);
        }
    }

    /**
     * The entry point for handling a mouse click.
     *
     * @param e The Square clicked event.
     */
    private void handleSquareClicked(MouseEvent e) {

        if (e.getButton() == MouseButton.PRIMARY) {
            leftMouseClicked(((SquarePane) e.getSource()));

        } else if (e.getButton() == MouseButton.SECONDARY) {
            rightMouseClicked();
        }
    }

    /**
     * Handles a left mouse click.
     *
     * @param clickedSquare The clicked square.
     */
    private void leftMouseClicked(SquarePane clickedSquare) {
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
    private void firstClick(SquarePane clickedSquare) {

        Square square = clickedSquare.getSquare();

        if (square.getPiece() != null && square.getPiece().getAlliance() == game.getPlayerToMove()) {

            if (srcSquare != null) {
                srcSquare.removeHighLight();
            }

            pieceToMove = square.getPiece();
            srcSquare = clickedSquare;
            srcSquare.highLightBorder(Color.FIREBRICK);
        }
    }

    /**
     * Handles the second time a square is clicked.
     *
     * @param clickedSquare The clicked square.
     */
    private void secondClick(SquarePane clickedSquare) {
        Square destSquare = clickedSquare.getSquare();
        MovePerformer movePerformer = new MovePerformer(game.getCurrentPosition());
        MoveAnalysis moveAnalysis = new MoveAnalyser(game.getCurrentPosition()).analyseMove(srcSquare.getSquare(), destSquare);

        PieceType pieceToPromoteTo = null;
        if (moveAnalysis.isPromotionMove()) {
            pieceToPromoteTo = new PromotionWindow(game.getCurrentPosition().getPlayerToMove()).getSelectedPieceType();
        }

        Board newPosition = movePerformer.makeMove(srcSquare.getSquare(), destSquare, pieceToPromoteTo);
        if (newPosition != null) {
            nextTurn(newPosition, new GameMove(pieceToMove, srcSquare.getSquare(), destSquare));
        }

        rightMouseClicked();
    }
}
