package org.jhess.ui;

import org.jhess.core.Move;
import org.jhess.core.board.Board;
import org.jhess.core.board.Square;
import org.jhess.core.pieces.Piece;
import org.jhess.utils.SquareUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

class BoardPanel extends JPanel implements SquareClickHandler{

    private final List<SquarePanel> squarePanels;
    private Board board = new Board();
    private int srcSquare = -1;
    private int destSquare = -1;
    private Piece pieceToMove;


    private final Dimension SQUARE_PANEL_DIMENSION = new Dimension(10, 10);

    BoardPanel(Dimension dimension) {
        super(new GridLayout(8,8));
        squarePanels = new ArrayList<>();

        populateSquares();

        setPreferredSize(dimension);
        validate();
    }

    Board getBoard(){
        return board;
    }

    private void populateSquares(){
        for (int i = 0; i < 64; i++) {
            int squareRow = SquareUtils.getSquareRow(i);
            int squareFile = SquareUtils.getSquareFile(i);

            // To reverse the position of the pieces
            squareRow = 7 - squareRow;

            int squareId = SquareUtils.getSquareId(squareRow, squareFile);

            SquarePanel squarePanel = new SquarePanel(this, squareId, SQUARE_PANEL_DIMENSION);

            squarePanels.add(squarePanel);
            add(squarePanel);
        }
    }

    private void drawBoard(Board board){
        removeAll();

        for(SquarePanel squarePanel : squarePanels){
            squarePanel.drawSquare(board);
            add(squarePanel);
        }

        validate();
        repaint();
    }

    @Override
    public void handleSquareClicked(MouseEvent e, int squareId){

        if (isLeftMouseButton(e)){

            if (srcSquare == -1){
                srcSquare = squareId;
                pieceToMove = board.getSquare(squareId).getPiece();

                if (pieceToMove == null){
                    srcSquare = -1;
                }

            } else {
                destSquare = squareId;

                if (Move.isValidMove(board, srcSquare, destSquare)){
                    board.getSquare(srcSquare).setPiece(null);
                    board.getSquare(destSquare).setPiece(pieceToMove);
                }

                srcSquare = -1;
                destSquare = -1;
                pieceToMove = null;
            }

            SwingUtilities.invokeLater(() -> drawBoard(board));

        } else if (isRightMouseButton(e)){
            srcSquare = -1;
            destSquare = -1;
            pieceToMove = null;
        }
    }
}
