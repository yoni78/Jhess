package org.jhess.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class GameWindow extends JFrame {

    private final JPanel panel = new JPanel();
    private JLabel boardLabel;

    GameWindow(){
        setTitle("Jhess");

        setSize(600, 600);
        setResizable(false);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        initBoard();

        centerWindow();
        initPanel();
    }

    private void centerWindow(){
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dim = tk.getScreenSize();

        int xPos = (dim.width / 2) - (getWidth() / 2);
        int yPos = (dim.height / 2) - (getHeight() / 2);

        setLocation(xPos, yPos);
    }

    private void initPanel(){
        panel.add(boardLabel);

        add(panel);
    }

    private void initBoard(){
        try{
            BufferedImage boardImage = ImageIO.read(new File("resources/images/chessboard.png"));
            Image boardImageResized = boardImage.getScaledInstance(400, 400, Image.SCALE_DEFAULT);

            boardLabel = new JLabel(new ImageIcon(boardImageResized));
        }

        catch (IOException e){
            JOptionPane.showMessageDialog(null, "Failed to load the board image.");
            System.exit(1);
        }
    }
}
