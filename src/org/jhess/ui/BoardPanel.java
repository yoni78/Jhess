package org.jhess.ui;

import org.jhess.core.Board;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class BoardPanel extends JPanel {

    private final List<SquarePanel> boardTiles;
    private Board board = new Board();

    private final Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);

    BoardPanel(Dimension dimension) {
        super(new GridLayout(8,8));
        boardTiles = new ArrayList<>();

        for (int i = 0; i < 64; i++) {
            int squareId = 63 - i;
            SquarePanel tilePanel = new SquarePanel(this, squareId, TILE_PANEL_DIMENSION);

            boardTiles.add(tilePanel);
            add(tilePanel);
        }

        setPreferredSize(dimension);
        validate();
    }

    Board getBoard(){
        return board;
    }
}
