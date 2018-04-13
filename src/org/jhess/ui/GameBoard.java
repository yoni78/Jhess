package org.jhess.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GameBoard extends JPanel {

    private Image boardImage;

    GameBoard() {
        try {
            boardImage = ImageIO.read(new File("resources/images/chessboard.png"))
                    .getScaledInstance(400, 400, Image.SCALE_DEFAULT);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to load the board image.");
            System.exit(1);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(boardImage, 0, 0, this);
    }
}
