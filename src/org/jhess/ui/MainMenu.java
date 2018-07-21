package org.jhess.ui;

import org.jhess.core.Game;
import org.jhess.core.board.Board;
import org.jhess.core.board.BoardFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

class MainMenu extends JFrame {

    private final JPanel panel = new JPanel();
    private final JButton bSinglePlayer = new JButton("Single Player");
    private final JButton bTwoPlayer = new JButton("Two Player");
    private final JButton bLoadPosition = new JButton("Load Position");
    private final JButton bExit = new JButton("Exit");

    MainMenu() {
        setTitle("Jhess");

        setSize(250, 400);
        setResizable(false);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        WindowUtils.centerWindow(this);
        initPanel();

        bTwoPlayer.addActionListener(this::bTwoPlayerClicked);
        bExit.addActionListener(this::bExitClicked);
        bLoadPosition.addActionListener(this::bLoadPositionClicked);
    }

    private void initPanel() {
        panel.setLayout(new GridLayout(4, 1));

        panel.add(bSinglePlayer);
        panel.add(bTwoPlayer);
        panel.add(bLoadPosition);
        panel.add(bExit);

        add(panel);
    }

    private void bTwoPlayerClicked(ActionEvent e) {
        Game game = new Game();
        GameWindow gameWindow = new GameWindow();
        GameController gameController = new GameController(game, gameWindow);

        gameWindow.setVisible(true);
    }

    private void bLoadPositionClicked(ActionEvent e) {
        String fen = JOptionPane.showInputDialog("Enter the FEN of the position");
        Board position = new BoardFactory().createFromFen(fen);

        Game game = new Game(position);
        GameWindow gameWindow = new GameWindow();
        GameController gameController = new GameController(game, gameWindow);

        gameWindow.setVisible(true);
    }

    private void bExitClicked(ActionEvent e) {
        System.exit(0);
    }
}
