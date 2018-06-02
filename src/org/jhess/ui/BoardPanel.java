package org.jhess.ui;

import org.jhess.core.board.Board;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the board in the UI.
 */
class BoardPanel extends JPanel {

    private List<SquarePanel> squarePanels;
    private final List<SquareClickedListener> listenerList = new ArrayList<>();

    private final Dimension SQUARE_PANEL_DIMENSION = new Dimension(10, 10);

    BoardPanel(Dimension dimension) {
        super(new GridLayout(8, 8));

        setPreferredSize(dimension);
        validate();
    }

    private void populateSquares(Board board) {
        squarePanels = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                int newRank =  7 - i;
                SquarePanel squarePanel = new SquarePanel(this, board.getSquares()[newRank][j], SQUARE_PANEL_DIMENSION);
                squarePanel.addSquareClickedListener(this::handleSquareClicked);

                squarePanels.add(squarePanel);
                add(squarePanel);
            }
        }
    }

    public List<SquarePanel> getSquarePanels() {
        return squarePanels;
    }

    public void drawBoard(Board board) {
        removeAll();

        populateSquares(board);

        for (SquarePanel squarePanel : squarePanels) {
            squarePanel.drawSquare();
            add(squarePanel);
        }

        validate();
        repaint();
    }

    public synchronized void addSquareClickedListener(SquareClickedListener l){
        listenerList.add(l);
    }

    public synchronized void removeSquareClickedListener(SquareClickedListener l){
        listenerList.remove(l);
    }

    /**
     * Propagates the SquareClickedEvent, thus funneling all of the SquareClickedEvents through one method.
     * @param e The SquareClickedEvent.
     */
    public void handleSquareClicked(SquareClickedEvent e) {
        for (SquareClickedListener listener : listenerList){
            listener.handleSquareClicked(e);
        }
    }
}
