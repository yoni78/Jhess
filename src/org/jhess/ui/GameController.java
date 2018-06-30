package org.jhess.ui;

import com.google.common.collect.Iterables;
import org.jhess.core.Alliance;
import org.jhess.core.board.Board;
import org.jhess.core.board.Square;
import org.jhess.core.moves.GameMove;
import org.jhess.core.moves.MoveAnalysis;
import org.jhess.core.moves.MoveVector;
import org.jhess.core.pieces.Piece;
import org.jhess.logics.moves.MoveAnalyser;
import org.jhess.logics.moves.MoveUtils;
import org.jhess.logics.moves.MovesLogic;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

// TODO: 2018-05-21 Add a different class to handle the manage the game flow?
public class GameController {

    private Board board;
    private final GameWindow gameWindow;

    private SquarePanel srcSquare = null;
    private Piece pieceToMove;
    private Alliance currentPlayer = Alliance.WHITE;
    private List<GameMove> gameMoves = new ArrayList<>();

    GameController(Board board, GameWindow gameWindow) {
        this.board = board;
        this.gameWindow = gameWindow;

        initiate();
    }

    /**
     * Initiates the controller
     */
    private void initiate() {
        gameWindow.getBoardPanel().addSquareClickedListener(this::handleSquareClicked);
        gameWindow.getBoardPanel().drawBoard(board);
    }

    /**
     * Changes the currentPlayer to the alliance of the player who should play next.
     */
    private void nextTurn() {
        if (currentPlayer == Alliance.WHITE) {
            currentPlayer = Alliance.BLACK;

        } else {
            currentPlayer = Alliance.WHITE;
        }
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
        if (srcSquare != null){
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

        if (square.getPiece() != null && square.getPiece().getAlliance() == currentPlayer) {

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

        GameMove lastMove = Iterables.getLast(gameMoves, null);
        MoveVector lastMoveVector = null;
        Piece lastPlayedPiece = null;

        if (lastMove != null) {
            lastMoveVector = new MoveVector(lastMove.getSrcSquare(), lastMove.getDestSquare());
            lastPlayedPiece = lastMove.getPlayedPiece();
        }

        MoveAnalysis moveAnalysis = MoveAnalyser.analyseMove(board, srcSquare.getSquare(), destSquare, lastPlayedPiece, lastMoveVector);

        if (moveAnalysis.isLegal() && pieceToMove.getAlliance() == currentPlayer) {
            board = MoveUtils.movePiece(board, srcSquare.getSquare(), destSquare);

            handleSpecialMoves(moveAnalysis);

            gameMoves.add(new GameMove(pieceToMove, srcSquare.getSquare(), destSquare));
            nextTurn();
        }

        srcSquare = null;
        pieceToMove = null;

        SwingUtilities.invokeLater(() -> gameWindow.getBoardPanel().drawBoard(board));
    }

    /**
     * Performs all of the necessary actions for a special move.
     *
     * @param moveAnalysis The analysis of the move.
     */
    private void handleSpecialMoves(MoveAnalysis moveAnalysis) {
        if (moveAnalysis.isCastlingMove()) {
            board = MovesLogic.castlingMove(board, moveAnalysis);

        } else if (moveAnalysis.isEnPassant()) {
            board = MovesLogic.enPassantMove(board, moveAnalysis);
        }

        if (moveAnalysis.isPromotionMove()) {
            PromotionDialog promotionDialog = new PromotionDialog(currentPlayer);
            promotionDialog.setVisible(true);

            board = MovesLogic.promotionMove(board, moveAnalysis, currentPlayer, promotionDialog.getSelectedPieceType());
        }
    }

    public Board getBoard() {
        return board;
    }

    public GameWindow getGameWindow() {
        return gameWindow;
    }

}
