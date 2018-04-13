package org.jhess.ui;

import javax.swing.*;
import java.awt.*;

class GameWindow extends JFrame {

    private GameBoard board = new GameBoard();

    GameWindow() {
        setTitle("Jhess");

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
        add(board);
    }
}
