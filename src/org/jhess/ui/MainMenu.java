package org.jhess.ui;

import org.jhess.core.board.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

class MainMenu extends JFrame {

    private final JPanel panel = new JPanel();
    private final JButton bNewGame = new JButton("New Game");
    private final JButton bExit = new JButton("Exit");

    MainMenu(){
        setTitle("Jhess");

        setSize(250, 400);
        setResizable(false);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        centerWindow();
        initPanel();

        bNewGame.addActionListener(this::bNewGameClicked);
        bExit.addActionListener(this::bExitClicked);
    }

    private void centerWindow(){
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dim = tk.getScreenSize();

        int xPos = (dim.width / 2) - (getWidth() / 2);
        int yPos = (dim.height / 2) - (getHeight() / 2);

        setLocation(xPos, yPos);
    }

    private void initPanel(){
        panel.setLayout(new GridLayout(4, 1));

        panel.add(bNewGame);
        panel.add(bExit);

        add(panel);
    }

    private void bNewGameClicked(ActionEvent e) {
        Board board = new Board();
        GameWindow gameWindow = new GameWindow();
        GameController gameController = new GameController(board, gameWindow);

        gameWindow.setVisible(true);
    }

    private void bExitClicked(ActionEvent e){
        System.exit(0);
    }
}
