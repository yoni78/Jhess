package org.jhess.ui.controllers;

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
import org.jhess.logic.GameAnalyser;
import org.jhess.logic.Pgn.PgnConverter;
import org.jhess.logic.engine.EngineCommunicator;
import org.jhess.logic.moves.MoveAnalyser;
import org.jhess.logic.moves.MovePerformer;
import org.jhess.ui.windows.PositionAnalyserWindow;
import org.jhess.ui.windows.PromotionWindow;
import org.jhess.ui.panes.SquarePane;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class PositionAnalyserController {

    private final Game game;
    private final PositionAnalyserWindow gameWindow;

    private boolean reverseBoard = false;

    private EngineCommunicator engineCommunicator;

    private SquarePane srcSquare = null;
    private Piece pieceToMove;

    private int gamePositionOffset = 0;

    public PositionAnalyserController(Game game, PositionAnalyserWindow gameWindow) {
        this.game = game;
        this.gameWindow = gameWindow;

        initiate();
    }

    /**
     * Initiates the controller
     */
    private void initiate() {
        gameWindow.getBoardPane().setOnMouseClicked(this::handleSquareClicked);
        gameWindow.getBtnFlip().setOnMouseClicked(this::btnFlipClicked);
        gameWindow.getBtnBack().setOnMouseClicked(this::btnBackClicked);
        gameWindow.getBtnFwd().setOnMouseClicked(this::btnFwdClicked);
        gameWindow.getBtnRewind().setOnMouseClicked(this::btnRewindClicked);
        gameWindow.getBtnCurrentPos().setOnMouseClicked(this::btnCurrentPosClicked);

        drawBoard();

        initEngine();

        highLightBestMove();
    }

    /**
     * Starts the engine process and initializes the engine.
     */
    private void initEngine() {
        try {
            engineCommunicator = new EngineCommunicator("C:\\Users\\Yoni.DESKTOP-9C58T0E\\Desktop\\stockfish-9-win\\Windows\\stockfish_9_x64.exe"); // TODO: 2018-08-10 Get the engine path from the user or default
            engineCommunicator.useUci();
            engineCommunicator.startNewGame();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Changes the currentPlayer to the alliance of the player who should play next.
     */
    private void nextTurn(Board newPosition, GameMove playedMove) {
        game.addTurn(newPosition, playedMove); // TODO: 2018-08-11 If the player played a move in a past position, create a new game with the new moves

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

        int positionIndex = (game.getPositionList().size() - 1) + gamePositionOffset;
        gameWindow.getBoardPane().drawBoard(game.getPositionList().get(positionIndex), reverseBoard);
    }

    /**
     * Converts the current game's move list to a list of strings for the engine.
     *
     * @return A list of strings representing the game's move list.
     */
    private List<String> getMoveList() {
        List<String> moveList = new ArrayList<>();

        PgnConverter pgnConverter = new PgnConverter();
        for (GameMove gameMove : game.getMoveList()) {
            moveList.add(pgnConverter.squareToPgn(gameMove.getSrcSquare()) + pgnConverter.squareToPgn(gameMove.getDestSquare()));
        }

        return moveList;
    }

    /**
     * Gets the best move from the engine an show it on the board
     */
    private void highLightBestMove() {
        String bestMoveString = null;
        try {
            engineCommunicator.setPositionWithMoves(getMoveList());
            bestMoveString = engineCommunicator.getBestMove();

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

        // So you can't make moves in the past
        if (gamePositionOffset != 0){
            return;
        }

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

    private void btnFlipClicked(MouseEvent mouseEvent) {
        reverseBoard = !reverseBoard;
        drawBoard();
        highLightBestMove();
    }

    private void btnBackClicked(MouseEvent mouseEvent) {
        if (!((game.getPositionList().size() - 1) + (gamePositionOffset - 1) < 0)) {
            gamePositionOffset--;
        }

        drawBoard();
        highLightBestMove();
    }

    private void btnFwdClicked(MouseEvent mouseEvent) {
        if (!((game.getPositionList().size() - 1) + (gamePositionOffset + 1) >= game.getPositionList().size())) {
            gamePositionOffset++;
        }

        drawBoard();
        highLightBestMove();
    }

    private void btnCurrentPosClicked(MouseEvent mouseEvent) {
        gamePositionOffset = 0;

        drawBoard();
        highLightBestMove();
    }

    private void btnRewindClicked(MouseEvent mouseEvent) {
        gamePositionOffset = -(game.getPositionList().size() - 1);
        drawBoard();
        highLightBestMove();
    }

}
