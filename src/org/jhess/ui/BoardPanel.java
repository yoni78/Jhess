package org.jhess.ui;

import org.jhess.core.Alliance;
import org.jhess.core.moves.MoveValidation;
import org.jhess.core.moves.MoveValidator;
import org.jhess.core.board.Board;
import org.jhess.core.board.Square;
import org.jhess.core.pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

class BoardPanel extends JPanel implements SquareClickHandler {

    private final List<SquarePanel> squarePanels;
    private Board board = new Board();
    private Square srcSquare = null;
    private Piece pieceToMove;
    private Alliance playerToMove = Alliance.WHITE;

    private final Dimension SQUARE_PANEL_DIMENSION = new Dimension(10, 10);

    BoardPanel(Dimension dimension) {
        super(new GridLayout(8, 8));
        squarePanels = new ArrayList<>();

        populateSquares();

        setPreferredSize(dimension);
        validate();
    }

    private void populateSquares() {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                int newRank =  7 - i;
                SquarePanel squarePanel = new SquarePanel(this, board.getSquares()[newRank][j], SQUARE_PANEL_DIMENSION);

                squarePanels.add(squarePanel);
                add(squarePanel);
            }
        }
    }

    private void drawBoard() {
        removeAll();

        for (SquarePanel squarePanel : squarePanels) {
            squarePanel.drawSquare();
            add(squarePanel);
        }

        validate();
        repaint();
    }

    private void nextTurn() {
        if (playerToMove == Alliance.WHITE) {
            playerToMove = Alliance.BLACK;

        } else {
            playerToMove = Alliance.WHITE;
        }
    }

    // TODO: 2018-04-27 Move to a different class
    private void movePiece(Square srcSquare, Square destSquare){
        destSquare.setPiece(srcSquare.getPiece());
        srcSquare.setPiece(null);
    }

    // TODO: 2018-04-27 Move to a different class
    private Square getCastlingRookSquare(Square rookSquare){
        if (rookSquare.getFile() == 0){
            return board.getSquares()[rookSquare.getRank()][3];

        } else if (rookSquare.getFile() == 7){
            return board.getSquares()[rookSquare.getRank()][5];

        } else {
            return null;
        }
    }

    Board getBoard() {
        return board;
    }

    Piece getPieceToMove() {
        return pieceToMove;
    }

    Alliance getPlayerToMove() {
        return playerToMove;
    }

    Square getSrcSquare() {
        return srcSquare;
    }

    @Override
    public void handleSquareClicked(MouseEvent e, int rank, int file) {

        Square destSquare;
        if (isLeftMouseButton(e)) {

            if (srcSquare == null) {
                srcSquare = board.getSquares()[rank][file];
                pieceToMove = srcSquare.getPiece();

                if (pieceToMove == null) {
                    srcSquare = null;
                }

            } else {
                destSquare = board.getSquares()[rank][file];

                MoveValidation validation = MoveValidator.validateMove(board, srcSquare, destSquare);

                if (validation.isValid() && pieceToMove.getAlliance() == playerToMove) {
                    movePiece(srcSquare, destSquare);

                    if (validation.isCastlingMove()){
                        Square rookSquare = validation.getRookToCastleSquare();
                        Square newRookSquare = getCastlingRookSquare(rookSquare);

                        movePiece(rookSquare, Objects.requireNonNull(newRookSquare));
                    }

                    nextTurn();
                }

                srcSquare = null;
                pieceToMove = null;
            }

            SwingUtilities.invokeLater(this::drawBoard);

        } else if (isRightMouseButton(e)) {
            srcSquare = null;
            pieceToMove = null;

            SwingUtilities.invokeLater(this::drawBoard);
        }
    }
}
