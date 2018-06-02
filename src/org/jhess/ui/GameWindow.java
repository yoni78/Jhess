package org.jhess.ui;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {

    private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 400);

    private BoardPanel boardPanel = new BoardPanel(BOARD_PANEL_DIMENSION);

    public GameWindow() {
        setTitle("Jhess");

        setLayout(new BorderLayout());

        setSize(600, 600);
        setResizable(false);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        centerWindow();
        initPanel();
    }

    private void centerWindow() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dim = tk.getScreenSize();

        int xPos = (dim.width / 2) - (getWidth() / 2);
        int yPos = (dim.height / 2) - (getHeight() / 2);

        setLocation(xPos, yPos);
    }

    private void initPanel() {
        add(boardPanel, BorderLayout.CENTER);
    }

    BoardPanel getBoardPanel() {
        return boardPanel;
    }
}
