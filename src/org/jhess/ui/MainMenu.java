package org.jhess.ui;

import javax.swing.*;

public class MainMenu extends JFrame {

    private JPanel panel;
    private JButton bNewGame;

    MainMenu(){
        setContentPane(panel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(200, 400);
        setTitle("Jhess");
    }
}
