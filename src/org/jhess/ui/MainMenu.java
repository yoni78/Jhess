package org.jhess.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

class MainMenu extends JFrame {

    private final JPanel panel = new JPanel();
    private final JButton bNewGame = new JButton("New Game");

    MainMenu(){
        setTitle("Jhess");

        setSize(250, 400);
        setResizable(false);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        centerWindow();
        initPanel();

        bNewGame.addActionListener(this::bNewGameClicked);
    }

    private void centerWindow(){
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dim = tk.getScreenSize();

        int xPos = (dim.width / 2) - (getWidth() / 2);
        int yPos = (dim.height / 2) - (getHeight() / 2);

        setLocation(xPos, yPos);
    }

    private void initPanel(){
        panel.add(bNewGame);

        add(panel);
    }

    private void bNewGameClicked(ActionEvent e){
        new GameWindow().setVisible(true);
    }
}
