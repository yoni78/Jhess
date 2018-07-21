package org.jhess.ui;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {

    private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 400);

    private final BoardPanel boardPanel = new BoardPanel(BOARD_PANEL_DIMENSION);

    public GameWindow() {
        setTitle("Jhess");

        setLayout(new BorderLayout());

        setSize(600, 600);
        setResizable(false);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        WindowUtils.centerWindow(this);
        initPanel();
    }

    private void initPanel() {
        add(boardPanel, BorderLayout.CENTER);
    }

    BoardPanel getBoardPanel() {
        return boardPanel;
    }
}
