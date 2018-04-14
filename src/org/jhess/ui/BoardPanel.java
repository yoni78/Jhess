package org.jhess.ui;

import org.jhess.core.Board;
import org.jhess.utils.SquareUtils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class BoardPanel extends JPanel {

    private final List<SquarePanel> squarePanels;
    private Board board = new Board();

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
}
